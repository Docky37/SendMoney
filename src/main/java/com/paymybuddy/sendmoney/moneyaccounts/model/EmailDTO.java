package com.paymybuddy.sendmoney.moneyaccounts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is a Data Transfer Object that only contains an email address.
 *
 * @author Thierry SCHREINER
 */
public class EmailDTO {

    /**
     * Empty constructor.
     */
    public EmailDTO() {
    }

    /**
     * An email address.
     */
    @Getter
    @Setter
    private String email;

}
