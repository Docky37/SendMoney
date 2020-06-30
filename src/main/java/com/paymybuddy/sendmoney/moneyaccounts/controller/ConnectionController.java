package com.paymybuddy.sendmoney.moneyaccounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.EmailDTO;
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
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger("ConnectionController");

    /**
     * Declare a ConnectionService object.
     */
    @Autowired
    private ConnectionService connectionService;

    /**
     * POST html request used to add a connection between current user's PMB
     * account and another buddy's one.
     *
     * @param emailDTO
     * @return a ResponseEntity<Object>
     * @throws UserWithoutPmbAccountException
     */
    @PostMapping("/connection")
    public ResponseEntity<Object> addConnection(
            @RequestBody final EmailDTO emailDTO)
            throws UserWithoutPmbAccountException {
        LOGGER.info("NEW POST REQUEST('/connection') ************************");
        LOGGER.info(" -> body: {}", emailDTO.getEmail());
        PmbAccountDTO pmbAccount = connectionService
                .addConnection(emailDTO.getEmail());

        if (pmbAccount != null) {
            LOGGER.info(
                    "POST REQUEST END: 201 Created **************************");
            return new ResponseEntity<Object>(
                    "Connection done, the PMB account of '"
                            + emailDTO.getEmail()
                            + "' has been added as beneficiary.",
                    new HttpHeaders(), HttpStatus.CREATED);

        }
        LOGGER.info("POST REQUEST END: 404 Not Found ************************");
        return new ResponseEntity<Object>(
                "No PMB account founded for '" + emailDTO.getEmail() + "'!",
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE html request used to remove an existing connection between current
     * user's PMB account and another buddy's one.
     *
     * @param email
     * @return a ResponseEntity<Object>
     * @throws Throwable
     */
    @DeleteMapping("/connection/{email}")
    public ResponseEntity<Object> delConnection(
            @PathVariable final String email)
            throws UserWithoutPmbAccountException {
        LOGGER.info("NEW DELETE REQUEST('/connection/{email}') **************");
        LOGGER.info(" -> PathVariable: {}", email);
        PmbAccountDTO pmbAccount = connectionService.delConnection(email);

        if (pmbAccount != null) {
            LOGGER.info(
                    "DELETE REQUEST END: 200 Ok *****************************");
            return new ResponseEntity<Object>(
                    "Deconnection is done, the PMB account of '" + email
                            + "' has been removed as beneficiary.",
                    new HttpHeaders(), HttpStatus.OK);

        }
        LOGGER.info("DELETE REQUEST END: 404 Not Found **********************");
        return new ResponseEntity<Object>(
                "No PMB account founded for '" + email + "'!",
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * GET html request used to get a list of the connected buddy's PMB account
     * of current user's.
     *
     * @return the PmbAccountDTO of the current user
     * @throws Throwable
     */
    @GetMapping("/connection")
    public PmbAccountDTO getConnections()
            throws UserWithoutPmbAccountException {
        LOGGER.info("NEW GET REQUEST('/connection') *************************");
        PmbAccountDTO myPmbAccount = connectionService.getConnections();

        if (myPmbAccount != null) {
            LOGGER.info("GET REQUEST END: 200 Ok");
            return myPmbAccount;
        } else {
            throw new UserWithoutPmbAccountException();
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<Object> userWithoutPmbAccountException(
            final UserWithoutPmbAccountException e) {
        LOGGER.info("No PMB account founded for current logged user!");
        LOGGER.info("END OF REQUEST: UserWithoutPmbAccountException *********");
        return new ResponseEntity<Object>(
                "No PMB account founded for current user!", new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

}
