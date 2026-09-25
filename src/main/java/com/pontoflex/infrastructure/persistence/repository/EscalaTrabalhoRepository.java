package com.pontoflex.infrastructure.persistence.repository;

import com.pontoflex.infrastructure.persistence.entity.EscalaTrabalho;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalaTrabalhoRepository extends JpaRepository<EscalaTrabalho, UUID> {}
