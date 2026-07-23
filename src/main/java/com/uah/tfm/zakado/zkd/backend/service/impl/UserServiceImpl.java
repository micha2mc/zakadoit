package com.uah.tfm.zakado.zkd.backend.service.impl;

import com.uah.tfm.zakado.zkd.backend.data.entity.User;
import com.uah.tfm.zakado.zkd.backend.data.repository.UserRepository;
import com.uah.tfm.zakado.zkd.backend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "USER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User createUser(String username, String rawPassword, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(DEFAULT_ROLE);
        user.setEmail(email);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void setEnabled(Long userId, boolean enabled) {
        userRepository.findById(userId).ifPresent(u -> {
            u.setEnabled(enabled);
            userRepository.save(u);
        });
    }

    @Override
    public void setRole(Long userId, String role) {
        userRepository.findById(userId).ifPresent(u -> {
            u.setRole(role);
            userRepository.save(u);
        });
    }
}
