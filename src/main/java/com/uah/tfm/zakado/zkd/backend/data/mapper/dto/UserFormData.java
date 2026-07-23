package com.uah.tfm.zakado.zkd.backend.data.mapper.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFormData {
    @NotEmpty(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotEmpty(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotEmpty(message = "El email es obligatorio")
    @Email(message = "Introduce un email válido")
    private String email;
}
