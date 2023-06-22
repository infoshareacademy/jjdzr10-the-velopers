package com.infoshare.webapp.service;

import com.infoshare.webapp.model.User;
import com.infoshare.webapp.model.Role;
import com.infoshare.webapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRoles()))
                .build();
    }

        private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            }
            return authorities;
        }
}
