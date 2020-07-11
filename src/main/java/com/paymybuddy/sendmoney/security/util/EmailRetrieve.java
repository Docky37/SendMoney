package com.paymybuddy.sendmoney.security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * This class allows us to get the e-mail address of the current logged user.
 *
 * @author Thierry SCHREINER
 */
@Component
public class EmailRetrieve {

    /**
     * This method retrieve the username of the current logged user, username
     * that is his email address.
     *
     * @return a String
     */
    public String getEmail() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    /**
     * This method retrieve the current logged user.
     *
     * @return a Buddy
     */
    public Buddy getBuddy() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return ((Buddy) principal);

    }

}
