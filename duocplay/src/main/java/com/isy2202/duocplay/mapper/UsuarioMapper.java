package com.isy2202.duocplay.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.isy2202.duocplay.dto.request.UsuarioRequestDTO;
import com.isy2202.duocplay.dto.response.UsuarioResponseDTO;
import com.isy2202.duocplay.model.UsuarioModel;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    
    @Mapping(target = "id_usuario", ignore = true)
    @Mapping(target = "password_hash", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "activo", ignore = true)
    UsuarioModel toEntityCreate(UsuarioRequestDTO.Create request);

    @Mapping(target = "id_usuario", source = "id_usuario")
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password_hash", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "activo", ignore = true)
    UsuarioModel toEntityUpdate(UsuarioRequestDTO.Update request);

    UsuarioResponseDTO toResponse(UsuarioModel usuario);

    List<UsuarioResponseDTO> toResponseList(List<UsuarioModel> usuarios);

    @Mapping(target = "id_usuario", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password_hash", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateEntity(UsuarioRequestDTO.Update request,
    @MappingTarget UsuarioModel usuario
);
}
