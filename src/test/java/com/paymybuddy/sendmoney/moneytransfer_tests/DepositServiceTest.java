package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.PmbConstants;
import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.money_transfer.service.DepositService;
import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * @author Thierry SCHREINER
 */
@SpringBootTest("DepositServiceImpl.class")
public class DepositServiceTest {
    @Autowired
    private DepositService depositService;

    @MockBean
    private TransferMapping transferMapping;

    @MockBean
    private PmbAccountMapping pmbAccountMapping;

    @MockBean
    private TransferRepository transferRepository;

    @MockBean
    private PmbAccountRepository pmbAccountRepository;

    // Test Data ************************************************
    static Buddy sender = new Buddy();
    static {
        sender.setEmail(PmbConstants.SEND_MONEY_EMAIL);
    }
    static Buddy beneficiary = new Buddy();
    static {
        beneficiary.setEmail("beneficiary@pmb.com");
    }
    static OrderDTO orderDTO = new OrderDTO(beneficiary.getEmail(), 100D,
            sender.getEmail());
    static PmbAccount pmbAccountSender = new PmbAccount();
    static PmbAccount pmbAccountBeneficiary = new PmbAccount();
    static Transfer transfer = new Transfer();
    static {
        pmbAccountBeneficiary.setPmbAccountNumber("PMB0000015");
        pmbAccountBeneficiary.setAccountBalance(57D);
        pmbAccountBeneficiary.setOwner(beneficiary);

        pmbAccountSender.setPmbAccountNumber("PMB--APPLI");
        pmbAccountSender.setAccountBalance(5215.00D);
        pmbAccountSender.setOwner(sender);

        transfer.setTransactionDate(new Date());
        transfer.setAmount(100D);
        transfer.setFee(0D);
        transfer.setPmbAccountBeneficiary(pmbAccountBeneficiary);
        transfer.setPmbAccountSender(pmbAccountSender);
        transfer.setValueDate(new Date());
    }

    @Test // With a valid orderDTO
    public void givenAnOrderDTO_whenSend_thenTransferIsAdded()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccountSender, pmbAccountBeneficiary);
        given(transferMapping.convertToEntity(any(TransferDTO.class)))
                .willReturn(transfer);
        // WHEN
        depositService.send(orderDTO);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(transferMapping).convertToEntity(any(TransferDTO.class));
        verify(transferRepository).save(any(Transfer.class));
    }

}
