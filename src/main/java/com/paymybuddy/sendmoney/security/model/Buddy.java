package com.paymybuddy.sendmoney.security.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * The Buddy class is the user entity of Pay My Buddy.
 *
 * @author Thierry Schreiner
 */
@Entity
@Table(name = "buddy")
public class Buddy implements UserDetails {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -5322172864212606550L;

    /**
     * The Buddy's id field.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The Buddy's first name.
     */
    @Getter
    @Setter
    private String firstName;

    /**
     * The Buddy's last name.
     */
    @Getter
    @Setter
    private String lastName;

    /**
     * The Buddy's email, used as user name for login.
     */
    @Getter
    @Setter
    private String email;

    /**
     * The Buddy's encrypted password.
     */
    @Getter
    @Setter
    private String password;

    /**
     * The join table used to map buddy and its roles.
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.REMOVE)
    @JoinTable(name = "buddy_role",
        joinColumns = @JoinColumn(name = "buddy_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles;

    /**
     * Boolean field that tells us if account is expired.
     */
    @Getter
    @Setter
    private boolean accountNonExpired = true;

    /**
     * Boolean field that tells us if account is locked.
     */
    @Getter
    @Setter
    private boolean accountNonLocked = true;

    /**
     * Boolean field that tells us if credentials are expired.
     */
    @Getter
    @Setter
    private boolean credentialsNonExpired = true;

    /**
     * Boolean field that tells us if account is enabled.
     */
    @Getter
    @Setter
    private boolean enabled = true;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String stringRoles = "";
        Iterator<Role> it = roles.iterator();
        while (it.hasNext()) {
            if (!stringRoles.isEmpty()) {
                stringRoles = stringRoles.concat(", ");
            }
            stringRoles = stringRoles.concat(it.next().getName());
        }
        System.out.println(stringRoles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(stringRoles);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getUsername() {
        return email;
    }

}
