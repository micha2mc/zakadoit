package com.uah.tfm.zakado.zkd.backend.data.mapper.dto;

import com.uah.tfm.zakado.zkd.backend.data.entity.AreaEntity;
import com.uah.tfm.zakado.zkd.backend.data.entity.CompanyEntity;
import com.uah.tfm.zakado.zkd.backend.data.entity.LanguageEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String fullName;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Introduce un email válido, ej: nombre@dominio.com")
    private String email;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate dob;
    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    @Max(value = 50, message = "Revisa el valor, parece demasiado alto")
    private int yearOfExperience;
    @NotNull(message = "El salario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor que 0")
    private BigDecimal annualSalary;
    @NotNull
    private CompanyEntity company;
    @NotNull
    private AreaEntity area;
    private String corporateKey;
    private String career;
    private Set<LanguageEntity> languages = new HashSet<>();
}
