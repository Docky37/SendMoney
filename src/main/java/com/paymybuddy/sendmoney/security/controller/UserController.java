package com.paymybuddy.sendmoney.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.service.UserService;
import com.paymybuddy.sendmoney.security.validator.UserValidator;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new Buddy());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") Buddy userForm,
            BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error",
                    "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message",
                    "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
}

/*
 * OAUTH2 Functionnality private final OAuth2AuthorizedClientService
 * authorizedClientService;
 * 
 * public UserController(OAuth2AuthorizedClientService authorizedClientService)
 * { this.authorizedClientService = authorizedClientService; }
 * 
 * @RequestMapping("/**")
 * 
 * @RolesAllowed("USER") public String getUser() { return "Welcome, user"; }
 * 
 * @RequestMapping("/admin")
 * 
 * @RolesAllowed("ADMIN") public String getAdmin() { return "Welcome, admin";
 * 
 * }
 * 
 * @RequestMapping("/*") public String getUserInfo(Principal user) {
 * StringBuffer userInfo = new StringBuffer(); if (user instanceof
 * UsernamePasswordAuthenticationToken) {
 * userInfo.append(getUsernamePasswordLoginInfo(user)); } else if (user
 * instanceOf OAuth2AuthenticationToken) {
 * userInfo.append(getOauth2LoginInfo(user)); } return userInfo.toString(); }
 * 
 * private StringBuffer getOauth2LoginInfo(Principal user) { StringBuffer
 * protectedInfo = new StringBuffer(); OAuth2AuthenticationToken authToken =
 * ((OAuth2AuthenticationToken) user); OAuth2User principal =
 * ((OAuth2AuthenticationToken) user).getPrincipal(); OAuth2AuthorizedClient
 * authClient = this.authorizedClientService
 * .loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(),
 * authToken.getName());
 * 
 * Map<String, Object> userAttributes = ((DefaultOAuth2User)
 * authToken.getPrincipal()).getAttributes(); String userToken =
 * authClient.getAccessToken().getTokenValue(); protectedInfo.append("Welcome, "
 * + userAttributes.get("name") + "<br><br>"); protectedInfo.append("e-mail: " +
 * userAttributes.get("email") + "<br><br>");
 * protectedInfo.append("Access Token: " + userToken + "<br><br>");
 * 
 * OidcIdToken idToken = getIdToken(principal); if (idToken != null) {
 * protectedInfo.append("idToken value: " + idToken.getTokenValue());
 * protectedInfo.append("Token mapped values <br><br>"); Map<String, Object>
 * claims = idToken.getClaims();
 * 
 * for (String key : claims.keySet()) { protectedInfo.append("    " + key +
 * ":   " + claims.get(key) + "<br>"); } } else { protectedInfo.append("NA"); }
 * return protectedInfo; }
 * 
 * private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
 * StringBuffer usernameInfo = new StringBuffer();
 * 
 * UsernamePasswordAuthenticationToken token =
 * ((UsernamePasswordAuthenticationToken) user); if (token.isAuthenticated()) {
 * User u = (User) token.getPrincipal(); usernameInfo.append("Welcome, " +
 * u.getUsername()); } else { usernameInfo.append("NA"); } return usernameInfo;
 * }
 * 
 * private OidcIdToken getIdToken(OAuth2User principal) { if (principal
 * instanceOf DefaultOidcUser) { DefaultOidcUser oidcUser = (DefaultOidcUser)
 * principal; return oidcUser.getIdToken(); } return null; }
 */
