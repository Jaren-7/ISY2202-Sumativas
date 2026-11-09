package com.isy2202.duocplay.model;

import lombok.Data;

@Data 
public class UsuarioModel {
    
    public enum RolUsuario {
        ADMIN,
        ORGANIZADOR,
        USUARIO
    }

    public enum EstadoUsuario {
        ACTIVO,
        INACTIVO
    }

    private int id_usuario;
    private String nombre;
    private String username;
    private String email;
    private String password_hash;
    private RolUsuario rol;
    private EstadoUsuario activo;
}
