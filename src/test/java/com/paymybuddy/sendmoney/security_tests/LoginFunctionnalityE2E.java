package com.paymybuddy.sendmoney.security_tests;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class LoginFunctionnalityE2E {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @Test
    public void givenValidCredentials_whenLogin_thenAuthenticated()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n"
                        + "    \"username\":\"Angelina.Jolie@Ocean11.cine\",\r\n"
                        + "    \"password\":\"1231231\"\r\n" + "}"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void givenInvalidCredentials_whenLogin_thenFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n"
                        + "    \"username\":\"Angelina.Jolie@Ocean11.cine\",\r\n"
                        + "    \"password\":\"7894561\"\r\n" + "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

}
