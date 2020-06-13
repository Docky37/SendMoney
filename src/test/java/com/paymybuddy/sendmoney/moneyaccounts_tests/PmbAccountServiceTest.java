package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.service.PmbAccountService;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PmbAccountServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PmbAccountService pmbAccountService;

    @MockBean
    private PmbAccountRepository pmbAccountRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void givenNewBuddy_whenSavePmbAccount_thenSaved()
            throws Exception {
        // GIVEN
        Buddy buddy = new Buddy();
        buddy.setId(1515L);
        buddy.setFirstName("First");
        buddy.setLastName("LAST");
        buddy.setEmail("service.test@test.fr");
        buddy.setPassword(
                "$2a$10$B6JrWsAYc42KrZ2C/5kngOojpnqz15ATrwa90UA2eOmfBxRFCCsYS");
        // WHEN
        PmbAccount pmbaccount = pmbAccountService.savePmbAccount(buddy);
        // THEN
        assertThat(pmbaccount.getPmbAccountNumber()).isEqualTo("PMB0001515");
        verify(pmbAccountRepository).save(any(PmbAccount.class));
    }
}
