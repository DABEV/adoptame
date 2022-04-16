package mx.edu.utez.adoptame.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailFormatValidator implements ConstraintValidator<EmailFormat, String>{

    private static final String PATTERN_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean isValid = false;
        
        try {
            isValid = email.matches(PATTERN_EMAIL);
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }
}
