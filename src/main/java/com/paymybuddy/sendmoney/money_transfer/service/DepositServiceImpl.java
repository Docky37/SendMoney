package com.paymybuddy.sendmoney.money_transfer.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;

/**
 * @author Thierry SCHREINER
 *
 */
@Service
public class DepositServiceImpl implements DepositService {

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

    @Override
    public Transfer send(final OrderDTO orderDTO) {

        PmbAccount pmbAccountSender = pmbAccountRepository
                .findByOwnerEmail(orderDTO.getSender());
        PmbAccount pmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail(orderDTO.getBeneficiary());
        TransferDTO transferDTO = new TransferDTO(null, "Deposit",
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

    @Override
    public String saveTransaction(Transfer deposit) {
        // TODO Auto-generated method stub
        return null;
    }

}
