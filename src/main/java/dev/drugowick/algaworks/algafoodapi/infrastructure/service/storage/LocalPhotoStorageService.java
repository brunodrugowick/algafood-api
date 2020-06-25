package dev.drugowick.algaworks.algafoodapi.infrastructure.service.storage;

import dev.drugowick.algaworks.algafoodapi.config.ApplicationProperties;
import dev.drugowick.algaworks.algafoodapi.domain.service.PhotoStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {

    private final Path photosDirectory;

    public LocalPhotoStorageService(ApplicationProperties applicationProperties) {
        this.photosDirectory = applicationProperties.getPhotosDirectory();
    }

    @Override
    public InputStream get(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            return Files.newInputStream(filePath);
        } catch (Exception e) {
            throw new StorageException("Could not get file.", e);
        }
    }

    @Override
    public void store(NewPhoto newPhoto) {
        Path filePath = getFilePath(newPhoto.getFileName());

        try {
            FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("Could not store file", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException("Could not remove file", e);
        }
    }

    private Path getFilePath(String fileName) {
        return photosDirectory.resolve(Path.of(fileName));
    }
}
