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
public class WithdrawalServiceImpl implements WithdrawalService {

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
    @Override
    public Transfer send(final OrderDTO orderDTO) {
        PmbAccount pmbAccountSender = pmbAccountRepository
                .findByOwnerEmail(orderDTO.getSender());
        PmbAccount pmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail(orderDTO.getBeneficiary());
        TransferDTO transferDTO = new TransferDTO(null, "Withdrawal",
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
     * {@inheritDoc}
     */
    @Override
    public String saveTransaction(final Transfer withdrawal) {

        PmbAccount beneficiaryAccount = withdrawal.getPmbAccountBeneficiary();
        beneficiaryAccount
                .setAccountBalance(beneficiaryAccount.getAccountBalance()
                        .add(withdrawal.getAmount()).add(withdrawal.getFee()));
        LOGGER.info("beneficiary AccountBalance = {}",
                beneficiaryAccount.getAccountBalance());

        PmbAccount pmbAppliAccount = withdrawal.getPmbAccountSender();
        pmbAppliAccount.setAccountBalance(pmbAppliAccount.getAccountBalance()
                .subtract(withdrawal.getAmount())
                .subtract(withdrawal.getFee()));
        LOGGER.info("pmbAppliAccount AccountBalance = {}",
                pmbAppliAccount.getAccountBalance());
        withdrawal.setEffective(true);
        withdrawal.setValueDate(new Date());

        try {
            pmbAccountRepository.save(beneficiaryAccount);
            pmbAccountRepository.save(pmbAppliAccount);
            transferRepository.save(withdrawal);
            response = response.concat(" The account balance of your & "
                    + " PMB account has been updated.");
            LOGGER.info(" The account balance of both appli & "
                    + "beneficiary PMB accounts have been updated.");
            return response;
        } catch (RuntimeException e) {
            LOGGER.info("An exception occurs during the update of an account"
                    + " balance. Currently, both sender & user accounts are not"
                    + " up to date of this withdrawal.");
            response = response.concat(
                    " But an exception occurs during the update of sender or"
                            + " beneficiary account balance");
            return response;
        }
    }

    /**
     * @return
     */
    @Override
    public String getResponse() {
        return response;
    }

}
