package com.paymybuddy.sendmoney.moneyaccounts_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.sendmoney.moneyaccounts.controller.BankAccountController;
import com.paymybuddy.sendmoney.moneyaccounts.service.BankAccountService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("Validation of account")
    public void accountValidation() {
        assertThat(controller).isNotNull();
    }
    
    @Test // GetMapping(value = "/bank-account")
    public void whenAddBankAccount_thenBankAccountForm()
            throws Exception {
        mvc.perform(get("/bank-account")).andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/jsp/bank-account.jsp"))
                .andExpect(status().isOk());
    }

    @Test // PostMapping(value = "/bank-account")
    public void givenValidData_whenPostBankAccount_thenCreated() throws Exception {
        final MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post("/bank-account")
                .param("ibanCode","FR3330002005500000157841Z25")
                .param("swift","CRLYFRPPXXX")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test // PostMapping(value = "/bank-account")
    public void givenInvalidIban_whenPostBankAccount_thenError() throws Exception {
        final MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post("/bank-account")
                .param("ibanCode","FR3330002005500000157841Z")
                .param("swift","CRLYFRPPXXX")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andReturn();
    }

    @Test // PostMapping(value = "/bank-account")
    public void givenInvalidSwift_whenPostBankAccount_thenError() throws Exception {
        final MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post("/bank-account")
                .param("ibanCode","FR3330002005500000157841Z25")
                .param("swift","CRLYFRPP")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andReturn();
    }

}
