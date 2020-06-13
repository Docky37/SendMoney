package com.paymybuddy.sendmoney.moneyaccounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.sendmoney.moneyaccounts.model.BankAccount;

/**
 * JPA repository interface use to deal with database bank_account table.
 *
 * @author Thierry SCHREINER
 */
@Repository
public interface BankAccountRepository
        extends JpaRepository<BankAccount, Long> {

}
