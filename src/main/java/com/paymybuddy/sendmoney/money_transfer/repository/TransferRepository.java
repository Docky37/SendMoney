package com.paymybuddy.sendmoney.money_transfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * @author Thierry SCHREINER
 *
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    /**
     * @param email
     * @return a List<Transfer> object
     */
    @Query("SELECT t FROM Transfer t"
            + " RIGHT JOIN t.pmbAccountSender p"
            + " INNER JOIN p.owner b"
            + " WHERE b.email = :email"
            + " ORDER BY t.valueDate DESC")
    List<Transfer> findByEmail(String email);

}
