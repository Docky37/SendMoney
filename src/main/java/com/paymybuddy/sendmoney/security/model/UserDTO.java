package com.paymybuddy.sendmoney.security.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is the Data Transfer Object used for user registration.
 *
 * @author Thierry Schreiner
 */
public class UserDTO {

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String firstName;

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String lastName;

    /**
     * The email address of a new user is stored in a String variable and is
     * used as login. Some validation checks are made with annotations
     * (@NotNull, @Size and @Email). An other external check is provided by the
     * UserValidator class.
     */
    @NotNull
    @Size(min = 6, max = 35)
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)"
            + "*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    @Getter
    @Setter
    private String email;

    /**
     * The password of a new user is stored in a String variable and some
     * validation checks are made with annotations (@NotNull, @NotEmpty, @Size).
     */
    @NotNull
    @NotEmpty
    @Size(min = 7, max = 14)
    @Getter
    @Setter
    private String password;

    /**
     * The confirmPassword of a new user is stored in a String variable and an
     * external check is provided by the UserValidator class, to assess that it
     * is equal to the password.
     */
    @Getter
    @Setter
    private String confirmPassword;

}
