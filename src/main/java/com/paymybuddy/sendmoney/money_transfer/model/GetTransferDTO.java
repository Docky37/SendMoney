package com.paymybuddy.sendmoney.money_transfer.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thierry SCHREINER
 */
public class GetTransferDTO extends OrderDTO {

    /**
     * This valueDate is set just after the transaction becomes effective in
     * both sender & beneficiary accounts.
     */
    private Date valueDate;

    /**
     * The transaction field contains the type of money transaction: Deposit,
     * Sending or Withdrawal.
     */
    @Getter
    @Setter
    private String transaction;

    /**
     * The fee of the transaction.
     */
    @Getter
    @Setter
    private double fee;

    /**
     * Getter of valueDate.
     *
     * @return a Date
     */
    public Date getValueDate() {
        Date dateClone = (Date) valueDate.clone();
        return dateClone;
    }

    /**
     * Setter of valueDate.
     *
     * @param pValueDate
     */
    public void setValueDate(final Date pValueDate) {
        valueDate = (Date) pValueDate.clone();
    }

    /**
     * Empty constructor.
     */
    public GetTransferDTO() {
        super();
    }

}
