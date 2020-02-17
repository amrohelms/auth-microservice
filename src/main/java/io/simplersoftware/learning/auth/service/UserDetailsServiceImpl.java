package io.simplersoftware.learning.auth.service;

import io.simplersoftware.learning.auth.model.Role;
import io.simplersoftware.learning.auth.model.User;
import io.simplersoftware.learning.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetails userDetails;


    public UserDetailsServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            User user = userRepository.findByEmail(email);
            Set grantedAuthorities = new HashSet<>();

            for (Role role : user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            userDetails = new UserDetailsImpl(user.getEmail(), user.getPassword(), Long.toString(user.getId()), grantedAuthorities);
            return userDetails;
        }
        catch (Exception exception){
            throw new UsernameNotFoundException("User not found");
        }
    }
}
