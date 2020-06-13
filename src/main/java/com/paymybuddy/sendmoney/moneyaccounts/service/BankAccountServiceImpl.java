package com.paymybuddy.sendmoney.moneyaccounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccount;
import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccountDTO;
import com.paymybuddy.sendmoney.moneyaccounts.repository.BankAccountRepository;
import com.paymybuddy.sendmoney.security.model.Buddy;
import com.paymybuddy.sendmoney.security.repository.UserRepository;

/**
 * {@inheritDoc}.
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    /**
     * A BankAccountRepository object used to deal with the DataBase
     * bank_account table.
     */
    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * A UserRepository object used to deal with the DataBase buddy table.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveBankAccount(final BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(bankAccountDTO.getIbanCode());
        bankAccount.setSwift(bankAccountDTO.getSwift());

        Buddy buddy = userRepository.findByEmail(bankAccountDTO.getEmail());
        bankAccount.setOwner(buddy);

        bankAccountRepository.save(bankAccount);

    }

}
