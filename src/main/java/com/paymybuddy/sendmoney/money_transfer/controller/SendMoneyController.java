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

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.service.SendMoneyService;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;

/**
 * @author Thierry SCHREINER
 *
 */
@RestController
public class SendMoneyController {

    /**
     * Declare a SendMoneyService object.
     */
    @Autowired
    private SendMoneyService sendMoneyService;

    /**
     * Utility class used to retrieve the email of the current logged user.
     */
    @Autowired
    private EmailRetrieve emailRetrieve;

    @PostMapping("/send-money")
    public ResponseEntity<Object> sendMoney(
            @RequestBody final OrderDTO orderDTO) {
        orderDTO.setSender(emailRetrieve.getEmail());
        sendMoneyService.send(orderDTO);
        return new ResponseEntity<Object>("Transfer done & saved.",
                new HttpHeaders(), HttpStatus.CREATED);
    }

}
