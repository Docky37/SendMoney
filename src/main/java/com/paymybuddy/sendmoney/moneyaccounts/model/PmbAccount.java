package com.paymybuddy.sendmoney.moneyaccounts.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.paymybuddy.sendmoney.security.model.Buddy;

import lombok.Getter;
import lombok.Setter;

/**
 * The PmbAccount entity is used to persist Pay My Buddy account in order to
 * allow internal money transfers between buddies.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "pmb_account")
public class PmbAccount {

    /**
     * The id and primary key of pmb_account table.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The Pay My Buddy account identification Number, a 10 characters String
     * that begins by "PMB" and contains the buddy id.
     */
    @Getter
    @Setter
    private String pmbAccountNumber;

    /**
     * The credit balance of the Pay My Buddy account (cannot be negative).
     */
    @Getter
    @Setter
    private Double accountBalance;

    /**
     * The buddy who is the owner of this bank account.
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = Buddy.class)
    @JoinColumn(name = "owner")
    private Buddy owner;

    /**
     * The join table used to make connections.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "connection",
        joinColumns = @JoinColumn(name = "me_id"),
        inverseJoinColumns = @JoinColumn(name = "beneficiary_id"))
    @Getter
    @Setter
    private Set<PmbAccount> connections = new TreeSet<PmbAccount>();

    public void addConnection(PmbAccount pmbAccount) {
        this.connections.add(pmbAccount);
    }  

}
