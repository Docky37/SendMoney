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

    /**
     * Find a PmbAccount by the email address of its owner.
     * @param eMail
     * @return a PmbAccount instance
     */
    PmbAccount findByOwnerEmail(String eMail);

}
