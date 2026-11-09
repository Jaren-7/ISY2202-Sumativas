package com.isy2202.duocplay.repository.impl;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import com.isy2202.duocplay.model.UsuarioModel;
import com.isy2202.duocplay.repository.interfaces.IUsuarioRepository;

@Repository 
public class UsuarioRepository implements IUsuarioRepository {

    private final Jdbi _jdbi;

    public UsuarioRepository(Jdbi jdbi) {
        _jdbi = jdbi;
    }

    @Override
    public List<UsuarioModel> getAll() {
        
        String sql = """
        SELECT * FROM DUOCPLAY.USUARIO
        ORDER BY ID_USUARIO ASC
        """;

        return _jdbi.withHandle(handle ->
            handle.createQuery(sql)
            .mapToBean(UsuarioModel.class)
            .list()
        );
        
    }

    @Override
    public Optional<UsuarioModel> getById(int id_usuario) {
        
        String sql = """
        SELECT * FROM DUOCPLAY.USUARIO WHERE ID_USUARIO = :id_usuario
        """;

        return _jdbi.withHandle(handle ->
            handle.createQuery(sql)
            .bind("id_usuario", id_usuario)
            .mapToBean(UsuarioModel.class)
            .findOne()
        );
    }

    @Override
    public Optional<UsuarioModel> getByUsername(String username) {
        
        String sql = """
        SELECT * FROM DUOCPLAY.USUARIO WHERE USERNAME = :username
        """;

        return _jdbi.withHandle(handle ->
            handle.createQuery(sql)
            .bind("username", username)
            .mapToBean(UsuarioModel.class)
            .findOne()
        );
    }

    @Override
    public int create(UsuarioModel usuario) {
        
        String sql = """
        INSERT INTO DUOCPLAY.USUARIO (
        NOMBRE,
        USERNAME,
        EMAIL,
        PASSWORD_HASH,
        ROL,
        ACTIVO,
        )
        VALUES (
        :nombre,
        :email,
        :password_hash,
        :rol,
        :activo
        )
        """;

        return _jdbi.withHandle(handle ->
            handle.createUpdate(sql)
            .bind("nombre", usuario.getNombre())
            .bind("username", usuario.getUsername())
            .bind("email", usuario.getEmail())
            .bind("password_hash", usuario.getPassword_hash())
            .bind("rol", usuario.getRol())
            .bind("activo", usuario.getActivo())
            .executeAndReturnGeneratedKeys("ID_USUARIO")
            .mapTo(int.class)
            .one()
        );
    }

    @Override 
    public int update(UsuarioModel usuario) {
        
        String sql = """
        UPDATE DUOCPLAY.USUARIO
        SET NOMBRE = :nombre, EMAIL = :email
        WHERE ID_USUARIO = :id_usuario
        """;

        return _jdbi.withHandle(handle -> 
            handle.createUpdate(sql)
            .bind("nombre", usuario.getNombre())
            .bind("email", usuario.getEmail())
            .bind("id_usuario", usuario.getId_usuario())
            .execute()
        );
    }
    
    @Override 
    public int desactivate(int id_usuario) {
        
        String sql = """
        UPDATE DUOCPLAY.USUARIO
        SET ACTIVO = :activo
        WHERE ID_USUARIO = :id_usuario
        """;

        return _jdbi.withHandle(handle ->
            handle.createUpdate(sql)
            .bind("activo",UsuarioModel.EstadoUsuario.INACTIVO)
            .bind("id_usuario", id_usuario)
            .execute()
        );
    }
}
