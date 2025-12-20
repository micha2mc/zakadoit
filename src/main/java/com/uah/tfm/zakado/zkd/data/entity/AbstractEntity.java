package com.uah.tfm.zakado.zkd.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "idgenerator")
    @SequenceGenerator(name = "idgenerator",
            initialValue = 1000)
    private Long id;
}
