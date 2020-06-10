/**
 * 
 */
package com.paymybuddy.sendmoney.moneyaccounts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.paymybuddy.sendmoney.security.model.Buddy;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thierry SCHREINER
 *
 */
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Getter
    @Setter
    private String iban;
    
    @Getter
    @Setter
    private String swift;
    
    @Getter
    @Setter
    @OneToOne(targetEntity = Buddy.class)
    @JoinColumn(name = "owner")
    private Buddy owner;
    
    
}
