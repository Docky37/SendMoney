package com.paymybuddy.sendmoney.moneyaccounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.sendmoney.moneyaccounts.model.PmbAccount;

/**
 * JPA repository interface use to deal with database pmb_account table.
 *
 * @author Thierry SCHREINER
 */
@Repository
public interface PmbAccountRepository extends JpaRepository<PmbAccount, Long> {

}
