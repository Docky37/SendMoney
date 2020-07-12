package com.paymybuddy.sendmoney.money_transfer.service;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * @author Thierry SCHREINER
 */
public interface DepositService {

    /**
     * The send method creates a money Transfer from an orderDTO.
     *
     * @param orderDTO
     * @return a Tranfer object
     */
    Transfer send(OrderDTO orderDTO);

    /**
     * The saveTransaction method performs the update of PmbAccounts from a
     * Transfer object.
     *
     * @param deposit
     * @return a String
     */
    String saveTransaction(Transfer deposit);

    /**
     * Getter of Response field.
     * @return a String
     */
    String getResponse();

}
