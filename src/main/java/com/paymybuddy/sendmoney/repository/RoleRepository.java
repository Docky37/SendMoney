package com.paymybuddy.sendmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.sendmoney.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
