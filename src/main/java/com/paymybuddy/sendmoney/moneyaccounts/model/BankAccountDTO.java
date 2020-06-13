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
     * The valid character number of a french bank IBAN.
     */
    static final int IBAN_SIZE = 27;

    /**
     * The valid character number of a bank SWIFT code.
     */
    static final int SWIFT_SIZE = 11;

    /**
     * The IBAN of buddy's bank account is stored in a String variable, some
     * validation checks are made with annotations (@NotNull, @Size). An other
     * external check is provided by the UserValidator class.
     */
    @NotNull
    @NotEmpty
    @Size(min = IBAN_SIZE, max = IBAN_SIZE)
    @Getter
    @Setter
    private String ibanCode;

    /**
     * The SWIFT code of buddy's bank account is stored in a String variable,
     * some validation checks are made with annotations (@NotNull, @Size).
     */
    @NotNull
    @NotEmpty
    @Size(min = SWIFT_SIZE, max = SWIFT_SIZE)
    @Getter
    @Setter
    private String swift;

    /**
     * The email address of the bank account owner.
     */
    @Getter
    @Setter
    private String email;

}
