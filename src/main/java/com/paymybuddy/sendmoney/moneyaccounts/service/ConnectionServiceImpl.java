package com.paymybuddy.sendmoney.moneyaccounts.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
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
     * Instance of EmailRetrieve utility class declaration.
     */
    @Autowired
    private EmailRetrieve emailRetrieve;

    /**
     * {@inheritDoc}
     */
    @Override
    public PmbAccount addConnection(final String eMail) {

        PmbAccount pmbAccountToAdd = pmbAccountRepository
                .findByOwnerEmail(eMail);

        if (pmbAccountToAdd != null) {
            String userEmail = emailRetrieve.getEmail();
            PmbAccount myPmbAccount = pmbAccountRepository
                    .findByOwnerEmail(userEmail);
            myPmbAccount.addConnection(pmbAccountToAdd);
            pmbAccountRepository.save(myPmbAccount);
            return myPmbAccount;
        } else {
            return null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PmbAccount delConnection(String eMail) {
        PmbAccount pmbAccountToDelete = pmbAccountRepository
                .findByOwnerEmail(eMail);

        if (pmbAccountToDelete != null) {
            String userEmail = emailRetrieve.getEmail();
            PmbAccount myPmbAccount = pmbAccountRepository
                    .findByOwnerEmail(userEmail);
            Set<PmbAccount> connections = myPmbAccount.getConnections();
            connections.remove(pmbAccountToDelete);
            myPmbAccount.setConnections(connections);
            pmbAccountRepository.save(myPmbAccount);
            return myPmbAccount;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PmbAccount> getConnections() {
        String userEmail = emailRetrieve.getEmail();
        PmbAccount myPmbAccount = pmbAccountRepository
                .findByOwnerEmail(userEmail);
        Set<PmbAccount> connections = myPmbAccount.getConnections();
        return connections;
    }

}
