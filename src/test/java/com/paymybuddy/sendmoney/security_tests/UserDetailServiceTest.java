package com.paymybuddy.sendmoney.security_tests;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.RoleRepository;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

@SpringBootTest("userDetailsServiceImpl.class")
public class UserDetailServiceTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void givenAEmail_whenloadUserByUsername_thenCallRepositoryFindMethod()
            throws Exception {
        // GIVEN
        Buddy buddy = new Buddy();
        buddy.setFirstName("User");
        buddy.setLastName("TEST");
        buddy.setEmail("User.Test@mail.fr");
        buddy.setPassword("3737373");
        given(userRepository.findByEmail(anyString())).willReturn(buddy);
        // WHEN
        UserDetails user = userDetailsService
                .loadUserByUsername("User.Test@mail.fr");
        // THEN
        verify(userRepository).findByEmail(anyString());
        assertThat(user.getUsername()).isEqualTo(buddy.getUsername());
    }

    @Test
    public void givenAnUnknownEmail_whenloadUserByUsername_thenUsernameNotFoundException() {
        // GIVEN
        given(userRepository.findByEmail(anyString())).willReturn(null);
        // WHEN
        // THEN
        assertThatThrownBy(() -> userDetailsService
                .loadUserByUsername("User.Test@mail.fr"))
                        .isInstanceOf(UsernameNotFoundException.class);
    }

}
