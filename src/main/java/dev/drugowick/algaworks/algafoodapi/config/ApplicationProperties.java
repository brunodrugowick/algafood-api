package dev.drugowick.algaworks.algafoodapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Thanks to https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-injecting-property-values-into-configuration-beans/
 */
@Component
public class ApplicationProperties {

    private final boolean examplePropertyEnabled;

    public ApplicationProperties(@Value("${algafoodapi.example.property.enabled:false}") boolean examplePropertyEnabled) {
        this.examplePropertyEnabled = examplePropertyEnabled;
    }

    public boolean isExamplePropertyEnabled() {
        return examplePropertyEnabled;
    }
}
