package com.paymybuddy.sendmoney.money_transfer.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Date transactionDate;

    /**
     * The transaction field contains the type of money transaction: Deposit,
     * Sending or Withdrawal.
     */
    @Getter
    @Setter
    private String transaction;

    /**
     * The description field is a free text field that allows user to write a
     * short description of the transfer.
     */
    @Getter
    @Setter
    private String description;

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
    private BigDecimal amount;

    /**
     * Getter of transactionDate.
     *
     * @return a Date
     */
    public Date getTransactionDate() {
        Date dateClone = transactionDate;
        return dateClone;
    }

    /**
     * Setter of transactionDate.
     *
     * @param pTransactionDate
     */
    public void setTransactionDate(final Date pTransactionDate) {
        transactionDate = (Date) pTransactionDate.clone();
    }

}
