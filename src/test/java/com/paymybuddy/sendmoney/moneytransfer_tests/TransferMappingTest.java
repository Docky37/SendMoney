package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * @author Thierry SCHREINER
 */
@SpringJUnitConfig(value=TransferMapping.class)
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
    static {
        pmbAccountSender.setPmbAccountNumber("PMB0000007");
        pmbAccountSender.setAccountBalance(500.00D);
        pmbAccountSender.setOwner(sender);
        pmbAccountSender.setConnections(new TreeSet<PmbAccount>());

        pmbAccountBeneficiary.setPmbAccountNumber("PMB0000015");
        pmbAccountBeneficiary.setAccountBalance(350.00D);
        pmbAccountBeneficiary.setOwner(beneficiary);
        
        pmbAccountSender.getConnections().add(pmbAccountBeneficiary);
   }
   static TransferDTO transferDTO = new TransferDTO(transactionDate, "Sending",
            pmbAccountSender, pmbAccountBeneficiary, 100D);
   static List<Transfer> transferList = new ArrayList<Transfer>();
   static {
       transfer.setTransactionDate(transactionDate);
       transfer.setAmount(100D);
       transfer.setFee(0.5D);
       transfer.setPmbAccountBeneficiary(pmbAccountBeneficiary);
       transfer.setPmbAccountSender(pmbAccountSender);
       transfer.setValueDate(transactionDate);
       transfer2.setTransactionDate(transactionDate);
       transfer2.setAmount(200D);
       transfer2.setFee(1D);
       transfer2.setPmbAccountBeneficiary(pmbAccountBeneficiary);
       transfer2.setPmbAccountSender(pmbAccountSender);
       transfer2.setValueDate(transactionDate);
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
        assertThat(transfer.getFee()).isEqualTo(0.5D);
        assertThat(transfer.getTransaction()).isEqualTo("Sending");
    }
    
    @Test // convertToEntity(transferDTO)
    public void givenATransferList_whenMapToDTO_thenGetTransferDTOListIsCreated()
            throws Exception {
        // GIVEN
        // WHEN
        List<GetTransferDTO> transfertDTOList = transferMapping.mapTransferListToDTO(transferList);
        // THEN
        assertThat(transfertDTOList.size()).isEqualTo(2);
        assertThat(transfertDTOList.get(0).getAmount()).isEqualTo(100D);
        assertThat(transfertDTOList.get(1).getAmount()).isEqualTo(200D);
        assertThat(transfertDTOList.get(1).getFee()).isEqualTo(1D);
        
    }

}
