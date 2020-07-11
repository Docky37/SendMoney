package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
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

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.service.PmbAccountService;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.util.EmailRetrieve;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PmbAccountControllerTest {

    Buddy buddy = new Buddy();

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PmbAccountService pmbAccountService;

    @MockBean
    private EmailRetrieve emailRetrieve;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test // PostMapping("/pmb-account")
    public void givenUserEmail_whenNewPmbAccount_thenCreated()
            throws Exception {
        buddy.setEmail("User.Test@pmb.com");
        given(emailRetrieve.getBuddy()).willReturn(buddy);
        given(pmbAccountService.savePmbAccount(any(Buddy.class)))
                .willReturn(new PmbAccountDTO());
        mvc.perform(MockMvcRequestBuilders.post("/pmb-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"User.Test@pmb.com\"}")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        verify(pmbAccountService).savePmbAccount(any(Buddy.class));
    }

    @Test // PostMapping("/pmb-account")
    public void givenDifferentEmail_whenNewPmbAccount_thenBadRequest()
            throws Exception {
        buddy.setEmail("Other.User@pmb.com");
        given(emailRetrieve.getBuddy()).willReturn(buddy);
        given(pmbAccountService.savePmbAccount(any(Buddy.class)))
                .willReturn(new PmbAccountDTO());
        mvc.perform(MockMvcRequestBuilders.post("/pmb-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"User.Test@pmb.com\"}")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        verify(pmbAccountService, never()).savePmbAccount(any(Buddy.class));
    }

    @Test // PostMapping("/pmb-account")
    public void givenPmbAccountAlreadyExists_whenNewPmbAccount_thenBadRequest()
            throws Exception {
        buddy.setEmail("User.Test@pmb.com");
        given(emailRetrieve.getBuddy()).willReturn(buddy);
        given(pmbAccountService.savePmbAccount(any(Buddy.class)))
                .willReturn(null);
        mvc.perform(MockMvcRequestBuilders.post("/pmb-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"User.Test@pmb.com\"}")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        verify(pmbAccountService).savePmbAccount(any(Buddy.class));
    }

}
