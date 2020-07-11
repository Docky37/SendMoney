package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.service.PmbAccountService;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringBootTest("PmbAccountServiceImpl.class")
public class PmbAccountServiceTest {

    @Autowired
    private PmbAccountService pmbAccountService;

    @MockBean
    private PmbAccountRepository pmbAccountRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PmbAccountMapping pmbAccountMapping;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void givenNewBuddy_whenSavePmbAccount_thenSaved() throws Exception {
        // GIVEN
        Buddy buddy = new Buddy();
        buddy.setId(1515L);
        buddy.setFirstName("First");
        buddy.setLastName("LAST");
        buddy.setEmail("service.test@test.fr");
        buddy.setPassword(
                "$2a$10$B6JrWsAYc42KrZ2C/5kngOojpnqz15ATrwa90UA2eOmfBxRFCCsYS");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        pmbAccountService.savePmbAccount(buddy);
        // THEN
        verify(pmbAccountRepository).findByOwnerEmail(buddy.getEmail());
        ArgumentCaptor<PmbAccount> argument = ArgumentCaptor
                .forClass(PmbAccount.class);
        verify(pmbAccountRepository).save(argument.capture());
        assertEquals("PMB0001515", argument.getValue().getPmbAccountNumber());

    }
}
