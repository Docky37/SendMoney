package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.BankAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.service.BankAccountService;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringBootTest("BankAccountServiceImpl.class")
public class BankAccountServiceTest {

    @Autowired
    private BankAccountService service;

    @MockBean
    private BankAccountRepository bankAccountRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void givenValidData_whenSaveBankAccount_thenCreated()
            throws Exception {
        // GIVEN
        BankAccountDTO bankAccDTO = new BankAccountDTO();
        bankAccDTO.setIbanCode("FR3330002005500000157841Z25");
        bankAccDTO.setSwift("CRLYFRPPXXX");
        bankAccDTO.setEmail("service.test@test.fr");
        given(userRepository.findByEmail(anyString())).willReturn(new Buddy());
        // WHEN
        service.saveBankAccount(bankAccDTO);
        // THEN
        verify(userRepository).findByEmail(anyString());
        verify(bankAccountRepository).save(any(BankAccount.class));
    }

}
