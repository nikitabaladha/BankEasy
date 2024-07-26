package com.bankeasy.bankeasy.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.bankeasy.bankeasy.entities.SignupRequest;

@Component
public class SignupRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SignupRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignupRequest signupRequest = (SignupRequest) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required", "First name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required", "Last name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Password is required");

        if (!isValidEmail(signupRequest.getEmail())) {
            errors.rejectValue("email", "email.invalid", "Email is invalid");
        }

        if (signupRequest.getPassword().length() < 8) {
            errors.rejectValue("password", "password.length", "Password must be at least 8 characters long");
        }

        if (!isValidPassword(signupRequest.getPassword())) {
            errors.rejectValue("password", "password.complexity", "Password must contain at least one uppercase letter, one lowercase letter, and one digit");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
}

