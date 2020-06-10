package com.paymybuddy.sendmoney.moneyaccounts.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is the Data Transfer Object used for bank account adding.
 *
 * @author Thierry SCHREINER
 */
public class BankAccountDTO {

    /**
     * The IBAN of buddy's bank account is stored in a String variable, some
     * validation checks are made with annotations (@NotNull, @Size). An other
     * external check is provided by the UserValidator class.
     */
    @NotNull
    @NotEmpty
    @Size(min = 27, max = 27)
    @Getter
    @Setter
    private String ibanCode;

    /**
     * The SWIFT code of buddy's bank account is stored in a String variable,
     * some validation checks are made with annotations (@NotNull, @Size). 
     */
    @NotNull
    @NotEmpty
    @Size(min = 11, max = 11)
    @Getter
    @Setter
    private String swift;
    
    @Getter
    @Setter
    private String email;

}
