package com.paymybuddy.sendmoney.moneyaccounts.service;

import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;

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
     * @throws Throwable
     */
    PmbAccountDTO addConnection(String eMail)
            throws UserWithoutPmbAccountException;

    /**
     * This method removes a connection of the PmbAccount corresponding to the
     * given email.
     *
     * @param eMail
     * @return the PmbAccount that has been disconnected
     * @throws Throwable
     */
    PmbAccountDTO delConnection(String eMail)
            throws UserWithoutPmbAccountException;

    /**
     * This method returns the list of all connections of the logged user.
     *
     * @return a Set<PmbAccount>
     */
    PmbAccountDTO getConnections();

}
