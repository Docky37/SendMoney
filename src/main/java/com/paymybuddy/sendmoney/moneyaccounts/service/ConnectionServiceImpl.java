package com.paymybuddy.sendmoney.moneyaccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private PmbAccountMapping pmbAccountMapping;

    /**
     * Instance of EmailRetrieve utility class declaration.
     */
    @Autowired
    private EmailRetrieve emailRetrieve;

    /**
     * {@inheritDoc}
     */
    @Override
    public PmbAccountDTO addConnection(final String eMail) {

        PmbAccount pmbAccountToAdd = pmbAccountRepository
                .findByOwnerEmail(eMail);

        if (pmbAccountToAdd != null) {
            String userEmail = emailRetrieve.getEmail();
            PmbAccount myPmbAccount = pmbAccountRepository
                    .findByOwnerEmail(userEmail);
            myPmbAccount.addConnection(pmbAccountToAdd);
            pmbAccountRepository.save(myPmbAccount);
            return pmbAccountMapping.mapPmbAccountToDTO(myPmbAccount);
        } else {
            return null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PmbAccountDTO delConnection(String eMail) {
        PmbAccount pmbAccountToDelete = pmbAccountRepository
                .findByOwnerEmail(eMail);

        if (pmbAccountToDelete != null) {
            String userEmail = emailRetrieve.getEmail();
            PmbAccount myPmbAccount = pmbAccountRepository
                    .findByOwnerEmail(userEmail);
            myPmbAccount.getConnections().remove(pmbAccountToDelete);
            pmbAccountRepository.save(myPmbAccount);
            return pmbAccountMapping.mapPmbAccountToDTO(myPmbAccount);
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
        return pmbAccountMapping.mapPmbAccountToDTO(myPmbAccount);
    }

}
