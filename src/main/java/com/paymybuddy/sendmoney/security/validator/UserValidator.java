package com.paymybuddy.sendmoney.security.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.service.UserService;

/**
 * This class has responsibility to check the validity of registering form data.
 *
 * @author Thierry SCHREINER
 */
@Component
public class UserValidator implements Validator {
    /**
     * Declares the service that provides registration and login.
     */
    @Autowired
    private UserService userService;

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean supports(final Class<?> aClass) {
        return Buddy.class.equals(aClass);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void validate(final Object o, final Errors errors) {
        UserDTO user = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");

        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword",
                    "Diff.userForm.confirmPassword");
        }
    }
}
