package com.isy2202.duocplay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

public class EventoRequestDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        @NotBlank
        @Size(max = 150)
        private String nombre;

        @Size(max = 255)
        private String descripcion;

        @NotBlank
        @Size(max = 100)
        private String videojuego;

        @NotBlank
        @Size(max = 80)
        private String categoria;

        @NotBlank
        @Size(max = 20)
        private String modalidad;

        @Size(max = 200)
        private String ubicacion;

        @Size(max = 500)
        private String url_evento;

        private LocalDateTime fecha_inicio;

        private LocalDateTime fecha_fin;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {

        @NotNull
        private int id_evento;

        @NotBlank
        @Size(max = 150)
        private String nombre;

        @Size(max = 255)
        private String descripcion;

        @NotBlank
        @Size(max = 100)
        private String videojuego;

        @NotBlank
        @Size(max = 80)
        private String categoria;

        @NotBlank
        @Size(max = 20)
        private String modalidad;

        @Size(max = 200)
        private String ubicacion;

        @Size(max = 500)
        private String url_evento;

        @NotNull
        private LocalDateTime fecha_inicio;

        private LocalDateTime fecha_fin;
    }
}
