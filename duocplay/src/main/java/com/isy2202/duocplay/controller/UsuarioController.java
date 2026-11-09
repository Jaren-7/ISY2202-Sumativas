package com.isy2202.duocplay.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isy2202.duocplay.dto.request.UsuarioRequestDTO;
import com.isy2202.duocplay.dto.response.UsuarioResponseDTO;
import com.isy2202.duocplay.service.interfaces.IUsuarioService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final IUsuarioService _service;

    public UsuarioController(IUsuarioService service) {
        _service = service;
    }

    @PostMapping
    public ResponseEntity<Integer> CrearUsuario(@Valid @RequestBody UsuarioRequestDTO.Create request) {
        int result = _service.crearUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping 
    public ResponseEntity<List<UsuarioResponseDTO>> ObtenerUsuarios() {
        List<UsuarioResponseDTO> usuarios = _service.getAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{idusuario}")
    public ResponseEntity<UsuarioResponseDTO> BuscarUsuarioId(@PathVariable int idusuario) {

        UsuarioResponseDTO response = _service.buscarUsuarioId(idusuario);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDTO> ActualizarUsuario(@Valid @RequestBody UsuarioRequestDTO.Update request) {

        UsuarioResponseDTO response = _service.actualizarUsuario(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idusuario}")
    public ResponseEntity<Void> DesactivarUsuario(
            @PathVariable int idusuario) {

        _service.desactivarUsuario(idusuario);

        return ResponseEntity.noContent().build();
    }
}
