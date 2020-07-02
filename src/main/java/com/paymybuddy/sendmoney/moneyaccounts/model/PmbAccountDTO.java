package com.paymybuddy.sendmoney.moneyaccounts.model;

import java.util.TreeSet;

import org.springframework.stereotype.Component;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * The PmbAccountDTO is used as a Data Transfer object to protect some
 * confidential Data of the PmbAccount entity.
 *
 * @author Thierry SCHREINER
 */
@Component
public class PmbAccountDTO {

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
     * The list of connections of the PMB account Number.
     */
    @Getter
    @Setter
    private Set<String> connections = new TreeSet<String>();

    /**
     * This method is used to add a email in the Set<String> field
     * (named 'connections') of the logged user's PmbAccountDTO.
     *
     * @param email
     */
    public void addConnection(final String email) {
        this.connections.add(email);
    }

}
