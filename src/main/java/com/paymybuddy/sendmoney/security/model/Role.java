package com.paymybuddy.sendmoney.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Role class is the role entity of Pay My Buddy.
 *
 * @author Thierry SCHREINER
 */
@Entity
@Table(name = "role")
public class Role {

    /**
     * The role id field and primary key.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The role name.
     */
    @Getter
    @Setter
    private String name;

    /**
     * The join table used to map buddy and its roles.
     
    @Getter
    @Setter
    @ManyToMany(mappedBy = "roles")
    private Set<Buddy> users;*/
}
