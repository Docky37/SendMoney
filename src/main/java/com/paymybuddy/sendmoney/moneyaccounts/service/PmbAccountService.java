package com.paymybuddy.sendmoney.moneyaccounts.service;

import com.paymybuddy.sendmoney.moneyaccounts.model.AccountBalanceDTO;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * Used to perform the recording of a PMB account in relation with its owner,
 * and to get the account balance.
 *
 * @author Thierry SCHREINER
 */
public interface PmbAccountService {

    /**
     * The savePMBAccount method performs the recording of a PmbAccount in
     * relation with its owner.
     *
     * @param buddy (the owner of the PmbAccount)
     * @return a PmbAccount object
     */
    PmbAccountDTO savePmbAccount(Buddy buddy);

    /**
     * This method allows user to get the account balance.
     *
     * @param buddy (the owner of the PmbAccount)
     * @return an AccountBalanceDTO
     */
    AccountBalanceDTO getAccountBalance(Buddy buddy);

}
