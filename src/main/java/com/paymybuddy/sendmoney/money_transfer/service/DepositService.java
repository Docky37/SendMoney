package com.paymybuddy.sendmoney.money_transfer.service;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * @author Thierry SCHREINER
 */
public interface DepositService {

    /**
     * @param any
     * @return
     */
    public Transfer send(OrderDTO any);

    /**
     * @param deposit
     * @return a String
     */
    public String saveTransaction(Transfer deposit);

    /**
     * @return
     */
    public String getResponse();

}
