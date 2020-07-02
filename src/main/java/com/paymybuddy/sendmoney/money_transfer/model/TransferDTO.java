package com.paymybuddy.sendmoney.money_transfer.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The TransferDTO class is the data transfer object used to carry data of a
 * money transfer, from service to mapping class.
 *
 * @author Thierry SCHREINER
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransferDTO {

    /**
     * The transferDTO transaction date equal to the creation date of the
     * instance.
     */
    @Getter
    private Date transactionDate;

    /**
     * The transaction field contains the type of money transaction: Deposit,
     * Sending or Withdrawal.
     */
    @Getter
    private String transaction;

    /**
     * The PmBAccount of the transaction sender.
     */
    @Getter
    private PmbAccount pmbAccountSender;

    /**
     * The PmBAccount of the transaction beneficiary.
     */
    @Getter
    private PmbAccount pmbAccountBeneficiary;

    /**
     * The amount of the transaction.
     */
    @Getter
    private Double amount;

}
