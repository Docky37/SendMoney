package com.paymybuddy.sendmoney.security.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.sendmoney.security.model.Role;

/**
 * JPA repository interface use to deal with database role table.
 *
 * @author Thierry SCHREINER
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * This methode is used to find all roles of a Buddy.
     *
     * @param role
     * @return a set<Role>
     */
    Set<Role> findByName(String role);
}
