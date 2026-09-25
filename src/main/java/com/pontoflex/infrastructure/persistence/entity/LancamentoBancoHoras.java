package com.pontoflex.infrastructure.persistence.entity;

import com.pontoflex.domain.bancohoras.TipoLancamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Lancamento imutavel do ledger do banco de horas: o saldo e sempre a soma dos lancamentos.
 */
@Entity
@Table(name = "lancamento_banco_horas")
@Getter
@Setter
@NoArgsConstructor
public class LancamentoBancoHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "colaborador_id", nullable = false)
    private UUID colaboradorId;

    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;

    @Column(nullable = false)
    private Integer minutos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoLancamento tipo;

    @Column(name = "data_expiracao")
    private LocalDate dataExpiracao;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private Instant criadoEm;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LancamentoBancoHoras other)) {
            return false;
        }
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
