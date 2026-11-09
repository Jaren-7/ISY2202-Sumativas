package com.isy2202.duocplay.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isy2202.duocplay.dto.request.EventoRequestDTO.Create;
import com.isy2202.duocplay.dto.request.EventoRequestDTO.Update;
import com.isy2202.duocplay.dto.response.EventoResponseDTO;
import com.isy2202.duocplay.mapper.EventoMapper;
import com.isy2202.duocplay.model.EventoModel;
import com.isy2202.duocplay.repository.interfaces.IEventoRepository;
import com.isy2202.duocplay.service.interfaces.IEventoService;

@Service 
public class EventoService implements IEventoService {

    private final IEventoRepository _repo;
    private final EventoMapper _mapper;

    public EventoService(IEventoRepository repo, EventoMapper mapper) {
        _repo = repo;
        _mapper = mapper;
    }

    @Override
    public EventoResponseDTO create(Create request, String usuario_creador) {

        if (usuario_creador == null || usuario_creador.isBlank()) {
            throw new RuntimeException("No se pudo identificar al usuario creador");    
        }

        EventoModel evento = _mapper.toEntityCreate(request);

        evento.setEstado(EventoModel.EstadoEvento.ACTIVO);
        evento.setUsuario_creador(usuario_creador);

        int id_evento = _repo.create(evento);

        EventoModel eventoCreado = _repo.getById(id_evento).orElseThrow(() -> new RuntimeException("No se pudo recuperar el evento creado"));

        return _mapper.toResponse(eventoCreado);
    }

    @Override
    public List<EventoResponseDTO> obtenerEventos() {
        List<EventoModel> eventos = _repo.getAll();
        return _mapper.toResponseList(eventos);
    }

    @Override
    public EventoResponseDTO obtenerEventoId(int id_evento) {
        EventoModel evento = _repo.getById(id_evento).orElseThrow(() -> new RuntimeException("El evento no existe"));
        return _mapper.toResponse(evento);
    }

    @Override
    public EventoResponseDTO actualizarEvento(Update request) {
    
        EventoModel evento = _repo.getById(request.getId_evento()).orElseThrow(() -> new RuntimeException("El evento no existe"));

        _mapper.updateEntity(request, evento);

        int filasActualizadas = _repo.update(evento);

        if (filasActualizadas == 0) {
            throw new RuntimeException("No se pudo actualizar el evento");
        }

        return _mapper.toResponse(evento);
    }

    @Override
    public void desactivarEvento(int id_evento) {
        
        EventoModel evento = _repo.getById(id_evento).orElseThrow(() -> new RuntimeException("El evento no existe"));

        if (evento.getEstado() == EventoModel.EstadoEvento.INACTIVO) {
            throw new RuntimeException("El evento ya se encuentra inactivo");            
        }

        int filasActualizadas = _repo.desactivate(id_evento);

        if (filasActualizadas == 0) {
            throw new RuntimeException("No se pudo desactivar el evento");
        }
    }
    
}
