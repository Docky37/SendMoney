package com.paymybuddy.sendmoney.money_transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.PmbConstants;
import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.service.WithdrawalService;

/**
 * @author Thierry SCHREINER
 */
@RestController
public class WithdrawalController {

    /**
     * Declare a WithdrawalService object.
     */
    @Autowired
    private WithdrawalService withdrawalService;

    /**
     * The Post html request sendMoney method allows logged user to order a
     * a Withdrawal (a transfer from his PmbAccount to his bankAccount.)
     *
     * @param orderDTO
     * @return a ResponseEntity<Object>
     */
    @PostMapping("/withdrawal")
    public ResponseEntity<Object> sendMoney(
            @RequestBody final OrderDTO orderDTO) {
        orderDTO.setSender(PmbConstants.SEND_MONEY_EMAIL);
        Transfer transfer = withdrawalService.send(orderDTO);
        String response;
        if (transfer != null) {
            response = withdrawalService.saveTransaction(transfer);
        } else {
            response = withdrawalService.getResponse();
        }

        if (response.contains("201")) {
            return new ResponseEntity<Object>(response, new HttpHeaders(),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>(response, new HttpHeaders(),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
