package com.pontoflex.infrastructure.security;

import com.pontoflex.infrastructure.persistence.entity.Usuario;
import com.pontoflex.infrastructure.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado: " + email));

        var authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(), usuario.getSenhaHash(), authorities);
    }
}
