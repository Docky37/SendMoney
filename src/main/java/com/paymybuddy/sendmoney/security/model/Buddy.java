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
public class Buddy implements UserDetails {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -5322172864212606550L;

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

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.REMOVE)
    @JoinTable(name = "buddy_role", joinColumns = @JoinColumn(name = "buddy_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles;

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

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
