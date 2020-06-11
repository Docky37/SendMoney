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
 * 
 * @author Thierry SCHREINER
 */
@Controller
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    // Add bank account
    @GetMapping(value = "/bank-account")
    public String addBankAccount(Model model) {
        model.addAttribute("bankAccountForm", new BankAccountDTO());
        return "bank-account";
    }

    @PostMapping(value = "/bank-account")
    public String newBankAccount(
            @ModelAttribute("bankAccountForm") @Valid BankAccountDTO bankAccountForm,
            BindingResult bindingResult, Model model) {
        System.out.println("Controller");
        if (bindingResult.hasErrors()) {
            return "redirect:/bank-account";
        }
       bankAccountService.save(bankAccountForm);
        return "welcome";
    }

}
