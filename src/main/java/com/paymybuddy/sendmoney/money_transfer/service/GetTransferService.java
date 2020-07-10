package com.paymybuddy.sendmoney.money_transfer.service;

import java.util.List;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;

/**
 * The GetTransferService interface defines a method that find all money
 * transfers that exists on the PmbAccount of the buddy of the given email.
 *
 * @author Thierry SCHREINER
 */
public interface GetTransferService {

    /**
     * This method finds all money transfers that exists on the PmbAccount of
     * the buddy of the given email.
     *
     * @param email
     * @return a GetTransferDTO object
     */
    List<GetTransferDTO> findMyTransfer(String email);

}
