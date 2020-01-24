package com.restaurant.restaurant.security.jwt;

import com.restaurant.restaurant.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Антон on 09.01.2020.
 */
public class JwtUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String lastname;
    private final String address;
    private final String phonenumber;
    private final Collection<SimpleGrantedAuthority> autorities;


    public JwtUser(Long id, String email, String password, String name, String lastname, String address, String phonenumber,
                   Role role) {
        this.id = id;
        this.username = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phonenumber = phonenumber;
        this.autorities = new ArrayList<>();
        autorities.add(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
