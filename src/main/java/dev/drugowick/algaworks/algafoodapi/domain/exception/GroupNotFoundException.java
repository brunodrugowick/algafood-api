package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GroupNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public GroupNotFoundException(String message) {
		super(message);
	}

	public GroupNotFoundException(Long id) {
		this(String.format("There's no Group with the id %d.", id));
	}

}
