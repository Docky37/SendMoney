package com.paymybuddy.sendmoney.money_transfer.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The transfer class is an entity that contains all data of a PMB money
 * transfer.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "transfer")
@NoArgsConstructor
@ToString
public class Transfer {

    /**
     * The transfer id field and primary key.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The transfer transaction date equal to the creation date of the
     * transfertDTO instance.
     */
    @Getter
    @Setter
    private Date transactionDate;

    /**
     * The transaction field contains the type of money transaction: Deposit,
     * Sending or Withdrawal.
     */
    @Getter
    @Setter
    private String transaction;

    /**
     * The PmBAccount of the transaction sender.
     */
    @Getter
    @Setter
    @OneToOne
    private PmbAccount pmbAccountSender;

    /**
     * The PmBAccount of the transaction beneficiary.
     */
    @Getter
    @Setter
    @OneToOne
    private PmbAccount pmbAccountBeneficiary;

    /**
     * The amount of the transaction.
     */
    @Getter
    @Setter
    private Double amount;

    /**
     * The fee of the transaction.
     */
    @Getter
    @Setter
    private Double fee;

    /**
     * This boolean value (false per default) will be set to true when the
     * transaction will be effective in both sender & beneficiary accounts.
     */
    @Getter
    @Setter
    private boolean isEffective = false;

    /**
     * This valueDate is set just after the transaction becomes effective in
     * both sender & beneficiary accounts.
     */
    @Getter
    @Setter
    private Date valueDate;

}
