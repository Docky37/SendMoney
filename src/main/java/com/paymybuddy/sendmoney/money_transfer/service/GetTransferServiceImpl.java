package com.paymybuddy.sendmoney.money_transfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;

/**
 * @{inheritDoc}
 *
 * @author Thierry SCHREINER
 */
@Service
public class GetTransferServiceImpl implements GetTransferService {

    /**
     * Declare a Spring autowired TransferRepository instance.
     */
    @Autowired
    private TransferRepository transferRepository;

    /**
     * Declare a Spring autowired TransferMapping instance.
     */
    @Autowired
    private TransferMapping transferMapping;

    /**
     * @{inheritDoc}
     *
     * @return a GetTransferDTO object
     */
    @Override
    public List<GetTransferDTO> findMyTransfer(final String email) {
        List<Transfer> transferList = transferRepository.findByEmail(email);
        if (transferList.get(0) != null) {
            List<GetTransferDTO> transferDTOList = transferMapping
                    .mapTransferListToDTO(transferList);
            return transferDTOList;
        }
        return null;
    }

}
