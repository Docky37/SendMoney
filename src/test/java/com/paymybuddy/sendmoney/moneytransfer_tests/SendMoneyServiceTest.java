package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.money_transfer.service.SendMoneyService;
import com.paymybuddy.sendmoney.money_transfer.service.SendMoneyServiceImpl;
import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * @author Thierry SCHREINER
 */
@SpringJUnitConfig(value=SendMoneyServiceImpl.class)
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
    static Buddy beneficiary2 = new Buddy();
    static {
        beneficiary2.setEmail("toto@pmb.com");
    }
    static Buddy appli = new Buddy();
    static {
        appli.setEmail("send.money@pmb.com");
    }
    static OrderDTO orderDTO = new OrderDTO(beneficiary.getEmail(), 100D,
            sender.getEmail());
    static OrderDTO orderDTO2 = new OrderDTO(beneficiary.getEmail(), 500D,
            sender.getEmail());
    static OrderDTO orderDTO3 = new OrderDTO(beneficiary2.getEmail(), 100D,
            sender.getEmail());
    static PmbAccount pmbAccountSender = new PmbAccount();
    static PmbAccount pmbAccountBeneficiary = new PmbAccount();
    static PmbAccount pmbAccountBeneficiary2 = new PmbAccount();
    static PmbAccount pmbAppliAccount = new PmbAccount();
    static Transfer transfer = new Transfer();
    static {
        pmbAccountBeneficiary.setPmbAccountNumber("PMB0000015");
        pmbAccountBeneficiary.setAccountBalance(350.00D);
        pmbAccountBeneficiary.setOwner(beneficiary);

        pmbAccountSender.setPmbAccountNumber("PMB0000007");
        pmbAccountSender.setAccountBalance(500.00D);
        pmbAccountSender.setOwner(sender);
        pmbAccountSender.setConnections(new TreeSet<PmbAccount>());
        pmbAccountSender.getConnections().add(pmbAccountBeneficiary);

        pmbAccountBeneficiary2.setPmbAccountNumber("PMB0000018");
        pmbAccountBeneficiary2.setAccountBalance(550.00D);
        pmbAccountBeneficiary2.setOwner(beneficiary2);

        pmbAppliAccount.setPmbAccountNumber("PMB--APPLI");
        pmbAppliAccount.setAccountBalance(2000.00D);
        pmbAppliAccount.setOwner(appli);

        transfer.setTransactionDate(new Date());
        transfer.setTransaction("Sending");
        transfer.setAmount(100D);
        transfer.setFee(0.5D);
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
        sendMoneyService.send(orderDTO);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(transferMapping).convertToEntity(any(TransferDTO.class));
        verify(transferRepository).save(any(Transfer.class));
    }
    
    @Test // With insufficient account balance
    public void givenBadOrderDTO_whenSend_thenStatus400()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccountSender, pmbAccountBeneficiary);
        given(transferMapping.convertToEntity(any(TransferDTO.class)))
                .willReturn(transfer);
        // WHEN
        sendMoneyService.send(orderDTO2);
        // THEN
        verify(pmbAccountRepository).findByOwnerEmail(anyString());
        verify(transferMapping, never()).convertToEntity(any(TransferDTO.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }
    
    @Test // With a non connected beneficiary
    public void givenNonConnectedBeneficiaryBadOrderDTO_whenSend_thenStatus400()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccountSender, pmbAccountBeneficiary2);
        given(transferMapping.convertToEntity(any(TransferDTO.class)))
                .willReturn(transfer);
        // WHEN
        sendMoneyService.send(orderDTO3);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(transferMapping, never()).convertToEntity(any(TransferDTO.class));
        verify(transferRepository, never()).save(any(Transfer.class));
    }
    
    @Test // With a valid orderDTO
    public void givenATransfer_whenSaveTransaction_thenAccountUpdated()
            throws Exception {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString())).willReturn(pmbAppliAccount);
        // WHEN
        sendMoneyService.saveTransaction(transfer);
        // THEN
        assertThat(pmbAccountSender.getAccountBalance()).isEqualTo(399.50D);
        assertThat(pmbAccountBeneficiary.getAccountBalance()).isEqualTo(450D);
        assertThat(pmbAppliAccount.getAccountBalance()).isEqualTo(2000.50D); 
        verify(pmbAccountRepository, times(3)).save(any(PmbAccount.class));
        verify(transferRepository).save(any(Transfer.class));
    }

}
