package com.uah.tfm.zakado.zkd.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "idgenerator_language")
    @SequenceGenerator(name = "idgenerator_language",
            initialValue = 1)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(length = 2, unique = true)
    private String isocode; // ej: "ES", "EN", "FR"

    @ManyToMany(mappedBy = "languages")
    @JsonIgnoreProperties({"languages"})
    private Set<Employee> employees = new HashSet<>();
}
