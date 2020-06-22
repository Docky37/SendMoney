package com.paymybuddy.sendmoney.moneyaccounts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.service.BankAccountService;

/**
 * This controller is in charge of the bank account registering.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class BankAccountController {

    /**
     * Declare a BankAccountService object.
     */
    @Autowired
    private BankAccountService bankAccountService;

    /**
     * HTML POST method used to register a Bank account.
     *
     * @param bankAccountForm
     * @param bindingResult
     * @param model
     * @return a String (name of frontendpage)
     */
    @PostMapping("/bank-account")
    public ResponseEntity<Object> newBankAccount(
            @RequestBody @Valid final BankAccountDTO bankAccountDTO,
            final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(
                    "Rejected value: "
                            + bindingResult.getFieldError().getRejectedValue()
                            + " because: "
                            + bindingResult.getFieldError().getDefaultMessage(),
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        bankAccountDTO.setEmail(((UserDetails) principal).getUsername()); 

        bankAccountService.saveBankAccount(bankAccountDTO);
        return new ResponseEntity<Object>("Bank account saved.",
                new HttpHeaders(), HttpStatus.OK);
    }

}
