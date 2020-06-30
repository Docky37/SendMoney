package com.paymybuddy.sendmoney.money_transfer.service;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;

/**
 * @author Thierry SCHREINER
 *
 */
public interface SendMoneyService {

    /**
     * @param orderDTO
     */
    void send(OrderDTO orderDTO);

}
