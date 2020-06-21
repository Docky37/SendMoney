/**
 * 
 */
package com.paymybuddy.sendmoney.security.model;

/**
 * Object that contains the JWT response after authentication.
 *
 * @author Thierry SCHREINER
 */
public class AuthenticationResponse {

    private final String jwt;

    /**
     * Class constructor.
     * @param jwt
     */
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    /**
     * Getter of jwt.
     * @return the Jason Web Token
     */
    public String getJwt() {
        return jwt;
    }

}
