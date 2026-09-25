package com.pontoflex.infrastructure.persistence.repository;

import com.pontoflex.infrastructure.persistence.entity.LancamentoBancoHoras;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LancamentoBancoHorasRepository extends JpaRepository<LancamentoBancoHoras, UUID> {

    @Query("select coalesce(sum(l.minutos), 0) from LancamentoBancoHoras l where l.colaboradorId = :colaboradorId")
    int somarMinutos(UUID colaboradorId);

    @Query("""
            select coalesce(sum(l.minutos), 0) from LancamentoBancoHoras l
            where l.colaboradorId = :colaboradorId and l.dataReferencia = :data
            """)
    int somarMinutosDoDia(UUID colaboradorId, LocalDate data);

    List<LancamentoBancoHoras> findByColaboradorIdOrderByDataReferenciaDescCriadoEmDesc(
            UUID colaboradorId, Pageable pageable);
}
