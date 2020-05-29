/**
 * 
 */
package com.paymybuddy.sendmoney.security.service;

import com.paymybuddy.sendmoney.security.model.Buddy;

/**
 * @author Thierry Schreiner
 */
public interface UserService {
    void save(Buddy user);

    Buddy findByUsername(String username);

}
