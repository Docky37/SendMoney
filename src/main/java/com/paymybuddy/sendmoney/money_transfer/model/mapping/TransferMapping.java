package com.paymybuddy.sendmoney.money_transfer.model.mapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.paymybuddy.sendmoney.PmbConstants;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;

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

    public List<GetTransferDTO> mapTransferListToDTO(
            final List<Transfer> transferList) {
        List<GetTransferDTO> transfertDTOList = new ArrayList<GetTransferDTO>();
        GetTransferDTO transferDTO;
        for (Transfer transfer : transferList) {
            transferDTO = mapTransferToDTO(transfer);
            transfertDTOList.add(transferDTO);
        }
        return transfertDTOList;
    }

    /**
     * @param transfer
     * @return
     */
    private GetTransferDTO mapTransferToDTO(final Transfer transfer) {
        // TODO Auto-generated method stub
        GetTransferDTO transferDTO = new GetTransferDTO();
        transferDTO.setValueDate(transfer.getValueDate());
        transferDTO.setTransaction(transfer.getTransaction());
        transferDTO.setAmount(transfer.getAmount());
        transferDTO.setFee(transfer.getFee());
        transferDTO.setBeneficiary(
                transfer.getPmbAccountBeneficiary().getOwner().getEmail());
        transferDTO.setSender(
                transfer.getPmbAccountSender().getOwner().getEmail());
        return transferDTO;
    }
}
