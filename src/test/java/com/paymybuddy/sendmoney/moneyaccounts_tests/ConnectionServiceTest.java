/**
 * 
 */
package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.service.ConnectionService;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;

/**
 * @author Thierry SCHREINER
 *
 */
@SpringBootTest("ConnectionServiceImpl.class")
public class ConnectionServiceTest {

    @Autowired
    private ConnectionService connectionService;

    @MockBean
    private PmbAccountRepository pmbAccountRepository;

    @MockBean
    private PmbAccountMapping pmbAccountMapping;

    @MockBean
    private EmailRetrieve emailRetrieve;

    @BeforeEach
    public void setup() {
    }

    @Test // AddConnection method
    public void givenAnEmail_whenAddConnection_thenConnectionIsAdded()
            throws Exception {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        PmbAccount pmbAccountToAdd = new PmbAccount();
        pmbAccountToAdd.setId(1L);
        pmbAccountToAdd.setPmbAccountNumber("PMB0001515");
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccountToAdd);
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        connectionService.addConnection(eMail);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(pmbAccountRepository).save(any(PmbAccount.class));
        verify(pmbAccountMapping).mapPmbAccountToDTO(any(PmbAccount.class));
    }

    @Test // DelConnection method
    public void givenAnEmail_whenDelConnection_thenConnectionIsDeleted()
            throws Exception {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        PmbAccount pmbAccountToAdd = new PmbAccount();
        pmbAccountToAdd.setId(1L);
        pmbAccountToAdd.setPmbAccountNumber("PMB0001515");
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccountToAdd, new PmbAccount());
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        connectionService.delConnection(eMail);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(pmbAccountRepository).save(any(PmbAccount.class));
        verify(pmbAccountMapping).mapPmbAccountToDTO(any(PmbAccount.class));
    }

    @Test // GetConnections method
    public void givenAUser_whenGetConnections_thenReturnsTheListOfConnections()
            throws Exception {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(new PmbAccount());
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        connectionService.getConnections();
        // THEN
        verify(pmbAccountRepository).findByOwnerEmail(anyString());
        verify(pmbAccountMapping).mapPmbAccountToDTO(any(PmbAccount.class));
    }

}
