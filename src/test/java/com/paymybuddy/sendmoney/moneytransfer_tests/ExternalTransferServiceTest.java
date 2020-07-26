package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.money_transfer.service.ExternalTransferServiceImpl;
import com.paymybuddy.sendmoney.money_transfer.service.ExternalTransferService;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;

/**
 * @author Thierry SCHREINER
 */
@SpringJUnitConfig(value = ExternalTransferServiceImpl.class)
public class ExternalTransferServiceTest {

    @Autowired
    private ExternalTransferService externalTransferService;

    @MockBean
    private TransferMapping transferMapping;

    @MockBean
    private PmbAccountMapping pmbAccountMapping;

    @MockBean
    private TransferRepository transferRepository;

    static Transfer transfer = new Transfer();
    static List<Transfer> transferList = new ArrayList<Transfer>();
    static {
        transfer.setTransactionDate(new java.util.Date());
        transfer.setAmount(new BigDecimal("100"));
        transfer.setFee(new BigDecimal("0.5"));
        transferList.add(transfer);
    }

    @Test //
    public void givenAEmail_whenFindMyTransfer_thenReturnsGetTransferDTOList()
            throws Exception {
        // GIVEN
        LocalDate submitLocalDate = LocalDate.now();
        Date submitDate = Date.valueOf(submitLocalDate);
        given(transferRepository.findByValueDate(submitDate))
                .willReturn(transferList);
        given(transferMapping.mapTransferListToDTO(transferList))
                .willReturn(new ArrayList<GetTransferDTO>());
        // WHEN
        externalTransferService.find(submitLocalDate);
        // THEN
        verify(transferRepository).findByValueDate(submitDate);
        verify(transferMapping)
                .mapTransferListToDTO(Mockito.<Transfer>anyList());
    }

}
