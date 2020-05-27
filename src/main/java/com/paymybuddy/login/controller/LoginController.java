package com.paymybuddy.login.controller;

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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private final OAuth2AuthorizedClientService authorizedClientService;

	public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}

	@RequestMapping("/**")
	@RolesAllowed("USER")
	public String getUser() {
		return "Welcome, user";
	}

	@RequestMapping("/admin")
	@RolesAllowed("ADMIN")
	public String getAdmin() {
		return "Welcome, admin";

	}

	@RequestMapping("/*")
	public String getUserInfo(Principal user) {
		StringBuffer userInfo = new StringBuffer();
		if (user instanceof UsernamePasswordAuthenticationToken) {
			userInfo.append(getUsernamePasswordLoginInfo(user));
		} else if (user instanceof OAuth2AuthenticationToken) {
			userInfo.append(getOauth2LoginInfo(user));
		}
		return userInfo.toString();
	}

	private StringBuffer getOauth2LoginInfo(Principal user) {
		StringBuffer protectedInfo = new StringBuffer();
		OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
		OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();
		OAuth2AuthorizedClient authClient = this.authorizedClientService
				.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());

		Map<String, Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();
		String userToken = authClient.getAccessToken().getTokenValue();
		protectedInfo.append("Welcome, " + userAttributes.get("name") + "<br><br>");
		protectedInfo.append("e-mail: " + userAttributes.get("email") + "<br><br>");
		protectedInfo.append("Access Token: " + userToken + "<br><br>");

		OidcIdToken idToken = getIdToken(principal);
		if (idToken != null) {
			protectedInfo.append("idToken value: " + idToken.getTokenValue());
			protectedInfo.append("Token mapped values <br><br>");
			Map<String, Object> claims = idToken.getClaims();

			for (String key : claims.keySet()) {
				protectedInfo.append("    " + key + ":   " + claims.get(key) + "<br>");
			}
		} else {
			protectedInfo.append("NA");
		}
		return protectedInfo;
	}

	private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
		StringBuffer usernameInfo = new StringBuffer();

		UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
		if (token.isAuthenticated()) {
			User u = (User) token.getPrincipal();
			usernameInfo.append("Welcome, " + u.getUsername());
		} else {
			usernameInfo.append("NA");
		}
		return usernameInfo;
	}

	private OidcIdToken getIdToken(OAuth2User principal) {
		if (principal instanceof DefaultOidcUser) {
			DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
			return oidcUser.getIdToken();
		}
		return null;
	}
}
