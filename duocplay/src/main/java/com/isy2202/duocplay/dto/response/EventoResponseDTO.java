package com.isy2202.duocplay.dto.response;

import java.time.LocalDateTime;

import com.isy2202.duocplay.model.EventoModel;

import lombok.Data;

@Data 
public class EventoResponseDTO {
    private int id_evento;
    private String nombre;
    private String descripcion;
    private String videojuego;
    private String categoria;
    private String modalidad;
    private String ubicacion;
    private String url_evento;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    private EventoModel.EstadoEvento estado;
    private String usuario_creador;
}
