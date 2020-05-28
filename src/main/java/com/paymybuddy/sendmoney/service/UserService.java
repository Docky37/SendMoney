/**
 * 
 */
package com.paymybuddy.sendmoney.service;

import com.paymybuddy.sendmoney.model.Buddy;

/**
 * @author Thierry Schreiner
 */
public interface UserService {
    void save(Buddy user);

    Buddy findByUsername(String username);

}
