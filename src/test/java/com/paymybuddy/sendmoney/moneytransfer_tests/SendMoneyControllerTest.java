package com.paymybuddy.sendmoney.moneytransfer_tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
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

import com.paymybuddy.sendmoney.money_transfer.model.OrderDTO;
import com.paymybuddy.sendmoney.money_transfer.service.SendMoneyService;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;

/**
 * @author Thierry SCHREINER
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SendMoneyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SendMoneyService sendMoneyService;

    @MockBean
    private EmailRetrieve emailRetrieve;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test // PostMapping("/send-money")
    public void givenAnOrderDTO_whenSend_thenCreated()
            throws Exception {
        given(emailRetrieve.getEmail()).willReturn("logged.user@pmb.com");
        mvc.perform(MockMvcRequestBuilders.post("/send-money")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"my.beneficiary@pmb.fr\","
                        + "\"amount\":100.00}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        
        verify(emailRetrieve).getEmail();
        verify(sendMoneyService).send(any(OrderDTO.class));
    }
    
}
