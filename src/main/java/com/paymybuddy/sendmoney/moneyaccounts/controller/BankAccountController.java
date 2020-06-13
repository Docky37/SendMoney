package com.paymybuddy.sendmoney.moneyaccounts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.service.BankAccountService;

/**
 * This controller is in charge of the bank account registering.
 *
 * @author Thierry SCHREINER
 */
@Controller
public class BankAccountController {

    /**
     * Declare a BankAccountService object.
     */
    @Autowired
    private BankAccountService bankAccountService;

    /**
     * HTML GET method used to provide Bank account frontend form.
     *
     * @param model
     * @return a String (name of frontendpage)
     */
    @GetMapping(value = "/bank-account")
    public String addBankAccount(final Model model) {
        model.addAttribute("bankAccountForm", new BankAccountDTO());
        return "bank-account";
    }

    /**
     * HTML POST method used to process with Bank account form data.
     *
     * @param bankAccountForm
     * @param bindingResult
     * @param model
     * @return a String (name of frontendpage)
     */
    @PostMapping(value = "/bank-account")
    public String newBankAccount(
            @ModelAttribute("bankAccountForm")
            @Valid final BankAccountDTO bankAccountForm,
            final BindingResult bindingResult, final Model model) {
        System.out.println("Controller");
        if (bindingResult.hasErrors()) {
            return "redirect:/bank-account";
        }
        bankAccountService.saveBankAccount(bankAccountForm);
        return "welcome";
    }

}
