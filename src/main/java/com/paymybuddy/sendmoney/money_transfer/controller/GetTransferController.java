package com.paymybuddy.sendmoney.money_transfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;
import com.paymybuddy.sendmoney.money_transfer.service.GetTransferService;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;

/**
 * @author Thierry SCHREINER
 */
@RestController
public class GetTransferController {

    /**
     * Declare a GetTransferService object.
     */
    @Autowired
    private GetTransferService getTransferService;

    /**
     * Utility class used to retrieve the email of the current logged user.
     */
    @Autowired
    private EmailRetrieve emailRetrieve;

    /**
     * Get html Request that returns the list user's money transfers.
     *
     * @return a List<GetTransferDTO>
     */
    @GetMapping("/transfer")
    public List<GetTransferDTO> getTransfer() {
        String email = emailRetrieve.getEmail();
        List<GetTransferDTO> getTransferDTOList = getTransferService
                .findMyTransfer(email);
        return getTransferDTOList;
    }

}
