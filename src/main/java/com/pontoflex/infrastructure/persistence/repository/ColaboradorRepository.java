package com.pontoflex.infrastructure.persistence.repository;

import com.pontoflex.infrastructure.persistence.entity.Colaborador;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> {

    boolean existsByCpf(String cpf);

    boolean existsByMatricula(String matricula);
}
