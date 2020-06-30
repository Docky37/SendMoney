package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.moneyaccounts.repository.PmbAccountRepository;
import com.paymybuddy.sendmoney.moneyaccounts.service.ConnectionService;
import com.paymybuddy.sendmoney.moneyaccounts.util.PmbAccountMapping;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;
import com.paymybuddy.sendmoney.moneyaccounts.exception.UserWithoutPmbAccountException;
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
    private PmbAccount pmbAccount;

    @MockBean
    private EmailRetrieve emailRetrieve;

    @BeforeEach
    public void setup() {
    }

    @Test // AddConnection method
    public void givenAnEmail_whenAddConnection_thenConnectionIsAdded()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        Buddy owner = new Buddy();
        owner.setId(1L);
        owner.setEmail(eMail);
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccount);
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        connectionService.addConnection(eMail);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(emailRetrieve).getEmail();
        verify(pmbAccountRepository).save(any(PmbAccount.class));
        verify(pmbAccountMapping).mapPmbAccountToDTO(any(PmbAccount.class));
        verify(pmbAccount).addConnection(any(PmbAccount.class));
    }

    @Test // AddConnection method
    public void givenNonRegistredEmail_whenAddConnection_thenConnectionIsAdded()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(null);
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        PmbAccountDTO pmbAccountDTO = connectionService.addConnection(eMail);

        // THEN
        verify(pmbAccountRepository).findByOwnerEmail(anyString());
        verify(pmbAccountRepository, never()).save(any(PmbAccount.class));
        assertThat(pmbAccountDTO).isEqualTo(null);
    }

    @Test // AddConnection method
    public void givenNoPmbAccount_whenAddConnection_thenThrowsException()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccount, (PmbAccount) null);
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        // WHEN
        // THEN
        assertThrows(UserWithoutPmbAccountException.class,
                () -> connectionService.addConnection(eMail));
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
    }

    @Test // DelConnection method
    public void givenAnEmail_whenDelConnection_thenConnectionIsDeleted()
            throws Throwable {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        pmbAccount.setId(1L);
        pmbAccount.setPmbAccountNumber("PMB0001515");
        pmbAccount.setConnections(new TreeSet<PmbAccount>());
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccount);
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        connectionService.delConnection(eMail);
        // THEN
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
        verify(emailRetrieve).getEmail();
        verify(pmbAccountRepository).save(any(PmbAccount.class));
        verify(pmbAccountMapping).mapPmbAccountToDTO(any(PmbAccount.class));
        verify(pmbAccount).getConnections();
    }

    @Test // DelConnection method
    public void givenNonRegistredEmail_whenDelConnection_thenConnectionIsDeleted()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(null);
        // WHEN
        PmbAccountDTO pmbAccountDTO = connectionService.delConnection(eMail);
        // THEN
        verify(pmbAccountRepository).findByOwnerEmail(anyString());
        verify(pmbAccountRepository, never()).save(any(PmbAccount.class));
        assertThat(pmbAccountDTO).isEqualTo(null);
    }

    @Test // DelConnection method
    public void givenNoPmbAccount_whenDelConnection_thenThrowsException()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        String eMail = "Yo.Yo@yoyo.fr";
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(pmbAccount, (PmbAccount) null);
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        // WHEN
        // THEN
        assertThrows(UserWithoutPmbAccountException.class,
                () -> connectionService.delConnection(eMail));
        verify(pmbAccountRepository, times(2)).findByOwnerEmail(anyString());
    }

    @Test // GetConnections method
    public void givenAUser_whenGetConnections_thenReturnsHisPmbAccountDTO()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(new PmbAccount());
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        connectionService.getConnections();
        // THEN
        verify(emailRetrieve).getEmail();
        verify(pmbAccountRepository).findByOwnerEmail(anyString());
        verify(pmbAccountMapping).mapPmbAccountToDTO(any(PmbAccount.class));
    }

    @Test // GetConnections method
    public void givenAUserWithoutPmbAccount_whenGetConnections_thenReturnsNull()
            throws Exception, UserWithoutPmbAccountException {
        // GIVEN
        given(pmbAccountRepository.findByOwnerEmail(anyString()))
                .willReturn(null);
        given(emailRetrieve.getEmail()).willReturn("My.Mail@pmb.fr");
        given(pmbAccountMapping.mapPmbAccountToDTO(any(PmbAccount.class)))
                .willReturn(new PmbAccountDTO());
        // WHEN
        connectionService.getConnections();
        // THEN
        verify(pmbAccountRepository).findByOwnerEmail(anyString());
        verify(emailRetrieve).getEmail();
        verify(pmbAccountMapping, never())
                .mapPmbAccountToDTO(any(PmbAccount.class));
    }

}
