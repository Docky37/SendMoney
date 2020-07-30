package com.paymybuddy.sendmoney.moneyaccounts.model;


import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The AccountBalanceDTO is used as a Data Transfer object to allow user to get
 * the account balance of is PmbAccount.
 *
 * @author Thierry SCHREINER
 */
@Component
public class AccountBalanceDTO {

    /**
     * The Pay My Buddy account identification Number, a 10 characters String
     * that begins by "PMB" and contains the buddy id.
     */
    @Getter
    @Setter
    private String pmbAccountNumber;

    /**
     * The email of the buddy who is the owner of this PMB account.
     */
    @Getter
    @Setter
    private String ownerMail;

    /**
     * The credit balance of the Pay My Buddy account (cannot be negative).
     */
    @Getter
    @Setter
    private BigDecimal accountBalance;


}
