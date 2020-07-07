/**
 * 
 */
package com.paymybuddy.sendmoney.money_transfer.service;

import java.util.List;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;

/**
 * @author Thierry SCHREINER
 *
 */
public interface GetTransferService {

    /**
     * @param email 
     * @return a GetTransferDTO object
     */
    List<GetTransferDTO> findMyTransfer(String email);

}