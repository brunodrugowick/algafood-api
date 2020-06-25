package dev.drugowick.algaworks.algafoodapi.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import dev.drugowick.algaworks.algafoodapi.config.StorageProperties;
import dev.drugowick.algaworks.algafoodapi.domain.service.PhotoStorageService;
import lombok.RequiredArgsConstructor;

import java.net.URL;

@RequiredArgsConstructor
public class S3PhotoStorageService implements PhotoStorageService {

    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    @Override
    public PhotoWrapper get(String fileName) {
        String filePath = getFilePath(fileName);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);

        return PhotoWrapper.builder().url(url.toString()).build();
    }

    @Override
    public void store(NewPhoto newPhoto) {
        try {
            String filePath = getFilePath(newPhoto.getFileName());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newPhoto.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPhoto.getInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Could not save file to Amazon S3", e);
        }

    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getFolder(), fileName);
    }

    @Override
    public void remove(String fileName) {
        try {
            String filePath = getFilePath(fileName);

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Could not remove file from Amazon S3", e);
        }
    }
}
