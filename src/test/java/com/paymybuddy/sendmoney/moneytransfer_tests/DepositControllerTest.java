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
import com.paymybuddy.sendmoney.money_transfer.model.Transfer;
import com.paymybuddy.sendmoney.money_transfer.service.DepositService;

/**
 * @author Thierry SCHREINER
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DepositControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepositService depositService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test // PostMapping("/pmb-adm/deposit")
    public void givenAValidOrderDTO_whenSend_thenDepositIsCreated()
            throws Exception {
        given(depositService.send(any(OrderDTO.class)))
                .willReturn(new Transfer());
        given(depositService.saveTransaction(any(Transfer.class)))
                .willReturn("201 Created");
        mvc.perform(MockMvcRequestBuilders.post("/pmb-adm/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"my.beneficiary@pmb.fr\","
                        + "\"amount\":100.00}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

         verify(depositService).send(any(OrderDTO.class));

    }

    @Test // PostMapping("/pmb-adm/deposit")
    public void givenAnInvalidOrderDTO_whenSend_thenBadRequest()
            throws Exception {
        given(depositService.send(any(OrderDTO.class)))
                .willReturn(null);
        given(depositService.getResponse()).willReturn("400 Bad Request");
        mvc.perform(MockMvcRequestBuilders.post("/pmb-adm/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"my.beneficiary@pmb.fr\","
                        + "\"amount\":100.00}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

         verify(depositService).send(any(OrderDTO.class));

    }

}
