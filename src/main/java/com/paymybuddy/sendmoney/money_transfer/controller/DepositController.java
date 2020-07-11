/**
 * 
 */
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
import com.paymybuddy.sendmoney.money_transfer.service.DepositService;

/**
 * @author Thierry SCHREINER
 */
@RestController
public class DepositController {

    /**
     * Declare a DepositService object.
     */
    @Autowired
    private DepositService depositService;

    /**
     * The Post html request sendMoney method allows PMB application to send the
     * amount of a deposit on its beneficiary PmbAccount.
     *
     * @param orderDTO
     * @return a ResponseEntity<Object>
     */
    @PostMapping("/pmb-adm/deposit")
    public ResponseEntity<Object> sendMoney(
            @RequestBody final OrderDTO orderDTO) {
        orderDTO.setSender(PmbConstants.SEND_MONEY_EMAIL);
        Transfer transfer = depositService.send(orderDTO);
        String response;
        if (transfer != null) {
            response = depositService.saveTransaction(transfer);
        } else {
            response = depositService.getResponse();
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
