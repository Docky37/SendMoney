package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.paymybuddy.sendmoney.PmbConstants;
import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.money_transfer.repository.TransferRepository;
import com.paymybuddy.sendmoney.money_transfer.service.DepositService;
import com.paymybuddy.sendmoney.money_transfer.service.DepositServiceImpl;
import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * @author Thierry SCHREINER
 */
@SpringJUnitConfig(value = DepositServiceImpl.class)
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
    static Buddy beneficiary = new Buddy();
    static {
        beneficiary.setEmail("beneficiary@pmb.com");
    }
    static Buddy appli = new Buddy();
    static {
        appli.setEmail("send.money@pmb.com");
    }
    static OrderDTO orderDTO = new OrderDTO(beneficiary.getEmail(),
            PmbConstants.HUNDRED, appli.getEmail());
    static PmbAccount pmbAccountBeneficiary = new PmbAccount();
    static PmbAccount pmbAppliAccount = new PmbAccount();
    static Transfer transfer = new Transfer();
    static {
        pmbAccountBeneficiary.setPmbAccountNumber("PMB0000015");

        pmbAccountBeneficiary.setOwner(beneficiary);

        pmbAppliAccount.setPmbAccountNumber("PMB--APPLI");
        pmbAppliAccount.setOwner(appli);

        transfer.setTransactionDate(new Date());
        transfer.setAmount(PmbConstants.HUNDRED);
        transfer.setFee(BigDecimal.ZERO);
        transfer.setPmbAccountBeneficiary(pmbAccountBeneficiary);
        transfer.setPmbAccountSender(pmbAppliAccount);
        transfer.setValueDate(new Date());
    }

    @BeforeEach
    private void init() {
        pmbAccountBeneficiary.setAccountBalance(new BigDecimal("57"));
        pmbAppliAccount.setAccountBalance(new BigDecimal("2000"));
    }

    @Test // With a valid orderDTO
    public void givenAnOrderDTO_whenSend_thenTransferIsAdded()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccountBeneficiary, pmbAppliAccount);
        given(transferMapping.convertToEntity(any(TransferDTO.class)))
                .willReturn(transfer);
        // WHEN
        depositService.send(orderDTO);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(transferMapping).convertToEntity(any(TransferDTO.class));
        verify(transferRepository).save(any(Transfer.class));
    }

    @Test // Without problem
    public void givenATransfer_whenSaveTransaction_thenAccountUpdated()
            throws Exception {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAppliAccount);
        // WHEN
        depositService.saveTransaction(transfer);
        // THEN
        ArgumentCaptor<PmbAccount> argument = ArgumentCaptor
                .forClass(PmbAccount.class);
        verify(pmbAccountRepository, times(2)).save(argument.capture());
        List<PmbAccount> arguments = argument.getAllValues();
        assertThat(arguments.get(0).getAccountBalance())
                .isEqualTo(new BigDecimal("157.00"));
        assertThat(arguments.get(1).getAccountBalance())
                .isEqualTo(new BigDecimal("1900.00"));
        verify(transferRepository).save(any(Transfer.class));
    }

    @Test // With exception
    public void givenATransfer_whenSaveTransaction_thenRollBack()
            throws Exception {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAppliAccount);
        given(pmbAccountRepository.save(pmbAppliAccount))
                .willThrow(new RuntimeException());
        // WHEN
        depositService.saveTransaction(transfer);
        // THEN
        ArgumentCaptor<PmbAccount> argument = ArgumentCaptor
                .forClass(PmbAccount.class);
        verify(pmbAccountRepository, times(2)).save(argument.capture());
        List<PmbAccount> arguments = argument.getAllValues();
        assertThat(arguments.get(0).getAccountBalance())
                .isEqualTo(new BigDecimal("157.00"));
        assertThat(arguments.get(1).getAccountBalance())
                .isEqualTo(new BigDecimal("1900.00"));
    }

}
