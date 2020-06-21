/**
 * 
 */
package com.paymybuddy.sendmoney.security.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thierry SCHREINER
 *
 */
public class AuthenticationRequest {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    /**
     * Empty constructor.
     */
    public AuthenticationRequest() {
    }

    /**
     * Class constructor.
     *
     * @param username
     * @param password
     */
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
