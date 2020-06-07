package com.paymybuddy.sendmoney.security.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "roles")
    private Set<Buddy> users;
}
