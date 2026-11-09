package com.isy2202.duocplay.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.isy2202.duocplay.dto.request.EventoRequestDTO;
import com.isy2202.duocplay.dto.response.EventoResponseDTO;
import com.isy2202.duocplay.model.EventoModel;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    
    @Mapping(target = "id_evento", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "usuario_creador", ignore = true)
    EventoModel toEntityCreate(EventoRequestDTO.Create request);

    // Actualizar un EventoModel existente
    @Mapping(target = "id_evento", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "usuario_creador", ignore = true)
    void updateEntity(EventoRequestDTO.Update request,@MappingTarget EventoModel evento);

    EventoResponseDTO toResponse(EventoModel evento);

    List<EventoResponseDTO> toResponseList(List<EventoModel> eventos);
}
