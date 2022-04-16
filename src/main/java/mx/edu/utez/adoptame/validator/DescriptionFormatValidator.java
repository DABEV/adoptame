package mx.edu.utez.adoptame.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DescriptionFormatValidator implements ConstraintValidator<DescriptionFormat, String> {
    
    private static final String PATTERN_DESCRIPTION = "[^À-ÿA-Za-z0-9\\s]+";

    @Override
    public boolean isValid(String description, ConstraintValidatorContext context) {
        boolean isValid = false;
        
        try {
            Pattern pattern = Pattern.compile(PATTERN_DESCRIPTION);
            Matcher matcher = pattern.matcher(description);
            isValid = getCountInvalidChars(matcher) == 0;
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }

    private int getCountInvalidChars (Matcher matcher) {
        int count = 0;
        while(matcher.find()) count++;
        return count;
    }
}
