package com.paymybuddy.sendmoney.moneyaccounts.util;

import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.moneyaccounts.model.AccountBalanceDTO;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;

/**
 * Mapping class used to create a Data Transfer Object containing non sensible
 * data of a PmbAccount.
 *
 * @author Thierry SCHREINER
 */
@Component
public class PmbAccountMapping {

    /**
     * Mapping method used to create a new Data Transfer Object and set its
     * fields with data coming from the given PmbAccount parameter.
     *
     * @param pmbAccount
     * @return a PmbAccountDTO object
     */
    public PmbAccountDTO mapPmbAccountToDTO(final PmbAccount pmbAccount) {
        PmbAccountDTO myPmbAccountDTO = new PmbAccountDTO();
        myPmbAccountDTO.setPmbAccountNumber(pmbAccount.getPmbAccountNumber());
        myPmbAccountDTO.setOwnerMail(pmbAccount.getOwner().getEmail());
        myPmbAccountDTO.setConnections(new TreeSet<String>());
        for (PmbAccount connection : pmbAccount.getConnections()) {
            myPmbAccountDTO.addConnection(connection.getOwner().getFirstName()
                    + " " + connection.getOwner().getLastName());
        }

        return myPmbAccountDTO;
    }

    /**
     * Mapping method used to create a new Data Transfer Object and set its
     * fields with data coming from the given PmbAccount parameter.
     *
     * @param pmbAccount
     * @return an AccountBalanceDTO object
     */
    public AccountBalanceDTO mapAccountBalanceDTO(final PmbAccount pmbAccount) {
        AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
        accountBalanceDTO.setPmbAccountNumber(pmbAccount.getPmbAccountNumber());
        accountBalanceDTO.setAccountBalance(pmbAccount.getAccountBalance());
        accountBalanceDTO.setOwnerMail(pmbAccount.getOwner().getEmail());

        return accountBalanceDTO;
    }
}
