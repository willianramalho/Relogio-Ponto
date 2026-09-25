package com.pontoflex.infrastructure.persistence.entity;

import com.pontoflex.domain.ponto.StatusRegistro;
import com.pontoflex.domain.ponto.TipoRegistro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "registro_ponto")
@Getter
@Setter
@NoArgsConstructor
public class RegistroPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "colaborador_id", nullable = false)
    private UUID colaboradorId;

    @Column(name = "horario_utc", nullable = false)
    private Instant horarioUtc;

    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoRegistro tipo;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "precisao_metros", precision = 10, scale = 2)
    private BigDecimal precisaoMetros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusRegistro status;

    @Column(nullable = false, length = 20)
    private String origem = "APP";

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private Instant criadoEm;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroPonto other)) {
            return false;
        }
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
