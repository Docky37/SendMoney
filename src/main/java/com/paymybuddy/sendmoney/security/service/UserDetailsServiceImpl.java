package com.paymybuddy.sendmoney.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * Custom implementation of UserDetailsService interface.
 *
 * @author Thierry SCHREINER
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * UserRepository instance used to deal with database.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Override method that finds and loads details of a user from database when
     * he logs in.
     *
     * @return a Buddy object
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Buddy user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + email);
        } else {
            return user;
        }
    }
}
