package com.isy2202.duocplay.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isy2202.duocplay.model.UsuarioModel;
import com.isy2202.duocplay.repository.interfaces.IUsuarioRepository;

@Service 
public class UsuarioDetailsService implements UserDetailsService {
    
    private final IUsuarioRepository _repo;

    public UsuarioDetailsService(IUsuarioRepository repo) {
        _repo = repo;
    }

    @Override 
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
            UsuarioModel usuario = _repo.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            return new UsuarioDetails(usuario);
        }
}
