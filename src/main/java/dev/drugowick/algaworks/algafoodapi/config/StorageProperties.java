package dev.drugowick.algaworks.algafoodapi.config;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "algafoodapi.storage")
public class StorageProperties {

    private Local local;
    private S3 s3;
    private StorageType type = StorageType.LOCAL;

    @Getter @Setter
    public static class Local {
        private Path path;
    }

    public static enum StorageType { LOCAL, S3 }

    @Getter @Setter
    public static class S3 {
        private String key;
        private String secret;
        private String bucket;
        private Regions region;
        private String folder;
    }
}
