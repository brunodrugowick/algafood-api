package dev.drugowick.algaworks.algafoodapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Thanks to https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-injecting-property-values-into-configuration-beans/
 *
 * (I may not have implemented the whole solution on the link above)
 */
@Component
@Getter
public class ApplicationProperties {

    private final boolean examplePropertyEnabled;
    private final Path photosDirectory;

    public ApplicationProperties(@Value("${algafoodapi.example.property.enabled:false}") boolean examplePropertyEnabled,
                                 @Value("${algafoodapi.storage.local.path}") Path photosDirectory) {
        this.examplePropertyEnabled = examplePropertyEnabled;
        this.photosDirectory = photosDirectory;
    }

    public boolean isExamplePropertyEnabled() {
        return examplePropertyEnabled;
    }

}
