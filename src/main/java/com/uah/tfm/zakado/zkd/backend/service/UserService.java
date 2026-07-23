package com.uah.tfm.zakado.zkd.backend.service;

import com.uah.tfm.zakado.zkd.backend.data.entity.User;

import java.util.List;

public interface UserService {
    User createUser(String username, String rawPassword, String email);
    List<User> findAll();
    void setEnabled(Long userId, boolean enabled);
    void setRole(Long userId, String role);
}
