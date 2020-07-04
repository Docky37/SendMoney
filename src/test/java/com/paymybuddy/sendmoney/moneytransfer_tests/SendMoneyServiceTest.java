package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.TreeSet;

import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.money_transfer.service.SendMoneyService;
import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * @author Thierry SCHREINER
 */
@SpringBootTest("SendMoneyServiceImpl.class")
public class SendMoneyServiceTest {

    @Autowired
    private SendMoneyService sendMoneyService;

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
        sender.setEmail("sender@pmb.com");
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
        pmbAccountSender.setPmbAccountNumber("PMB0000007");
        pmbAccountSender.setAccountBalance(500.00D);
        pmbAccountSender.setOwner(sender);
        pmbAccountSender.setConnections(new TreeSet<PmbAccount>());
        pmbAccountSender.getConnections().add(pmbAccountBeneficiary);

        pmbAccountBeneficiary.setPmbAccountNumber("PMB0000015");
        pmbAccountBeneficiary.setAccountBalance(350.00D);
        pmbAccountBeneficiary.setOwner(beneficiary);

        transfer.setAmount(100D);
        transfer.setFee(0.5D);
        transfer.setPmbAccountBeneficiary(pmbAccountBeneficiary);
        transfer.setPmbAccountSender(pmbAccountSender);
    }

    @Test // With a valid orderDTO
    public void givenAnOrderDTO_whenSend_thenTransferIsAdded()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccountSender, pmbAccountBeneficiary);
        given(transferMapping.convertToEntity(any(TransferDTO.class)))
                .willReturn(new Transfer());
        // WHEN
        sendMoneyService.send(orderDTO);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(transferMapping).convertToEntity(any(TransferDTO.class));
        verify(transferRepository).save(any(Transfer.class));
        // verify(pmbAccountRepository, times(2)).save(any(PmbAccount.class));
    }

    @Test // With a valid orderDTO
    public void givenATransfer_whenSaveTransaction_thenAccountUpdated()
            throws Exception {
        // GIVEN

        // WHEN
        sendMoneyService.saveTransaction(transfer);
        // THEN
        assertThat(pmbAccountSender.getAccountBalance()).isEqualTo(399.50);
        //verify(transferRepository).save(any(Transfer.class));
        //verify(pmbAccountRepository, times(2)).save(any(PmbAccount.class));
    }

}
