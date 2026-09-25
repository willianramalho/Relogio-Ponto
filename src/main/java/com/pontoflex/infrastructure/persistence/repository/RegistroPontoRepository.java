package com.pontoflex.infrastructure.persistence.repository;

import com.pontoflex.infrastructure.persistence.entity.RegistroPonto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, UUID> {

    Optional<RegistroPonto> findFirstByColaboradorIdOrderByHorarioUtcDesc(UUID colaboradorId);

    List<RegistroPonto> findByColaboradorIdAndDataReferenciaOrderByHorarioUtcAsc(
            UUID colaboradorId, LocalDate dataReferencia);
}
