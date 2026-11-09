package com.isy2202.duocplay.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.isy2202.duocplay.model.EventoModel;

public interface IEventoRepository {
    
    List<EventoModel> getAll();

    Optional<EventoModel> getById(int id_evento);

    int create(EventoModel evento);

    int update(EventoModel evento);

    int desactivate(int id_evento);
}
