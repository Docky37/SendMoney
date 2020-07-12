package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.BDDMockito.given;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.service.PmbAccountService;
import com.paymybuddy.sendmoney.moneyaccounts.service.PmbAccountServiceImpl;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringJUnitConfig(value = PmbAccountServiceImpl.class)
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

    @Test
    public void givenBuddyAlreadyHadPmbAccount_whenSavePmbAccount_thenReturnsNull()
            throws Exception {
        // GIVEN
        Buddy buddy = new Buddy();
        buddy.setId(1515L);
        buddy.setFirstName("First");
        buddy.setLastName("LAST");
        buddy.setEmail("service.test@test.fr");
        buddy.setPassword(
                "$2a$10$B6JrWsAYc42KrZ2C/5kngOojpnqz15ATrwa90UA2eOmfBxRFCCsYS");
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(new PmbAccount());
        // WHEN
        PmbAccountDTO pmbAccountDTO = pmbAccountService.savePmbAccount(buddy);
        // THEN
        verify(pmbAccountRepository).findByOwnerEmail(buddy.getEmail());
        assertThat(pmbAccountDTO).isNull();
        verify(pmbAccountRepository, never()).save(any(PmbAccount.class));
    }

}
