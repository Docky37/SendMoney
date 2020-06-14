package com.paymybuddy.sendmoney.security_tests;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.model.Role;
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.repository.RoleRepository;
import com.paymybuddy.sendmoney.security.repository.UserRepository;
import com.paymybuddy.sendmoney.security.service.UserService;

@SpringBootTest("UserServiceImpl.class")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;
    
    @BeforeEach
    public void setup() {
    }

    @Test
    public void givenAEmail_whenFindByEmail_thenCallRepositoryFindMethod()
            throws Exception {
        // GIVEN
        
        // WHEN
        userService.findByEmail("User.Test@mail.fr");
        // THEN
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    public void givenAUserDTO_whenSave_thenCallRepositorySaveMethod()
            throws Exception {
        // GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("User");
        userDTO.setLastName("TEST");
        userDTO.setEmail("User.Test@mail.fr");
        userDTO.setPassword("3737373");
        userDTO.setConfirmPassword("3737373");
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role());
        roles.get(0).setId(1L);
        roles.get(0).setName("USER");
        given(roleRepository.findAll()).willReturn(roles);
        // WHEN
        userService.save(userDTO);
        // THEN
        verify(userRepository).save(any(Buddy.class));
        verify(roleRepository).findAll();
    }
}
