package com.paymybuddy.sendmoney.moneyaccounts.service;

import java.util.Set;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;

/**
 * The ConnectionService interface contains three methods used to manage
 * connection between Pmb accounts to allow money transfer.
 *
 * @author Thierry SCHREINER
 */
public interface ConnectionService {

    /**
     * This method adds the connection of the PmbAccount corresponding to the
     * given email.
     *
     * @param eMail
     * @return a PmbAccount
     */
    public PmbAccount addConnection(String eMail);

    /**
     * This method removes a connection of the PmbAccount corresponding to the
     * given email.
     *
     * @param eMail
     * @return the PmbAccount that has been disconnected
     */
    public PmbAccount delConnection(String eMail);

    /**
     * This method returns the list of all connections of the logged user.
     *
     * @return a Set<PmbAccount>
     */
    public Set<PmbAccount> getConnections();

}
