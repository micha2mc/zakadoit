package com.uah.tfm.zakado.zkd.backend.service.impl;

import com.uah.tfm.zakado.zkd.backend.data.entity.User;
import com.uah.tfm.zakado.zkd.backend.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // ✅ Devolvemos el User directamente (ya implementa UserDetails)
        // Si el User ya tiene el rol con "ROLE_", no lo añadimos de nuevo
        return user;
    }
}
