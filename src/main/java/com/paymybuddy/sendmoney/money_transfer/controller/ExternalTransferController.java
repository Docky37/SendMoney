package com.paymybuddy.sendmoney.money_transfer.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;
import com.paymybuddy.sendmoney.money_transfer.service.ExternalTransferService;

/**
 * @author Thierry SCHREINER
 */
@RestController
public class ExternalTransferController {

    /**
     * Date format used to convert String parameter to LocalDate.
     */
    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("MM/dd/yyyy");

    /**
     * Declare a DepositService object.
     */
    @Autowired
    private ExternalTransferService externalTransferService;

    /**
     * The GET html request findNewExternalTransfer method allows user with
     * administrator role to find all new transfers that need a bank transfer
     * (withdrawals and fees).
     *
     * @param submitDay a String
     * @return a List<ExternalTransferDTO>
     */
    @GetMapping("/pmb-adm/transfer")
    public List<GetTransferDTO> findNewExternalTransfer(
            @RequestParam final String submitDay) {

        LocalDate submitDate = LocalDate.parse(submitDay, formatter);

        List<GetTransferDTO> externalTransferList = externalTransferService
                .find(submitDate);
        return externalTransferList;

    }

}
