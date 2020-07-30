package com.paymybuddy.sendmoney.moneyaccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.moneyaccounts.model.AccountBalanceDTO;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
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
     * Autowired PmbAccountMapping instance used to convert a PmbAccount object
     * to a pmbAccountDTO object.
     */
    @Autowired
    private PmbAccountMapping pmbAccountMapping;

    /**
     * {@inheritDoc}.
     */
    @Override
    public PmbAccountDTO savePmbAccount(final Buddy buddy) {

        PmbAccount pmbAccount = pmbAccountRepository
                .findByOwnerEmail(buddy.getEmail());
        if (pmbAccount != null) {
            return null;
        }
        pmbAccount = new PmbAccount();
        pmbAccount
                .setPmbAccountNumber(pmbAccountNumberGenerator(buddy.getId()));
        pmbAccount.setOwner(buddy);

        pmbAccountRepository.save(pmbAccount);

        PmbAccountDTO pmbAccountDTO = pmbAccountMapping
                .mapPmbAccountToDTO(pmbAccount);

        return pmbAccountDTO;
    }

    /**
     * This method has responsibility to generate the PMB account number of a
     * new buddy, number in relation with its owner id.
     *
     * @param buddyId
     * @return a String (the generated Pay My Buddy account number)
     */
    private String pmbAccountNumberGenerator(final long buddyId) {
        String pmbAccountNumber = "PMB" + String.format("%07d", buddyId);

        return pmbAccountNumber;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public AccountBalanceDTO getAccountBalance(final Buddy buddy) {
        PmbAccount pmbAccount = pmbAccountRepository
                .findByOwnerEmail(buddy.getEmail());
        AccountBalanceDTO accountBalanceDTO = pmbAccountMapping
                .mapAccountBalanceDTO(pmbAccount);
        return accountBalanceDTO;
    }
}
