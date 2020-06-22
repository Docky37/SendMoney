package com.paymybuddy.sendmoney.security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author Thierry SCHREINER
 */
@Component
public class EmailRetrieve {

    public  String getEmail() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername(); 
    }

}
