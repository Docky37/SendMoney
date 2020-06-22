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

    /**
     * The minimum size for email.
     */
    static final int EMAIL_MIN_SIZE = 6;
    /**
     * The maximum size for email.
     */
    static final int EMAIL_MAX_SIZE = 35;
    /**
     * The minimum size for password.
     */
    static final int PASSWORD_MIN_SIZE = 6;
    /**
     * The maximum size for password.
     */
    static final int PASSWORD_MAX_SIZE = 35;

    /**
     * The first name of the Buddy.
     */
    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String firstName;

    /**
     * The last name of the Buddy.
     */
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
    @Size(min = EMAIL_MIN_SIZE, max = EMAIL_MAX_SIZE)
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
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE)
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

    /**
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param confirmPassword
     */
    public UserDTO(@NotNull @NotEmpty String firstName,
            @NotNull @NotEmpty String lastName,
            @NotNull @Size(min = 6, max = 35) @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") String email,
            @NotNull @NotEmpty @Size(min = 6, max = 35) String password,
            String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    /**
     * 
     */
    public UserDTO() {
    }

    
}
