package jee.annuaire.controller;

import jee.annuaire.web.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class UserValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;

    // Validate that the id is not null and is a number
    if (user.getUserId() == null || !user.getUserId().toString().matches("\\d+")) {
      errors.rejectValue("id", "user.id.invalid", "ID must be a number");
    }

    // Validate that the password is not null or empty
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
        "user.password", "Password is required.");

  }
}