package com.isy2202.duocplay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class UsuarioResponseDTO {

    private Long id_usuario;
    private String nombre;
    private String username;
    private String email;
    private String rol;
    private String activo;
}
