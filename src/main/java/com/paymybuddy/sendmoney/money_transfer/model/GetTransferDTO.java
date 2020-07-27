package com.paymybuddy.sendmoney.money_transfer.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * This Data Transfer Object is used as response of users'html GET request on
 * /transfer and administrators'html GET request on /pmb-adm/transfer.
 *
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
    private BigDecimal fee;

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
     * The first name of the transfer beneficiary.
     */
    @Getter
    @Setter
    private String beneficiaryFirstName;

    /**
     * The last name of the transfer beneficiary.
     */
    @Getter
    @Setter
    private String beneficiaryLastName;

    /**
     * The BankBAccount IBAN of the transaction beneficiary, only used for
     * withdrawal.
     */
    @Getter
    @Setter
    private String bankAccountIban;

    /**
     * The BankBAccount SWIFT of the transaction beneficiary, only used for
     * withdrawal.
     */
    @Getter
    @Setter
    private String bankAccountSwift;

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
