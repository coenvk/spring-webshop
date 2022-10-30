package org.coenvk.samples.spring.toolkit.annotation;

import java.util.UUID;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotDefaultValidator implements ConstraintValidator<NotDefault, Object> {
    private static final UUID DEFAULT_UUID = new UUID(0L, 0L);

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (!(o instanceof UUID uuid)) {
            return true;
        }

        return !uuid.equals(DEFAULT_UUID);
    }
}
