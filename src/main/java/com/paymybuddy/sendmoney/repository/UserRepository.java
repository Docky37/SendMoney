package com.paymybuddy.sendmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.sendmoney.model.Buddy;

public interface UserRepository extends JpaRepository<Buddy, Long> {
    Buddy findByUsername(String username);
}
