package com.paymybuddy.sendmoney.moneyaccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * {@inheritDoc}.
 */
@Service
public class PmbAccountServiceImpl implements PmbAccountService {

    /**
     * A PmbAccountRepository object used to deal with the DataBase pmb_account
     * table.
     */
    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    /**
     * {@inheritDoc}.
     */
    @Override
    public PmbAccount savePmbAccount(final Buddy buddy) {

        PmbAccount pmbAccount = new PmbAccount();
        pmbAccount
                .setPmbAccountNumber(pmbAccountNumberGenerator(buddy.getId()));
        pmbAccount.setOwner(buddy);

        pmbAccountRepository.save(pmbAccount);

        return pmbAccount;
    }

    /**
     * This method has responsibility to generate the pmb account number of a
     * new buddy, number in relation with its owner id.
     *
     * @param buddyId
     * @return a String (the generated Pay My Buddy account number)
     */
    private String pmbAccountNumberGenerator(final long buddyId) {
        String pmbAccountNumber = "PMB" + String.format("%07d", buddyId);

        return pmbAccountNumber;
    }
}
