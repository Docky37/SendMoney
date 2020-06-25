package com.paymybuddy.sendmoney.moneyaccounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
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

    /**
     * POST html request used to add a connection between current user's PMB
     * account and another buddy's one.
     *
     * @param email
     * @return a ResponseEntity<Object>
     */
    @PostMapping("/connection")
    public ResponseEntity<Object> addConnection(
            @RequestBody final String email) {
        PmbAccountDTO pmbAccount = connectionService.addConnection(email);

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

    /**
     * DELETE html request used to remove an existing connection between current
     * user's PMB account and another buddy's one.
     *
     * @param email
     * @return a ResponseEntity<Object>
     */
    @DeleteMapping("/deconnection/{email}")
    public ResponseEntity<Object> delConnection(
            @PathVariable final String email) {
        PmbAccountDTO pmbAccount = connectionService.delConnection(email);

        if (pmbAccount != null) {
            return new ResponseEntity<Object>(
                    "Deconnection is done, the PMB account of '" + email
                            + "' has been removed as beneficiary.",
                    new HttpHeaders(), HttpStatus.OK);

        }
        return new ResponseEntity<Object>(
                "No PMB account founded for '" + email + "'!",
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * GET html request used to get a list of the connected buddy's PMB account
     * of current user's.
     *
     * @return the PmbAccountDTO of the current user
     */
    @GetMapping("/connection")
    public PmbAccountDTO getConnections() {
        PmbAccountDTO myPmbAccount = connectionService.getConnections();

        if (myPmbAccount != null) {
            return myPmbAccount;

        }
        return null;
    }

}
