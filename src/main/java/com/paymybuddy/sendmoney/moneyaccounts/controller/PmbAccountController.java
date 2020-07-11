package com.paymybuddy.sendmoney.moneyaccounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.moneyaccounts.model.EmailDTO;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.service.PmbAccountService;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;

/**
 * This controller is in charge of the PMB account registering.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class PmbAccountController {

    /**
     * Declare a PmbAccountService object.
     */
    @Autowired
    private PmbAccountService pmbAccountService;

    /**
     * Utility class used to retrieve the email of the current logged user.
     */
    @Autowired
    private EmailRetrieve emailRetrieve;

    /**
     * HTML POST method used to register a PMB account.
     *
     * @param emailDTO
     * @return a String
     */
    @PostMapping("/pmb-account")
    public ResponseEntity<Object> newPmbAccount(
            @RequestBody final EmailDTO emailDTO) {
        Buddy buddy = emailRetrieve.getBuddy();
        String emailVerif = buddy.getEmail();
        if (emailDTO.getEmail().compareTo(emailVerif) != 0) {
            return new ResponseEntity<Object>(
                    "Logged user can't create a PMB account for someone else!",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        PmbAccountDTO pmbAccountDTO = pmbAccountService.savePmbAccount(buddy);
        if (pmbAccountDTO == null) {
            return new ResponseEntity<Object>(
                    "Operation cancelled because PMB account already exists.",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<Object>("PMB account saved.",
                new HttpHeaders(), HttpStatus.CREATED);
    }

}
