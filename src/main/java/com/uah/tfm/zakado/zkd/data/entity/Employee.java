package com.uah.tfm.zakado.zkd.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "idgenerator")
    @SequenceGenerator(name = "idgenerator",
            initialValue = 1000)
    private Long id;

    @NotEmpty
    private String corporateKey;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @Column(name = "date_of_birth")
    @NotNull
    private LocalDate dob;

    private String career;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties({"employees"})
    @NotNull
    private Company company;


    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "area_id", nullable = false)
    @JsonIgnoreProperties({"employees"})
    private Area area;

    @ManyToMany
    @JoinTable(
            name = "employee_language",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    @JsonIgnoreProperties({"employees"})
    @Builder.Default
    private Set<Language> languages = new HashSet<>();

}
