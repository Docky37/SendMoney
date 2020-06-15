package com.paymybuddy.sendmoney.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.service.UserService;
import com.paymybuddy.sendmoney.security.validator.UserValidator;

/**
 * This controller class is in charge of html request methods of registration
 * and login.
 *
 * @author Thierry SCHREINER
 */
@Controller
public class UserController {

    /**
     * Declares the service class that provides business work behind this
     * controller.
     */
    @Autowired
    private UserService userService;

    /**
     * Declares a class in charge of the validation of the registration form
     * data.
     */
    @Autowired
    private UserValidator userValidator;

    // REGISTRATION PART
    /**
     * A GET html request that provides the frontend registration form.
     *
     * @param model
     * @return a String (the name of the frontend page)
     */
    @GetMapping(value = "/registration")
    public String registration(final Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    /**
     * POST html request used to process with registration form data.
     *
     * @param userForm
     * @param bindingResult
     * @param model
     * @return a String (the name of the next frontend page)
     */
    @PostMapping(value = "/registration")
    public String registration(
            @ModelAttribute("userForm") @Valid final UserDTO userForm,
            final BindingResult bindingResult, final Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "redirect:/registration";
        }
        userService.save(userForm);
        return "bank-account";
    }

// LOGIN PART
    /**
     * A GET html request that provides the frontend registration form.
     *
     * @param model
     * @param error
     * @param logout
     * @return a String (the name of the next frontend page)
     */
    @GetMapping(value = "/login")
    public String login(final Model model, final String error,
            final String logout) {
        if (error != null) {
            model.addAttribute("error",
                    "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message",
                    "You have been logged out successfully.");
        }
        return "login";
    }

 // USER WELCOME PAGE
    /**
     * A GET html request that provides the frontend user welcome page.
     *
     * @param model
     * @return a String (the name of the next frontend page)
     */
    @GetMapping(value = { "/", "/welcome" })
    public String welcome(final Model model) {
        return "welcome";
    }

// ADMIN WELCOME PAGE
    /**
     * A GET html request that provides the frontend admin welcome page.
     *
     * @param model
     * @return a String (the name of the next frontend page)
     */
    @GetMapping(value = { "/admin" })
    public String admin(final Model model) {
        return "admin";
    }
}
