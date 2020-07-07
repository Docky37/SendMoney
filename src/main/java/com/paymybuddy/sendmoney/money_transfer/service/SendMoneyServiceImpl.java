package com.paymybuddy.sendmoney.money_transfer.service;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.sendmoney.PmbConstants;
import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;

/**
 * {@inheritDoc}.
 *
 * @author Thierry SCHREINER
 */
@Service
public class SendMoneyServiceImpl implements SendMoneyService {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory.getLogger("SendMoneyService");

    /**
     * Declaration of a TransferMapping object autowired by Spring's dependency
     * injection facilities.
     */
    @Autowired
    private TransferMapping transferMapping;

    /**
     * Declaration of a PmbAccountRepository interface autowired by Spring's
     * dependency injection facilities.
     */
    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    /**
     * Declaration of a TransferRepository interface autowired by Spring's
     * dependency injection facilities.
     */
    @Autowired
    private TransferRepository transferRepository;

    /**
     * Class variable used to build a response message that will be return to
     * the controller.
     */
    private String response = "";

    /**
     * {@inheritDoc}
     */
    public String getResponse() {
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transfer send(final OrderDTO orderDTO) {

        PmbAccount pmbAccountSender = pmbAccountRepository
                .findByOwnerEmail(orderDTO.getSender());
        if (pmbAccountSender.getAccountBalance() < 1
                + PmbConstants.FEE_RATE * orderDTO.getAmount()) {
            response = "400 Bad Request - "
                    + "Insufficient funds on PMB account for this transfer!";
            LOGGER.info(response);
            return null;
        }
        LOGGER.debug("Sufficient account balance.");
        PmbAccount pmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail(orderDTO.getBeneficiary());
        Set<PmbAccount> connections = pmbAccountSender.getConnections();
        if (!connections.contains(pmbAccountBeneficiary)) {
            response = "400 Bad Request - "
                    + "Beneficiary is not a valid connection of the user!";
            LOGGER.info(response);
            return null;
        }
        LOGGER.debug("Beneficiary is well connected to user!");
        TransferDTO transferDTO = new TransferDTO(null, "Sending",
                pmbAccountSender, pmbAccountBeneficiary, orderDTO.getAmount());
        transferDTO.setTransactionDate(new Date());
        LOGGER.debug("transferDTO = {}", transferDTO.toString());
        Transfer transfer = transferMapping.convertToEntity(transferDTO);
        LOGGER.debug("transfer = {}", transfer.toString());
        Transfer savedTransfer = transferRepository.save(transfer);

        response = "201 Created - Transfer done & saved.";
        LOGGER.info(response);
        return savedTransfer;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional
    public String saveTransaction(final Transfer transfer) {
        PmbAccount senderAccount = transfer.getPmbAccountSender();
        senderAccount.setAccountBalance(senderAccount.getAccountBalance()
                - transfer.getAmount() - transfer.getFee());
        LOGGER.info("sender AccountBalance = {}",
                senderAccount.getAccountBalance());

        PmbAccount beneficiaryAccount = transfer.getPmbAccountBeneficiary();
        beneficiaryAccount.setAccountBalance(
                beneficiaryAccount.getAccountBalance() + transfer.getAmount());

        try {
            pmbAccountRepository.save(senderAccount);
            pmbAccountRepository.save(beneficiaryAccount);
            response = response.concat(" The account balance of both sender & "
                    + " PMB accounts have been updated.");
            LOGGER.info(" The account balance of both sender & "
                    + "beneficiary PMB accounts have been updated.");
            return response;
        } catch (Exception e) {
            LOGGER.info("An exception occurs during the update of an account"
                    + " balance. Currently, both sender & user accounts are not"
                    + " up to date of this transfer.");
            response = response.concat(
                    " But an exception occurs during the update of sender or"
                            + " beneficiary account balance");
            return response;
        }
    }

}
