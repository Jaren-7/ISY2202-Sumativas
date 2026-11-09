package com.isy2202.duocplay.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UsuarioRequestDTO {
    
    @Data 
    @NoArgsConstructor 
    @AllArgsConstructor 
    @Builder 
    public static class Create {

        @NotBlank(message = "El nombre no puede estar vacío")
        private String nombre;

        @NotBlank(message = "el nombre de usuario no puede estar vacío")
        private String username;

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email no tiene un formato válido")
        private String email;

        @NotBlank(message = "La contraseña no puede estar vacía")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Update {

        @NotNull(message = "Debe indicar el ID del usuario")
        private int id_usuario;

        @NotBlank(message = "El nombre no puede estar vacío")
        private String nombre;

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email no tiene un formato válido")
        private String email;
    }
}
