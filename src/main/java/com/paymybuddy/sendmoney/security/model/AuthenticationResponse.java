package com.paymybuddy.sendmoney.security.model;

/**
 * Object that contains the Jason Web Token response after authentication.
 *
 * @author Thierry SCHREINER
 */
public class AuthenticationResponse {

    /**
     * The Jason Web Token returned by the server after valid authentication.
     */
    private final String jwt;

    /**
     * Class constructor.
     *
     * @param pJwt
     */
    public AuthenticationResponse(final String pJwt) {
        jwt = pJwt;
    }

    /**
     * Getter of jwt.
     *
     * @return the Jason Web Token
     */
    public String getJwt() {
        return jwt;
    }

}
