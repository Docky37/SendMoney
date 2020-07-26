package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.paymybuddy.sendmoney.money_transfer.model.GetTransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.model.TransferDTO;
import com.paymybuddy.sendmoney.money_transfer.model.mapping.TransferMapping;
import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * @author Thierry SCHREINER
 */
@SpringJUnitConfig(value = TransferMapping.class)
public class TransferMappingTest {

    @Autowired
    TransferMapping transferMapping;

    // Test Data ************************************************
    static Buddy sender = new Buddy();
    static {
        sender.setEmail("sender@pmb.com");
    }
    static Buddy beneficiary = new Buddy();
    static {
        beneficiary.setEmail("beneficiary@pmb.com");
    }
    static PmbAccount pmbAccountSender = new PmbAccount();
    static PmbAccount pmbAccountBeneficiary = new PmbAccount();
    static Transfer transfer = new Transfer();
    static Transfer transfer2 = new Transfer();
    static Date transactionDate = new Date();
    static BankAccount bankAcc = new BankAccount();

    static {
        pmbAccountSender.setPmbAccountNumber("PMB0000007");
        pmbAccountSender.setAccountBalance(new BigDecimal("500.00"));
        pmbAccountSender.setOwner(sender);
        pmbAccountSender.setConnections(new TreeSet<PmbAccount>());

        pmbAccountBeneficiary.setPmbAccountNumber("PMB0000015");
        pmbAccountBeneficiary.setAccountBalance(new BigDecimal("350.00"));
        pmbAccountBeneficiary.setOwner(beneficiary);

        pmbAccountSender.getConnections().add(pmbAccountBeneficiary);

        bankAcc.setIban("FR3330002005500000157841Z25");
        bankAcc.setSwift("CRLYFRPPXXX");
        bankAcc.setOwner(beneficiary);
    }
    static TransferDTO transferDTO = new TransferDTO(transactionDate, "Sending",
            "Test", pmbAccountSender, pmbAccountBeneficiary,
            new BigDecimal("100.00"));
    static List<Transfer> transferList = new ArrayList<Transfer>();
    static {
        transfer.setTransactionDate(transactionDate);
        transfer.setTransaction("Sending");
        transfer.setAmount(new BigDecimal("100.00"));
        transfer.setFee(new BigDecimal("0.5"));
        transfer.setPmbAccountBeneficiary(pmbAccountBeneficiary);
        transfer.setPmbAccountSender(pmbAccountSender);
        transfer.setValueDate(transactionDate);
        transfer2.setTransactionDate(transactionDate);
        transfer2.setTransaction("Withdrawal");
        transfer2.setAmount(new BigDecimal("200.00"));
        transfer2.setFee(new BigDecimal("1.00"));
        transfer2.setPmbAccountBeneficiary(pmbAccountBeneficiary);
        transfer2.setPmbAccountSender(pmbAccountSender);
        transfer2.setValueDate(transactionDate);
        transfer2.setBankAccountBeneficiary(bankAcc);
        transferList.add(transfer);
        transferList.add(transfer2);
    }

    @Test // convertToEntity(transferDTO)
    public void givenATransferDTO_whenConvertToEntity_thenTransferIsCreated()
            throws Exception {
        // GIVEN
        // WHEN
        Transfer transfer = transferMapping.convertToEntity(transferDTO);
        // THEN
        assertThat(transfer.getTransactionDate()).isEqualTo(transactionDate);
        assertThat(transfer.getFee()).isEqualTo(new BigDecimal("0.50"));
        assertThat(transfer.getTransaction()).isEqualTo("Sending");
    }

    @Test // convertToEntity(transferDTO)
    public void givenATransferList_whenMapToDTO_thenGetTransferDTOListIsCreated()
            throws Exception {
        // GIVEN
        // WHEN
        List<GetTransferDTO> transferDTOList = transferMapping
                .mapTransferListToDTO(transferList);
        // THEN
        assertThat(transferDTOList.size()).isEqualTo(2);
        assertThat(transferDTOList.get(0).getAmount())
                .isEqualTo(new BigDecimal("100.00"));
        assertThat(transferDTOList.get(1).getAmount())
                .isEqualTo(new BigDecimal("200.00"));
        assertThat(transferDTOList.get(1).getFee())
                .isEqualTo(new BigDecimal("1.00"));
        assertThat(transferDTOList.get(1).getBankAccountIban())
                .isEqualTo("FR3330002005500000157841Z25");

    }

}
