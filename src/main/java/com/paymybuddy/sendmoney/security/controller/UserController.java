package com.paymybuddy.sendmoney.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.security.model.AuthenticationRequest;
import com.paymybuddy.sendmoney.security.model.AuthenticationResponse;
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.service.UserService;
/* IMPORTS FOR OAUTH2
import java.security.Principal;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;*/
import com.paymybuddy.sendmoney.security.validator.UserValidator;
import com.paymybuddy.sendmoney.security.util.JwtUtil;

/**
 * This controller class is in charge of html request methods of registration
 * and login.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class UserController {

    /**
     * Instance of UserDetailsService declaration.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Instance of UserService declaration.
     */
    @Autowired
    private UserService userService;

    /**
     * Declares a class in charge of the validation of the registration form
     * data.
     */
    @Autowired
    private UserValidator userValidator;

    /**
     * Instance of Jason Web Token utility class declaration.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Instance of AuthenticationManager declaration.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    // USER WELCOME PAGE*********************************************
    /**
     * A GET html request that provides the frontend user welcome page.
     *
     * @return a String (the name of the next frontend page)
     */
    @GetMapping("/welcome")
    public String welcomePage() {
        return "Welcome";
    }

    /**
     * POST html request used to authenticate and provide a Token.
     *
     * @param authenticationRequest
     * @return a ResponseEntity<Object>
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody final AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // REGISTRATION PART
    /**
     * POST html request used to create a new account.
     *
     * @param userDTO
     * @param bindingResult
     * @return a ResponseEntity<Object>
     */
    @PostMapping("/registration")
    public ResponseEntity<Object> registration(
            @RequestBody @Valid final UserDTO userDTO,
            final BindingResult bindingResult) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(
                    "Rejected value: "
                            + bindingResult.getFieldError().getRejectedValue()
                            + " because: "
                            + bindingResult.getFieldError().getDefaultMessage(),
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        userService.save(userDTO);
        return new ResponseEntity<Object>("User account saved.",
                new HttpHeaders(), HttpStatus.OK);
    }

}
