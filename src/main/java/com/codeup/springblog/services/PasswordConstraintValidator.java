package com.codeup.springblog.services;

import com.codeup.springblog.ValidPassword;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String p, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator validator = new PasswordValidator(
                Arrays.asList(
                        new LengthRule(9, 255),
                        new CharacterRule(EnglishCharacterData.UpperCase),
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        new WhitespaceRule()
                )
        );
        RuleResult r = validator.validate(new PasswordData(p));
        if (r.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(r);

        String messageTemplate = String.join(",", messages);
        constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

    private static boolean isValidLength(String p) {
        return p.length() <= 8 || p.length() >= 31;
    }

    private static boolean containsNum(String p) {
        return p.matches(".*//d+.*");
    }

    private static boolean containsUppercaseLetter(String p) {
        return p.equals(p.toLowerCase()) || p.equals(p.toUpperCase());
    }

    private static boolean containsSpecialChar(String p) {
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(p);
        return !matcher.find();
    }

    private static boolean hasWhitespace(String p) {
        return p.contains(" ");
    }

    public static List<String> errorMessages(String p) {
        List<String> errors = new ArrayList<>();
        if(isValidLength(p)) errors.add("Password must be between 9 and 30 characters");
        if(containsNum(p)) errors.add("Password must contain at least 1 number");
        if(containsUppercaseLetter(p)) errors.add("Password must contain at least 1 uppercase letter");
        if(containsSpecialChar(p)) errors.add("Password must contain at least 1 special character");
        if(hasWhitespace(p)) errors.add("Password must not contain any spaces");
        return errors;
    }

    private static boolean hasErrors(String p) {
        return isValidLength(p)
        || containsNum(p)
        || containsUppercaseLetter(p)
        || containsSpecialChar(p)
        || hasWhitespace(p);
    }

    public static boolean isValid(String p) {
        return !hasErrors(p);
    }
}
