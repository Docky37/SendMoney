package com.paymybuddy.sendmoney.moneyaccounts.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.service.ConnectionService;

/**
 * This controller is in charge of the Pmb account connections.
 *
 * @author Thierry SCHREINER
 */
@RestController
public class ConnectionController {

    /**
     * Declare a ConnectionService object.
     */
    @Autowired
    private ConnectionService connectionService;

    @PostMapping("/connection")
    public ResponseEntity<Object> addConnection(
            @RequestBody final String email) {
        PmbAccount pmbAccount = connectionService.addConnection(email);

        if (pmbAccount != null) {
            return new ResponseEntity<Object>(
                    "Connection done, the PMB account of '" + email
                            + "' has been added as beneficiary.",
                    new HttpHeaders(), HttpStatus.CREATED);

        }
        return new ResponseEntity<Object>(
                "No PMB account founded for '" + email + "'!",
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/connection")
    public Set<PmbAccount> getConnections() {
        Set<PmbAccount> connections = connectionService.getConnections();

        if (connections != null) {
            return connections;

        }
        return null;
    }

}
