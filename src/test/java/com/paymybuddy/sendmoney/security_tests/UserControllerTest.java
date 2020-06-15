package com.paymybuddy.sendmoney.security_tests;

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

// TEST OF LOGIN FUNCTIONALITY
    @Test // GetMapping(value = "/login")
    public void whenGetLogin_thenViewLoginForm() throws Exception {
        mvc.perform(get("/login")).andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"))
                .andExpect(status().isOk());
    }

// TEST OF REGISTRATION FUNCTIONALITY
    @Test // GetMapping(value = "/registration")
    public void whenGetRegistration_thenViewRegistrationForm()
            throws Exception {
        mvc.perform(get("/registration")).andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/jsp/registration.jsp"))
                .andExpect(status().isOk());

    }

    @Test // PostMapping(value = "/registration")
    public void givenNoError_whenPostRegistration_thenCreated() throws Exception {
        final MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post("/registration")
                .param("firstName", "User")
                .param("lastName", "TEST")
                .param("email","springuser.123@oc.com")
                .param("password","spring123")
                .param("confirmPassword","spring123")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(result);
    }

    @Test // PostMapping(value = "/registration")
    public void givenErrors_whenPostRegistration_then302() throws Exception {
        final MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post("/registration")
                .param("firstName", "User")
                .param("lastName", "TEST")
                .param("email","springuser.123")
                .param("password","spring123")
                .param("confirmPassword","spring123")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andReturn();
        System.out.println(result);
    }

 // TEST OF USER WELCOME PAGE FUNCTIONALITY
    @Test // GetMapping(value = "/welcome")
    public void whenGetWelcome_thenViewWelcome() throws Exception {
        mvc.perform(get("/welcome")).andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/jsp/welcome.jsp"))
                .andExpect(status().isOk());
    }

    // TEST OF USER WELCOME PAGE FUNCTIONALITY
    @Test // GetMapping(value = "/admin")
    public void whenGetAdmin_thenViewAdmin() throws Exception {
        mvc.perform(get("/admin")).andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin.jsp"))
                .andExpect(status().isOk());
    }

}
