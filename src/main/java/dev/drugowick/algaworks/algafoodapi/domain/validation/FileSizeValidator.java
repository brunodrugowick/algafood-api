package dev.drugowick.algaworks.algafoodapi.domain.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This is the implementation for {@link Multiple} annotation.
 *
 * The method `initialize` sets `multipleNumber` with the property set when someone is using the annotation.
 *
 * The method `isValid` implement the business rule.
 */
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize max;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.max = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || value.getSize() <= this.max.toBytes();
    }
}
