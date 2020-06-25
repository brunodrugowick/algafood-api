package dev.drugowick.algaworks.algafoodapi.config;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "algafoodapi.storage")
public class StorageProperties {

    private Local local;
    private S3 s3;

    @Getter @Setter
    public static class Local {
        private Path path;
    }

    @Getter @Setter
    public static class S3 {
        @NotBlank private String key;
        @NotBlank private String secret;
        @NotBlank private String bucket;
        @NotNull private Regions region;
        @NotBlank private String folder;
    }
}
