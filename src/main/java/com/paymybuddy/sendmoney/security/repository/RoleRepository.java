package com.paymybuddy.sendmoney.security.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.sendmoney.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
    Set<Role> findByName(String role);
}
