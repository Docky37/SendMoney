package com.paymybuddy.sendmoney.money_transfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.sendmoney.money_transfer.model.Transfer;

/**
 * @author Thierry SCHREINER
 *
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
