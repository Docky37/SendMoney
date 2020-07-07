package com.paymybuddy.sendmoney.money_transfer.service;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * The SendMoneyService class is in charge of the business work of sending
 * transaction functionality.
 *
 * @author Thierry SCHREINER
 */
public interface SendMoneyService {

    /**
     * The send method is in charge to check if sender PmbAccount has a
     * sufficient account balance and if beneficiary is a valid connection of
     * the sender. Then it creates the transferDTO, calls an external transfer
     * mapping method that returns the transfer entity and calls the repository
     * to persist transfer. After that it calls the doTransaction method to
     * update sender and beneficiary accounts.
     *
     * @param orderDTO
     * @return a String response starting with status like 201...
     */
    Transfer send(OrderDTO orderDTO);

    /**
     * This method is responsible of the update of both sender and beneficiary
     * accounts.
     *
     * @param transfer
     * @return a boolean (true if operation succeeds)
     */
    String saveTransaction(Transfer transfer);

    /**
     * Getter of response.
     *
     * @return a String, the service response message.
     */
    String getResponse();

}
