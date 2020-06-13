package com.paymybuddy.sendmoney.security.service;

import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.model.UserDTO;

/**
 * This UserService interface defines 2 methods, one to save new users, the
 * second to find a registred user by his email address.
 *
 * @author Thierry Schreiner
 */
public interface UserService {
    /**
     * This method is used to save the userDTO data in DataBase via a Buddy
     * entity.
     *
     * @param user a UserDTO object
     */
    void save(UserDTO user);

    /**
     * This method is used to find the Buddy entity in DataBase with an email
     * field value equal to the given parameter.
     *
     * @param email
     * @return a Buddy object
     */
    Buddy findByEmail(String email);

}