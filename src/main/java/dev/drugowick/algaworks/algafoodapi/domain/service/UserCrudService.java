package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.UserNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Group;
import dev.drugowick.algaworks.algafoodapi.domain.model.User;
import dev.drugowick.algaworks.algafoodapi.domain.repository.UserRespository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserCrudService {

    private static final String MSG_USER_CONFLICT = "Operation on User %d conflicts with another entity and can not be performed.";

    private final UserRespository userRepository;
    private final GroupCrudService groupCrudService;

    public UserCrudService(UserRespository userRepository, GroupCrudService groupCrudService) {
        this.userRepository = userRepository;
        this.groupCrudService = groupCrudService;
    }

    @Transactional
    public User save(User user) {
        // It's necessary to detach the user from the EntityManager to avoid unplanned sync with DB by JPA
        userRepository.detach(user);
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent() && !optionalUser.get().equals(user)) {
            throw new GenericBusinessException(
                    String.format("There's already a user with the email %s", user.getEmail())
            );
        }

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_USER_CONFLICT, user.getId()));
        }
    }

    public User update(Long id, User user) {
        user.setId(id);
        return save(user);
    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
            // Flushing here guarantees the DB exceptions below can be caught.
            userRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_USER_CONFLICT, id));
        } catch (EmptyResultDataAccessException exception) {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    public void updatePassword(Long userId, String newPassword, String password) {
        User userToUpdatePassword = findOrElseThrow(userId);
        if (userToUpdatePassword.passwordDoesNotMatch(password)) {
            throw new GenericBusinessException("Wrong password");
        }
        userToUpdatePassword.setPassword(newPassword);
        // transaction takes care of commiting the changes
    }

    @Transactional
    public boolean bindGroup(Long userId, Long groupId) {
        User user = findOrElseThrow(userId);
        Group group = groupCrudService.findOrElseThrow(groupId);

        return user.addGroup(group);
    }

    @Transactional
    public boolean unbindGroup(Long userId, Long groupId) {
        User user = findOrElseThrow(userId);
        Group group = groupCrudService.findOrElseThrow(groupId);

        return user.removeGroup(group);
    }


    /**
     * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
     *
     * @param id of the entity to find.
     * @return the entity from the repository.
     */
    public User findOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
