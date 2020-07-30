package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.paymybuddy.sendmoney.moneyaccounts.model.AccountBalanceDTO;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringJUnitConfig(value = PmbAccountMapping.class)
public class PmbAccountMappingTest {

    @Autowired
    private PmbAccountMapping pmbAccountMapping;

    @MockBean
    private PmbAccountRepository pmbAccountRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
    }

    // Test Data ************************************************
    static Buddy sender = new Buddy();
    static {
        sender.setEmail("sender@pmb.com");
    }
    static Buddy beneficiary = new Buddy();
    static {
        beneficiary.setEmail("beneficiary@pmb.com");
        beneficiary.setFirstName("FirstName");
        beneficiary.setLastName("LastName");
    }
    static PmbAccount pmbAccount = new PmbAccount();
    static PmbAccount pmbAccount2 = new PmbAccount();
    static {
        pmbAccount.setPmbAccountNumber("PMB0000015");
        pmbAccount.setOwner(sender);
        pmbAccount.setAccountBalance(new BigDecimal("577.00"));

        pmbAccount2.setPmbAccountNumber("PMB0000018");
        pmbAccount2.setOwner(beneficiary);

        pmbAccount.addConnection(pmbAccount2);

    }

    @Test
    public void givenNewBuddy_whenMapPmbAccountToDTO_thenReturnsPmbAccountDTO()
            throws Exception {
        // GIVEN
        String beneficiaryFullName = "FirstName LastName";
        // WHEN
        PmbAccountDTO pmbAccountDTO = pmbAccountMapping
                .mapPmbAccountToDTO(pmbAccount);
        // THEN
        assertThat(pmbAccountDTO.getOwnerMail()).isEqualTo("sender@pmb.com");
        assertThat(pmbAccountDTO.getPmbAccountNumber()).isEqualTo("PMB0000015");
        assertThat(pmbAccountDTO.getConnections())
                .contains(beneficiaryFullName);
    }

    @Test
    public void givenABuddy_whenMapAccountBalanceDTO_thenReturnsAccountBalanceDTO()
            throws Exception {
        // GIVEN
        // WHEN
        AccountBalanceDTO accountBalanceDTO = pmbAccountMapping
                .mapAccountBalanceDTO(pmbAccount);
        // THEN
        assertThat(accountBalanceDTO.getOwnerMail())
                .isEqualTo("sender@pmb.com");
        assertThat(accountBalanceDTO.getPmbAccountNumber())
                .isEqualTo("PMB0000015");
        assertThat(accountBalanceDTO.getAccountBalance())
                .isEqualTo(new BigDecimal("577.00"));
    }
}
