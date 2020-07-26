package com.paymybuddy.sendmoney.money_transfer.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * This repository interface is use to deal with the transfer table of the
 * database.
 *
 * @author Thierry SCHREINER
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    /**
     * This method find all transfers where the buddy, owner of the given email,
     * is sender or beneficiary, using a @Query.
     *
     * @param email
     * @return a List<Transfer> entity object
     */
    @Query("SELECT t FROM Transfer t"
            + " RIGHT JOIN t.pmbAccountSender p INNER JOIN p.owner b"
            + " RIGHT JOIN t.pmbAccountBeneficiary p2 INNER JOIN p2.owner b2"
            + " WHERE b.email = :email OR b2.email = :email"
            + " ORDER BY t.valueDate DESC")
    List<Transfer> findByEmail(String email);

    /**
     * This method, only for user with administrator role, find all transfers of
     * a given day, using a @Query.
     *
     * @param submitDate
     * @return a List<Transfer> entity objet
     */
    @Query("SELECT t FROM Transfer t WHERE Date(t.valueDate) = :submitDate")
    List<Transfer> findByValueDate(Date submitDate);

}
