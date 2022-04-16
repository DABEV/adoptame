package mx.edu.utez.adoptame.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberFormatValidator implements ConstraintValidator<PhoneNumberFormat, String> {

    private static final String PATTERN_PHONE = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        boolean isValid = false;
        
        try {
            isValid = phoneNumber.matches(PATTERN_PHONE);
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }
}
