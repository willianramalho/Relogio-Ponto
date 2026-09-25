package com.pontoflex.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "local_trabalho")
@Getter
@Setter
@NoArgsConstructor
public class LocalTrabalho {

    @Id
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "raio_metros", nullable = false)
    private Integer raioMetros;

    @Column(nullable = false, length = 100)
    private String municipio;

    @Column(nullable = false, length = 2)
    private String estado;
}
