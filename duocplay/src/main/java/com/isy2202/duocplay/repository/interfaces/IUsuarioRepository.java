package com.isy2202.duocplay.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.isy2202.duocplay.model.UsuarioModel;

public interface IUsuarioRepository {
    
    List<UsuarioModel> getAll();

    Optional<UsuarioModel> getById(int id_usuario);

    Optional<UsuarioModel> getByUsername(String username);

    int create(UsuarioModel usuario);

    int update(UsuarioModel usuario);

    int desactivate(int id_usuario);
}
