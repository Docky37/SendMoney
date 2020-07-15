package com.paymybuddy.sendmoney.money_transfer.service;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * @author Thierry SCHREINER
 */
public interface WithdrawalService {

    /**
     * The send method creates a money Transfer from an orderDTO.
     *
     * @param orderDTO
     * @return a Transfer object
     */
    Transfer send(OrderDTO orderDTO);

    /**
     * The saveTransaction method performs the update of PmbAccounts from a
     * Transfer object.
     *
     * @param withdrawal a Transfer object
     * @return a String
     */
    String saveTransaction(Transfer withdrawal);

    /**
     * Getter of response.
     * 
     * @return a String
     */
    String getResponse();

}
