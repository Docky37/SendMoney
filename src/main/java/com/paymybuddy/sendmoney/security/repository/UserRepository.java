package com.paymybuddy.sendmoney.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.sendmoney.security.model.Buddy;

public interface UserRepository extends JpaRepository<Buddy, Long> {

    Buddy findByEmail(String username);
    
}
