package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.anyString;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.money_transfer.service.GetTransferService;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;

/**
 * @author Thierry SCHREINER
 */
@SpringBootTest("GetTransferServiceImpl.class")
public class GetTransferServiceTest {

    @Autowired
    private GetTransferService getTransferService;

    @MockBean
    private TransferMapping transferMapping;

    @MockBean
    private PmbAccountMapping pmbAccountMapping;

    @MockBean
    private TransferRepository transferRepository;

    static Transfer transfer = new Transfer();
    static List<Transfer> transferList = new ArrayList<Transfer>();
    static {
        transfer.setTransactionDate(new Date());
        transfer.setAmount(100D);
        transfer.setFee(0.5D);
        transferList.add(transfer);
    }

    @Test //
    public void givenAEmail_whenFindMyTransfer_thenReturnsGetTransferDTOList()
            throws Exception {
        // GIVEN
        given(transferRepository.findByEmail(anyString()))
                .willReturn(transferList);
        given(transferMapping.mapTransferListToDTO(transferList))
                .willReturn(new ArrayList<GetTransferDTO>());
        // WHEN
        getTransferService.findMyTransfer("User.Test@email.fr");
        // THEN
        verify(transferRepository).findByEmail(anyString());
        verify(transferMapping)
                .mapTransferListToDTO(Mockito.<Transfer>anyList());
    }

}
