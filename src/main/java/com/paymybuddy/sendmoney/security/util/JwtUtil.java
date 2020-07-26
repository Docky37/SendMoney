package com.paymybuddy.sendmoney.security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility class used to create a Json Web Token.
 *
 * @author Thierry SCHREINER
 */
@Service
public class JwtUtil {

    /**
     * The secret Key use to encrypt the token.
     */
    private static final String SECRET_KEY = "Sec6K6PmB6200714";

    /**
     * The token period of validity in milliseconds.
     */
    private static final  int VALIDITY_PERIOD_MILLISECONDS = 3600000; // 60 min

    /**
     * This method extract the username of the token.
     *
     * @param token
     * @return a String
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This method extract the expiration Date of the token.
     *
     * @param token
     * @return a Date
     */
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * This method is used to extract Claim calling the extractAllClaims method.
     *
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return a <T> object
     */
    public <T> T extractClaim(final String token,
            final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * This method is used to decrypt the token and parse it as claims.
     *
     * @param token
     * @return a Claims
     */
    private Claims extractAllClaims(final String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token)
                .getBody();
    }

    /**
     * This method check if the expiration date extracted from the token is
     * passed.
     *
     * @param token
     * @return a Boolean
     */
    private Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * This method initializes the generation of the token calling the
     * generateToken method?
     *
     * @param userDetails
     * @return a String
     */
    public String generateToken(final UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * This method create a JWT token after authentication of the user.
     *
     * @param claims
     * @param subject
     * @return a String
     */
    private String createToken(final Map<String, Object> claims,
            final String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + VALIDITY_PERIOD_MILLISECONDS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * This method check if the username of userDetails is equal to the username
     * extracted from the token and if the token is not expired.
     *
     * @param token
     * @param userDetails
     * @return a Boolean
     */
    public Boolean validateToken(final String token,
            final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }
}
