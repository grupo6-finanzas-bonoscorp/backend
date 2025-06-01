package com.example.backendBonosCorp.iam.infrastructure.authorization.sfs.services;

import com.example.backendBonosCorp.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.example.backendBonosCorp.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

@Service(value = "defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This method is used to load user details by RUC (Registro Único de Contribuyentes)
    @Override
    public UserDetails loadUserByUsername(String ruc) throws UsernameNotFoundException {
        var user = userRepository.findByRuc(ruc)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + ruc));
        return (UserDetails) UserDetailsImpl.build(user);
    }
}
