package com.paymybuddy.sendmoney.moneyaccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;

/**
 * Service class that performs connection business between PmbAccounts.
 *
 * @author Thierry SCHREINER
 */
@Service
public class ConnectionServiceImpl implements ConnectionService {

    /**
     * Instance of PmbAccountRepository declaration.
     */
    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    /**
     * Instance of PmbAccountMapping class declaration.
     */
    @Autowired
    private PmbAccountMapping pmbAccountMapping;

    /**
     * Instance of EmailRetrieve utility class declaration.
     */
    @Autowired
    private EmailRetrieve emailRetrieve;

    /**
     * {@inheritDoc}
     *
     * @throws Throwable
     */
    @Override
    public PmbAccountDTO addConnection(final String eMail)
            throws UserWithoutPmbAccountException {

        PmbAccount pmbAccountToAdd = pmbAccountRepository
                .findByOwnerEmail(eMail);

        if (pmbAccountToAdd != null) {
            String userEmail = emailRetrieve.getEmail();
            PmbAccount myPmbAccount = pmbAccountRepository
                    .findByOwnerEmail(userEmail);
            if (myPmbAccount != null) {
                myPmbAccount.addConnection(pmbAccountToAdd);
                pmbAccountRepository.save(myPmbAccount);
                return pmbAccountMapping.mapPmbAccountToDTO(myPmbAccount);
            } else {
                throw new UserWithoutPmbAccountException();
            }
        } else {
            return null;
        }

    }

    /**
     * {@inheritDoc}
     *
     * @throws Throwable
     */
    @Override
    public PmbAccountDTO delConnection(final String eMail)
            throws UserWithoutPmbAccountException {
        PmbAccount pmbAccountToDelete = pmbAccountRepository
                .findByOwnerEmail(eMail);

        if (pmbAccountToDelete != null) {
            String userEmail = emailRetrieve.getEmail();
            PmbAccount myPmbAccount = pmbAccountRepository
                    .findByOwnerEmail(userEmail);
            if (myPmbAccount != null) {
                myPmbAccount.getConnections().remove(pmbAccountToDelete);
                pmbAccountRepository.save(myPmbAccount);
                return pmbAccountMapping.mapPmbAccountToDTO(myPmbAccount);
            } else {
                throw new UserWithoutPmbAccountException();
            }
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PmbAccountDTO getConnections() {
        String userEmail = emailRetrieve.getEmail();
        PmbAccount myPmbAccount = pmbAccountRepository
                .findByOwnerEmail(userEmail);
        if (myPmbAccount != null) {
            return pmbAccountMapping.mapPmbAccountToDTO(myPmbAccount);
        }
        return null;
    }

}
