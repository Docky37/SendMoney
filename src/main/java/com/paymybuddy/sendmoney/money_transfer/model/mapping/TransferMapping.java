package com.paymybuddy.sendmoney.money_transfer.model.mapping;

import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;

/**
 * @author Thierry SCHREINER
 */
@Component
public class TransferMapping {

    /**
     * This constant defines the value of the fee rate of PMB transfer.
     */
    public static final double FEE_RATE = 0.005; // 0.5%
    
    /**
    /**
     * This constant contains the value of 100.
     */
    public static final double CENT = 100;
    
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
        transfer.setFee(Math.rint(transfer.getAmount()*FEE_RATE*CENT)/CENT);
        return transfer;
    }

}
