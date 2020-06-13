package com.paymybuddy.sendmoney.moneyaccounts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.paymybuddy.sendmoney.security.model.Buddy;

import lombok.Getter;
import lombok.Setter;

/**
 * The BankAccount entity is used to persist bank account data in order to
 * allow external money transfers.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "bank_account")
public class BankAccount {

    /**
     * The id and primary key of bank_account table.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The IBAN of buddy's bank account to create.
     */
    @Getter
    @Setter
    private String iban;

    /**
     * The SWIFT code of buddy's bank account to create.
     */
    @Getter
    @Setter
    private String swift;

    /**
     * The buddy who is the owner of this bank account.
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = Buddy.class)
    @JoinColumn(name = "owner")
    private Buddy owner;

}