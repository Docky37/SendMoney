package com.paymybuddy.sendmoney.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * JPA repository interface use to deal with database Buddy table.
 *
 * @author Thierry SCHREINER
 */
public interface UserRepository extends JpaRepository<Buddy, Long> {

    /**
     * This method is used to find a Buddy by is email address.
     *
     * @param username
     * @return a Buddy object
     */
    Buddy findByEmail(String username);

}
