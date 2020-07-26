package com.paymybuddy.sendmoney.money_transfer.service;

import java.time.LocalDate;
import java.util.List;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;

/**
 * @author Thierry SCHREINER
 */
public interface ExternalTransferService {

    /**
     * This method is used to call the repository to find all transfers of a
     * given day, then call a TransfertMapping object to build a GetTransferDTO
     * and finally returns it to the controller.
     *
     * @param submitDate
     * @return a List<GetTransferDTO> object
     */
    List<GetTransferDTO> find(LocalDate submitDate);
}
