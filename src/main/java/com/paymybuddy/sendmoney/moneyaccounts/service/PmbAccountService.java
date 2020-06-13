package com.paymybuddy.sendmoney.moneyaccounts.service;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * Used to perform the recording of a PMB account in relation with its owner.
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
    PmbAccount savePmbAccount(Buddy buddy);

}
