package com.paymybuddy.sendmoney.security.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.model.UserDTO;
import com.paymybuddy.sendmoney.security.repository.RoleRepository;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    

    @Override
    public void save(UserDTO userDTO) {
        Buddy buddy = new Buddy();
        buddy.setFirstName(userDTO.getFirstName());
        buddy.setLastName(userDTO.getLastName());
        buddy.setEmail(userDTO.getEmail());
        buddy.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        buddy.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(buddy);
    }

    @Override
    public Buddy findByUsername(String userName) {
        return userRepository.findByEmail(userName);
    }

}
