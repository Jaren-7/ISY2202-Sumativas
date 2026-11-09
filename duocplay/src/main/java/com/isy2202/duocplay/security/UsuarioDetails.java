package com.isy2202.duocplay.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.isy2202.duocplay.model.UsuarioModel;

public class UsuarioDetails implements UserDetails {
    
    private final UsuarioModel _usuario;

    public UsuarioDetails(UsuarioModel usuario) {
        _usuario = usuario;
    }

    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + _usuario.getRol().name()));
    }

    @Override
    public String getPassword() {
        return _usuario.getPassword_hash();
    }

    @Override
    public String getUsername() {
        return _usuario.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return _usuario.getActivo() == UsuarioModel.EstadoUsuario.ACTIVO;
    }

    @Override 
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override 
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override 
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
