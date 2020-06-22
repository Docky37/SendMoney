/**
 * 
 */
package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.moneyaccounts.service.AddConnectionService;
import com.paymybuddy.sendmoney.moneyaccounts.service.PmbAccountService;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * @author Thierry SCHREINER
 *
 */
@SpringBootTest("AddConnectionServiceImpl.class")
public class AddConnectionServiceTest {

    @Autowired
    private AddConnectionService addConnectionService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void givenAnEmail_whenAddConnection_thenAdded()
            throws Exception {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        Buddy buddyToAdd = new Buddy();
        given(userRepository.findByEmail(anyString())).willReturn(buddyToAdd);
        
        // WHEN
        addConnectionService.addConnection(eMail);
        // THEN
        verify(userRepository).findByEmail(anyString());
        verify(userRepository).save(any(Buddy.class));
    }

}
