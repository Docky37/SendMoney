package com.paymybuddy.sendmoney.security_tests;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.service.UserService;
import com.paymybuddy.sendmoney.security.validator.UserValidator;

@SpringBootTest("UserValidator.class")
public class UserValidatorTest {

    @Autowired
    private UserValidator userValidator;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
    
    }

    @Test // Email already registered in DB
    public void givenRegisteredEmail_whenValidate_thenRejectDuplicateMail()
            throws Exception {
        // GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("User");
        userDTO.setLastName("TEST");
        userDTO.setEmail("User.Test@mail.fr");
        userDTO.setPassword("3737373");
        userDTO.setConfirmPassword("3737373");
        Errors errors = new BeanPropertyBindingResult(userDTO,"Invalid Email");
        Buddy buddy = new Buddy();
        buddy.setFirstName("User");
        buddy.setLastName("TEST");
        buddy.setEmail("User.Test@mail.fr");
        buddy.setPassword("3737373");
        given(userService.findByEmail(anyString())).willReturn(buddy);
        // WHEN
        userValidator.validate((Object) userDTO, errors);
        
        // THEN
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("email"));
    }
    
    @Test // Unknown Email in DB
    public void givenUnknownEmail_whenValidate_thenNoEmailError()
            throws Exception {
        // GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("User");
        userDTO.setLastName("TEST");
        userDTO.setEmail("User.Test@mail.fr");
        userDTO.setPassword("3737373");
        userDTO.setConfirmPassword("3737373");
        Errors errors = new BeanPropertyBindingResult(userDTO,"Invalid Email");
        given(userService.findByEmail(anyString())).willReturn(null);
        // WHEN
        userValidator.validate((Object) userDTO, errors);
        
        // THEN
        assertFalse(errors.hasErrors());
        assertNull(errors.getFieldError("email"));
    }
    
    @Test // ConfirmPassword and Password are different
    public void givenDifferentConfirmPassword_whenValidate_thenError()
            throws Exception {
        // GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("User");
        userDTO.setLastName("TEST");
        userDTO.setEmail("User.Test@mail.fr");
        userDTO.setPassword("3737373");
        userDTO.setConfirmPassword("7373737");
        Errors errors = new BeanPropertyBindingResult(userDTO,"Passwords are different");
        given(userService.findByEmail(anyString())).willReturn(null);
        // WHEN
        userValidator.validate((Object) userDTO, errors);
        
        // THEN
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("confirmPassword"));
    }

}
