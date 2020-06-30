package com.paymybuddy.sendmoney.moneyaccounts_tests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
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
import com.paymybuddy.sendmoney.moneyaccounts.service.ConnectionService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 
 * @author Thierry SCHREINER
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ConnectionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConnectionService connectionService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test // PostMapping("/connection")
    public void givenRegistredEmail_whenAddConnection_thenCreated()
            throws Throwable {
        PmbAccountDTO pmbAccount = new PmbAccountDTO();
        given(connectionService.addConnection(anyString()))
                .willReturn(pmbAccount);
        mvc.perform(MockMvcRequestBuilders.post("/connection")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n" + "\"email\":\"test.mail@mel.fr\"\r\n" + "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        verify(connectionService).addConnection(anyString());
    }

    @Test // PostMapping("/connection")
    public void givenUnknownEmail_whenAddConnection_thenNotFound()
            throws Throwable {
        given(connectionService.addConnection(anyString())).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.post("/connection")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n" + "\"email\":\"test.mail@mel.fr\"\r\n" + "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        verify(connectionService).addConnection(anyString());
    }

    @Test // DeleteMapping("/connection")
    public void givenRegistredEmail_whenDelConnection_thenOk()
            throws Throwable {
        PmbAccountDTO pmbAccount = new PmbAccountDTO();
        given(connectionService.delConnection(anyString()))
                .willReturn(pmbAccount);
        mvc.perform(
                MockMvcRequestBuilders.delete("/connection/test.mail@mel.fr"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(connectionService).delConnection(anyString());
    }

    @Test // DeleteMapping("/connection/{email}")
    public void givenUnknownEmail_whenDelConnection_thenNotFound()
            throws Throwable {
        given(connectionService.delConnection(anyString())).willReturn(null);
        mvc.perform(
                MockMvcRequestBuilders.delete("/connection/test.mail@mel.fr"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(connectionService).delConnection(anyString());
    }

    @Test // GetMapping("/connection")
    public void whenGetConnections_thenOk() throws Exception {
        given(connectionService.getConnections())
                .willReturn(new PmbAccountDTO());
        mvc.perform(MockMvcRequestBuilders.get("/connection"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(connectionService).getConnections();
    }

    @Test // GetMapping("/connection")
    public void whenGetConnections_thenNotFound() throws Exception {
        given(connectionService.getConnections()).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/connection"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
