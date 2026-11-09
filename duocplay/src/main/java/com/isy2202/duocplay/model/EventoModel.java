package com.isy2202.duocplay.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class EventoModel {
    
    public enum EstadoEvento {
        ACTIVO,
        INACTIVO
    }

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
    private EstadoEvento estado;
    private String usuario_creador;
}
