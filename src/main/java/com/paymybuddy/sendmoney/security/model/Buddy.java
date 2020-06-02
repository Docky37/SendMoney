package com.paymybuddy.sendmoney.security.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.paymybuddy.sendmoney.security.model.Role;

import lombok.Getter;
import lombok.Setter;

/**
 * The Buddy class is the user entity of Pay My Buddy.
 *
 * @author Thierry Schreiner
 */
@Entity
@Table(name = "buddy")
public class Buddy {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String confirmPassword;


    @ManyToMany
    @JoinTable(name = "buddy_role",
        joinColumns = @JoinColumn(name = "buddy_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles;



}
