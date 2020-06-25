package dev.drugowick.algaworks.algafoodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    PhotoWrapper get(String fileName);

    void store(NewPhoto newPhoto);

    void remove(String fileName);

    default void replace(String oldFileName, NewPhoto newPhoto) {
        this.store(newPhoto);

        if (oldFileName != null) {
            this.remove(oldFileName);
        }
    }

    default String getFileName(String suffix) {
        return UUID.randomUUID().toString() + "_" + suffix;
    }

    @Getter
    @Builder
    class NewPhoto {
        private String fileName;
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class PhotoWrapper {
        private InputStream inputStream;
        private String url;

        public boolean hasUrl() {
            return url != null;
        }

        public boolean hasInputStream() {
            return inputStream != null;
        }
    }
}
