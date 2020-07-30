package com.paymybuddy.sendmoney.security.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.repository.RoleRepository;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * Implementation class of the Interface UserService that contains the method
 * save and the method findByEmail.
 *
 * @author Thierry Schreiner
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * A UserRepository object used to deal with the DataBase buddy table.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * A RoleRepository object used to deal with the DataBase role table.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * A PasswordEncoder implementation using BCrypt to protect the password.
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The save method create a new Buddy entity object, fills all its fields
     * with the userDTO data before it calls the userRepository to persist.
     */
    @Override
    public void save(final UserDTO userDTO) {
        Buddy buddy = new Buddy();
        buddy.setFirstName(userDTO.getFirstName());
        buddy.setLastName(userDTO.getLastName());
        buddy.setEmail(userDTO.getEmail());
        buddy.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        buddy.setRoles(new HashSet<>(roleRepository.findByName("USER")));
        userRepository.save(buddy);
    }

    /**
     * The findByEmail method call the userRepository to find a user by his
     * email address.
     */
    @Override
    public Buddy findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

}
