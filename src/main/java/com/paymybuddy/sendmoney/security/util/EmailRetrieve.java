package com.paymybuddy.sendmoney.security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Thierry SCHREINER
 */
public class EmailRetrieve {

    public  String getEmail() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername(); 
    }

}
