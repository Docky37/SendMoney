package com.paymybuddy.sendmoney.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.sendmoney.security.service.UserService;
import com.paymybuddy.sendmoney.security.validator.UserValidator;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserValidator userValidator;

    @MockBean
    private UserService userService;
    
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test // GetMapping(value = "/login")
    public void whenGetLogin_thenViewLoginForm() throws Exception {
        mvc.perform(get("/login")).andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"))
                .andExpect(status().isOk());
    }

    @Test // GetMapping(value = "/registration")
    public void whenGetRegistration_thenViewRegistrationForm()
            throws Exception {
        mvc.perform(get("/registration")).andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/jsp/registration.jsp"))
                .andExpect(status().isOk());

    }

    @Test // PostMapping(value = "/registration")
    public void whenPostRegistration_thenCreated() throws Exception {
        final MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post("/registration")
                .param("email","springuser.123@oc.com")
                .param("password","spring123")
                .param("confirmPassword","spring123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(result);
    }
}
