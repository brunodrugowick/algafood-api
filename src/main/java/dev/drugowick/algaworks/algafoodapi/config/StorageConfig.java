package dev.drugowick.algaworks.algafoodapi.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import dev.drugowick.algaworks.algafoodapi.domain.service.PhotoStorageService;
import dev.drugowick.algaworks.algafoodapi.infrastructure.service.storage.LocalPhotoStorageService;
import dev.drugowick.algaworks.algafoodapi.infrastructure.service.storage.S3PhotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final StorageProperties storageProperties;
    private final ApplicationProperties applicationProperties;

    @Bean
    @ConditionalOnProperty(name = "algafoodapi.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(storageProperties.getS3().getKey(), storageProperties.getS3().getSecret());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build();
    }

    @Bean
    public PhotoStorageService photoStorageService() {
        if (storageProperties.getType().equals(StorageProperties.StorageType.S3)) {
            return new S3PhotoStorageService(amazonS3(), storageProperties);
        }

        return new LocalPhotoStorageService(applicationProperties);
    }
}
