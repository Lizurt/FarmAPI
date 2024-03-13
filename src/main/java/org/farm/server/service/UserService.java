package org.farm.server.service;

import org.farm.server.model.entities.UserEntity;
import org.farm.server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity create(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            // todo exceptions or messages to requester?
            return null;
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }

        return save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
