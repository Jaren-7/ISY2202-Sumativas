package com.isy2202.duocplay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType;
    private String username;
    private String rol;
}
