package com.paperwork.paperworkapp.auth.service;

import com.paperwork.paperworkapp.auth.repository.RoleRepository;
import com.paperwork.paperworkapp.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;


@Component
public class UserDetailsImpl implements UserDetails {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private String username;
    private String password;
    private String userId;
    private Set<SimpleGrantedAuthority> roles;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(String username, String password, String userId, Set<SimpleGrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "userRepository=" + userRepository +
                ", roleRepository=" + roleRepository +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", roles=" + roles +
                '}';
    }
}
