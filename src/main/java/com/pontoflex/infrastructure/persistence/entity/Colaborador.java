package com.pontoflex.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "colaborador")
@Getter
@Setter
@NoArgsConstructor
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 30)
    private String matricula;

    @Column(name = "escala_id", nullable = false)
    private UUID escalaId;

    @Column(name = "local_trabalho_id", nullable = false)
    private UUID localTrabalhoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gestor_id")
    private Colaborador gestor;

    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colaborador other)) {
            return false;
        }
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
