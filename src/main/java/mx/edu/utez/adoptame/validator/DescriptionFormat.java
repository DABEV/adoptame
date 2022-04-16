package mx.edu.utez.adoptame.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DescriptionFormatValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionFormat {
    String message() default "El formato del campo es inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
