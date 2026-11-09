package com.isy2202.duocplay.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isy2202.duocplay.dto.request.UsuarioRequestDTO;
import com.isy2202.duocplay.dto.request.UsuarioRequestDTO.Create;
import com.isy2202.duocplay.dto.response.UsuarioResponseDTO;
import com.isy2202.duocplay.mapper.UsuarioMapper;
import com.isy2202.duocplay.model.UsuarioModel;
import com.isy2202.duocplay.model.UsuarioModel.EstadoUsuario;
import com.isy2202.duocplay.model.UsuarioModel.RolUsuario;
import com.isy2202.duocplay.repository.interfaces.IUsuarioRepository;
import com.isy2202.duocplay.service.interfaces.IUsuarioService;

@Service 
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository _repo;
    private final UsuarioMapper _mapper;
    private final PasswordEncoder _passwordEncoder;

    public UsuarioService(IUsuarioRepository repo, UsuarioMapper mapper, PasswordEncoder passwordEncoder) {
        _repo = repo;
        _mapper = mapper;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public int crearUsuario(Create request) {
        if (_repo.getByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El Usuario ya existe");
        }

        UsuarioModel usuario = _mapper.toEntityCreate(request);

        usuario.setPassword_hash(_passwordEncoder.encode(request.getPassword()));

        usuario.setRol(RolUsuario.USUARIO);
        usuario.setActivo(EstadoUsuario.ACTIVO);

        return _repo.create(usuario);

    }

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<UsuarioModel> usuarios = _repo.getAll();
        return _mapper.toResponseList(usuarios);    
    }

    @Override
    public UsuarioResponseDTO buscarUsuarioId(int id_usuario) {
        UsuarioModel usuario = _repo.getById(id_usuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return _mapper.toResponse(usuario); 
    }

    @Override 
    public UsuarioResponseDTO actualizarUsuario(UsuarioRequestDTO.Update request) {

        UsuarioModel usuario = _repo.getById(request.getId_usuario()).orElseThrow(() -> new RuntimeException("El usuario no existe"));
        
        _mapper.updateEntity(request, usuario);

        int filasActualizadas = _repo.update(usuario);

        if (filasActualizadas == 0) {
            throw new RuntimeException("No se pudo actualizar el usuario");
        }

        return _mapper.toResponse(usuario);
    }

    @Override 
    public void desactivarUsuario(int id_usuario) {
        
        UsuarioModel usuario = _repo.getById(id_usuario).orElseThrow(() -> new RuntimeException("El usuario no existe"));

        if (usuario.getActivo() == UsuarioModel.EstadoUsuario.INACTIVO) {
            throw new RuntimeException("El usuario ya se encuentra inactivo");
        }

        int filasActualizadas = _repo.desactivate(id_usuario);

        if (filasActualizadas == 0) {
            throw new RuntimeException("No se pudo desactivar el usuario");
        }
    }
}