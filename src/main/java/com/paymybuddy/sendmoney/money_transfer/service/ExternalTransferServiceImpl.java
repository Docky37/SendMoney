
package com.paymybuddy.sendmoney.money_transfer.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;

/**
 * @author Thierry SCHREINER
 */
@Service
public class ExternalTransferServiceImpl implements ExternalTransferService {

    /**
     * Declare an autowired TransferRepository object.
     */
    @Autowired
    private TransferRepository transferRepository;

    /**
     * Declare an autowired TransferMapping object.
     */
    @Autowired
    private TransferMapping transferMapping;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GetTransferDTO> find(final LocalDate pSubmitDate) {

        Date submitDate = Date.valueOf(pSubmitDate);
        List<Transfer> transferList = transferRepository
                .findByValueDate(submitDate);
        List<GetTransferDTO> getTransferDTOList = transferMapping
                .mapTransferListToDTO(transferList);
        return getTransferDTOList;
    }

}
