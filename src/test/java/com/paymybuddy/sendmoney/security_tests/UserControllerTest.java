package com.paymybuddy.sendmoney.security_tests;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.http.MediaType;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;


    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    public static UserDTO validUser = new UserDTO("User", "TEST",
            "springuser.123@oc.com", "spring123", "spring123");
    public static UserDTO invalidUser = new UserDTO("User", "TEST",
            "springuser.123@oc.com", "spring123", "spring124");

// TEST OF REGISTRATION FUNCTIONALITY
    @Test // PostMapping(value = "/registration")-
          // No error -> Status OK
    public void givenNoError_whenPostRegistration_thenStatusOk()
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validUser)))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(result);
    }

    @Test // POST(value = "/registration")
          // With error -> Status 400
    public void givenErrors_whenPostRegistration_thenStatus400()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidUser)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
        System.out.println(result);
    }

}
