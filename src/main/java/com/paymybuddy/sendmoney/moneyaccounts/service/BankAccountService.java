package com.paymybuddy.sendmoney.moneyaccounts.service;

import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccountDTO;

/**
 * Used to perform the recording of a bank account in relation with its owner.
 *
 * @author Thierry SCHREINER
 */
public interface BankAccountService {

    /**
     * The saveBankAccount method is in charge of the BankAccountDTO mapping to
     * a BankAccount entity, using encryption for IBAN before recording in DB. 
     *
     * @param bankAccountDTO coming from frontend form
     */
    void saveBankAccount(BankAccountDTO bankAccountDTO);

}
