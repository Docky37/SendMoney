package com.paymybuddy.sendmoney.security.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The JSON Web Token (JWT) Authentication filter checks if the incoming request
 * has a valid JSON Web Token (JWT). It checks the validity of the JWT by
 * verifying the JWT signature, audiences and issuer based on the HTTP filter
 * configuration.
 *
 * @author Thierry SCHREINER
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Instance of UserDetailsService declaration. Spring will use the
     * UserDetailsServiceImpl class.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Instance of JwtUtil class, a utility class that provides methods to
     * authenticate with a Json web token.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        String bearer = "Bearer ";

        if (authorizationHeader != null
                && authorizationHeader.startsWith(bearer)) {
            jwt = authorizationHeader.substring(bearer.length());
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext()
                .getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken userPassAuthToken =
                        new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                userPassAuthToken
                        .setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                SecurityContextHolder.getContext()
                        .setAuthentication(userPassAuthToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
