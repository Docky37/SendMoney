package com.paymybuddy.sendmoney.money_transfer.model.mapping;

import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.PmbConstants;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;

/**
 * @author Thierry SCHREINER
 */
@Component
public class TransferMapping {
    /**
     * This method create a Transfer entity object and fill its fields with the
     * values carried by the given transferDTO object.
     *
     * @param transferDTO
     * @return a Transfer object
     */
    public Transfer convertToEntity(final TransferDTO transferDTO) {
        Transfer transfer = new Transfer();
        transfer.setTransactionDate(transferDTO.getTransactionDate());
        transfer.setTransaction(transferDTO.getTransaction());
        transfer.setPmbAccountSender(transferDTO.getPmbAccountSender());
        transfer.setPmbAccountBeneficiary(
                transferDTO.getPmbAccountBeneficiary());
        transfer.setAmount(transferDTO.getAmount());
        transfer.setFee(Math.rint(transfer.getAmount() * PmbConstants.FEE_RATE
                * PmbConstants.CENT) / PmbConstants.CENT);
        return transfer;
    }

}
