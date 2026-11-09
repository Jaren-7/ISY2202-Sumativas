package com.isy2202.duocplay.service.interfaces;

import java.util.List;

import com.isy2202.duocplay.dto.request.UsuarioRequestDTO;
import com.isy2202.duocplay.dto.response.UsuarioResponseDTO;

public interface IUsuarioService {
    
    int crearUsuario(UsuarioRequestDTO.Create request);

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO buscarUsuarioId(int id_usuario);

    UsuarioResponseDTO actualizarUsuario(UsuarioRequestDTO.Update request);

    void desactivarUsuario(int id_usuario);
}
