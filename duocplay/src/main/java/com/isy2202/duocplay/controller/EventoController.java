package com.isy2202.duocplay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.isy2202.duocplay.dto.request.EventoRequestDTO;
import com.isy2202.duocplay.dto.response.EventoResponseDTO;
import com.isy2202.duocplay.service.interfaces.IEventoService;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping("/api/eventos")
public class EventoController {
    
    private final IEventoService _service;

    public EventoController(IEventoService service) {
        _service = service;
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> CrearEvento(@Valid @RequestBody EventoRequestDTO.Create request, Principal principal) {
        
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Debe iniciar sesión para crear un evento");
        }        

        EventoResponseDTO response = _service.create(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> ObtenerEventos() {
        List<EventoResponseDTO> eventos = _service.obtenerEventos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{idevento}")
    public ResponseEntity<EventoResponseDTO> ObtenerEventoId(@PathVariable int idevento) {
        EventoResponseDTO response = _service.obtenerEventoId(idevento);
        return ResponseEntity.ok(response);
    }

    @PutMapping 
    public ResponseEntity<EventoResponseDTO> ActualizarEvento(@Valid @RequestBody EventoRequestDTO.Update request) {
        EventoResponseDTO response = _service.actualizarEvento(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idevento}")
    public ResponseEntity<Void> DesactivarEvento(@PathVariable int idevento) {
        _service.desactivarEvento(idevento);
        return ResponseEntity.noContent().build();
    }
    
}
