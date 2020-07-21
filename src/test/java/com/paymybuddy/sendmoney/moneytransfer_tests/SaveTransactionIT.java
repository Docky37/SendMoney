package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.service.DepositService;
import com.paymybuddy.sendmoney.money_transfer.service.SendMoneyService;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;

/**
 * @author Thierry SCHREINER
 *
 */
@SpringBootTest
public class SaveTransactionIT {
    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory.getLogger("SendMoneyService");

    /**
     * Declaration of a TransferMapping object autowired by Spring's dependency
     * injection facilities.
     */

    @Autowired
    private DepositService depositService;

    @Autowired
    private SendMoneyService sendMoneyService;

    @Autowired
    private PmbAccountRepository pmbAccountRepository;

    private Transfer transfer = new Transfer();
    private Transfer deposit = new Transfer();

    @BeforeEach
    void init() {
        PmbAccount pmbAccountSender = pmbAccountRepository
                .findByOwnerEmail("Daniel.Craig@JamesBond.fr");
        pmbAccountSender.setAccountBalance(new BigDecimal("749"));
        pmbAccountRepository.save(pmbAccountSender);
        PmbAccount pmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail("Al.Pacino@Hollywood.com");
        pmbAccountBeneficiary.setAccountBalance(new BigDecimal("777"));
        pmbAccountRepository.save(pmbAccountBeneficiary);
        PmbAccount pmbAppliAccount = pmbAccountRepository
                .findByOwnerEmail("send.money@pmb.com");
        pmbAppliAccount.setAccountBalance(new BigDecimal("2000"));
        pmbAccountRepository.save(pmbAppliAccount);
        transfer.setTransactionDate(new Date());
        transfer.setAmount(new BigDecimal("100"));
        transfer.setFee(new BigDecimal("0.5"));
        transfer.setPmbAccountBeneficiary(pmbAccountBeneficiary);
        transfer.setPmbAccountSender(pmbAccountSender);

        deposit.setTransactionDate(new Date());
        deposit.setAmount(new BigDecimal("100"));
        deposit.setFee(BigDecimal.ZERO);
        deposit.setPmbAccountBeneficiary(pmbAccountBeneficiary);
        deposit.setPmbAccountSender(pmbAppliAccount);

    }

    @Test // Saving transfer transaction succeeds.
    public void givenATransfer_whenSaveTransaction_thenTransactionSaved()
            throws Exception {
        // GIVEN
        System.out.println(transfer.toString());

        // WHEN
        sendMoneyService.saveTransaction(transfer);
        // THEN
        PmbAccount checkPmbAccountSender = pmbAccountRepository
                .findByOwnerEmail("Daniel.Craig@JamesBond.fr");
        assertThat(checkPmbAccountSender.getAccountBalance())
                .isEqualTo(new BigDecimal("648.5"));
        PmbAccount checkPmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail("Al.Pacino@Hollywood.com");
        assertThat(checkPmbAccountBeneficiary.getAccountBalance())
                .isEqualTo(new BigDecimal("877"));
        PmbAccount checkPmbAppliAccount = pmbAccountRepository
                .findByOwnerEmail("send.money@pmb.com");
        assertThat(checkPmbAppliAccount.getAccountBalance())
                .isEqualTo(new BigDecimal("2000.5"));
    }

    @Test // Saving transfer transaction fails.
    public void givenATransfer_whenSaveTransactionFails_thenRollBack()
            throws Exception {
        // GIVEN
        transfer.getPmbAccountSender().setId(-1);
        // WHEN
        assertThrows(Exception.class, () -> {
            depositService.saveTransaction(transfer);
        });
        // THEN
        PmbAccount checkPmbAccountSender = pmbAccountRepository
                .findByOwnerEmail("Daniel.Craig@JamesBond.fr");
        assertThat(checkPmbAccountSender.getAccountBalance())
                .isEqualTo(new BigDecimal("749"));
        PmbAccount checkPmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail("Al.Pacino@Hollywood.com");
        assertThat(checkPmbAccountBeneficiary.getAccountBalance())
                .isEqualTo(777.0);
        PmbAccount checkPmbAppliAccount = pmbAccountRepository
                .findByOwnerEmail("send.money@pmb.com");
        assertThat(checkPmbAppliAccount.getAccountBalance())
                .isEqualTo(new BigDecimal("2000"));
    }

    @Test // Saving deposit transaction succeeds.
    public void givenADeposit_whenSaveTransaction_thenTransactionSaved()
            throws Exception {
        // GIVEN
        System.out.println(deposit.toString());
        // WHEN
        depositService.saveTransaction(deposit);
        // THEN
        PmbAccount checkPmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail("Al.Pacino@Hollywood.com");
        assertThat(checkPmbAccountBeneficiary.getAccountBalance())
                .isEqualTo(new BigDecimal("877"));
        PmbAccount checkPmbAppliAccount = pmbAccountRepository
                .findByOwnerEmail("send.money@pmb.com");
        assertThat(checkPmbAppliAccount.getAccountBalance())
                .isEqualTo(new BigDecimal("1900"));
    }

    @Test // Saving deposit transaction fails.
    public void givenADeposit_whenSaveTransactionFails_thenRollBack()
            throws Exception {
        // GIVEN
        deposit.getPmbAccountSender().setId(-1);
        // WHEN
        assertThrows(Exception.class, () -> {
            depositService.saveTransaction(deposit);
        });
        // THEN
        PmbAccount checkPmbAccountBeneficiary = pmbAccountRepository
                .findByOwnerEmail("Al.Pacino@Hollywood.com");
        assertThat(checkPmbAccountBeneficiary.getAccountBalance())
                .isEqualTo(new BigDecimal("777"));
        PmbAccount checkPmbAppliAccount = pmbAccountRepository
                .findByOwnerEmail("send.money@pmb.com");
        assertThat(checkPmbAppliAccount.getAccountBalance())
                .isEqualTo(new BigDecimal("2000"));
    }

}
