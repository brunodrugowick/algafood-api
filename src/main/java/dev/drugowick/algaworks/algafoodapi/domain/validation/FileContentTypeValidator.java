package dev.drugowick.algaworks.algafoodapi.domain.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * This is the implementation for {@link Multiple} annotation.
 *
 * The method `initialize` sets `multipleNumber` with the property set when someone is using the annotation.
 *
 * The method `isValid` implement the business rule.
 */
public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> contentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.contentTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null ||
                this.contentTypes.contains(value.getContentType());
    }
}
