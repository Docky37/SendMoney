package com.paymybuddy.sendmoney.moneyaccounts.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.BankAccountRepository;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * 
 * @author Thierry SCHREINER
 */
@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * @param bankAccDTO
     */
    public void save(@Valid BankAccountDTO bankAccDTO) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(bankAccDTO.getIbanCode());
        bankAccount.setSwift(bankAccDTO.getSwift());

        Buddy buddy = userRepository.findByEmail(bankAccDTO.getEmail());
        bankAccount.setOwner(buddy);

        bankAccountRepository.save(bankAccount);

    }

}
