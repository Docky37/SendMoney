package com.paymybuddy.sendmoney.moneyaccounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.AccountBalanceDTO;
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
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger("PmbAccountController");

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

    /**
     * {@inheritDoc}
     */
    @GetMapping("/account-balance")
    public AccountBalanceDTO getAccountBalance()
            throws UserWithoutPmbAccountException {
        LOGGER.info("NEW GET REQUEST('/account-balance') ********************");
        Buddy buddy = emailRetrieve.getBuddy();
        AccountBalanceDTO accountBalanceDTO = pmbAccountService
                .getAccountBalance(buddy);

        if (accountBalanceDTO != null) {
            LOGGER.info("GET REQUEST END: 200 Ok");
            return accountBalanceDTO;
        } else {
            throw new UserWithoutPmbAccountException();
        }
    }

}
