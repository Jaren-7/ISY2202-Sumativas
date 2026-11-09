package com.isy2202.duocplay.service.interfaces;

import java.util.List;

import com.isy2202.duocplay.dto.request.EventoRequestDTO;
import com.isy2202.duocplay.dto.response.EventoResponseDTO;

public interface IEventoService {
    
    EventoResponseDTO create(EventoRequestDTO.Create request,String usuario_creador);

    List<EventoResponseDTO> obtenerEventos();

    EventoResponseDTO obtenerEventoId(int id_evento);

    EventoResponseDTO actualizarEvento(EventoRequestDTO.Update request);

    void desactivarEvento(int id_evento);
}
