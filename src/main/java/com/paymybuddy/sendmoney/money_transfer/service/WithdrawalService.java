package com.paymybuddy.sendmoney.money_transfer.service;

import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * @author Thierry SCHREINER
 *
 */
@Service
public class WithdrawalService {

    /**
     * Class variable used to build a response message that will be return to
     * the controller.
     */
    private String response = "";

   /**
     * @param order
     * @return
     */
    public Transfer send(final OrderDTO order) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param any
     * @return
     */
    public String saveTransaction(final Transfer withdrawal) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public String getResponse() {
        return response;
    }

}
