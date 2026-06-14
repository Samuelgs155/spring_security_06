package com.spring.security.app_security.security.jwt;

import com.spring.security.app_security.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository
                .findByEmail(username)
                .map(customer -> {
                    final var authorities = customer.getRoles()
                            .stream().map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                            .toList();
                    return new User(customer.getEmail(), customer.getPassword(), authorities);
                }).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }
}
