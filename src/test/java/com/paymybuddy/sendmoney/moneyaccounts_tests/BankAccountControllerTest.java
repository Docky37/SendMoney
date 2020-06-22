package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.sendmoney.moneyaccounts.controller.BankAccountController;
import com.paymybuddy.sendmoney.moneyaccounts.service.BankAccountService;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BankAccountControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BankAccountController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BankAccountService bankAccountService;

    @MockBean
    private EmailRetrieve emailRetrieve;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        given(emailRetrieve.getEmail()).willReturn("my.mail@mel.fr");
    }

    @Test
    @DisplayName("Validation of account")
    public void accountValidation() {
        assertThat(controller).isNotNull();
    }

    @Test // PostMapping("/bank-account")
    public void givenValidData_whenPostBankAccount_thenCreated()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/bank-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n"
                        + "\"ibanCode\":\"FR3330002005ETYOYO000000Z25\",\r\n"
                        + "\"swift\":\"CRLYFRPPXXX\"}"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(emailRetrieve).getEmail();
    }

    @Test // PostMapping(value = "/bank-account")
    public void givenInvalidIban_whenPostBankAccount_thenError()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/bank-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n"
                        + "\"ibanCode\":\"FR3330002005ETYOYO000000Z\",\r\n"
                        + "\"swift\":\"CRLYFRPPXXX\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
        verify(emailRetrieve, never()).getEmail();
    }

    @Test // PostMapping(value = "/bank-account")
    public void givenInvalidSwift_whenPostBankAccount_thenError()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/bank-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n"
                        + "\"ibanCode\":\"FR3330002005ETYOYO000000Z25\",\r\n"
                        + "\"swift\":\"CRLYFRPP\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
        verify(emailRetrieve, never()).getEmail();
    }

}
