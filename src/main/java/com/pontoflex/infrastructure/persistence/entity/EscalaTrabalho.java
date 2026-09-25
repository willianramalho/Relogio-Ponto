package com.pontoflex.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "escala_trabalho")
@Getter
@Setter
@NoArgsConstructor
public class EscalaTrabalho {

    @Id
    private UUID id;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(name = "horas_diarias_previstas", nullable = false)
    private Integer horasDiariasPrevistas;

    /** Abreviacoes dos dias trabalhados: SEG, TER, QUA, QUI, SEX, SAB, DOM. */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dias_trabalho", nullable = false, columnDefinition = "jsonb")
    private List<String> diasTrabalho = new ArrayList<>();
}
