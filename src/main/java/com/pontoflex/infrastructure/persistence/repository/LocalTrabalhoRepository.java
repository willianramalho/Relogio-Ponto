package com.pontoflex.infrastructure.persistence.repository;

import com.pontoflex.infrastructure.persistence.entity.LocalTrabalho;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalTrabalhoRepository extends JpaRepository<LocalTrabalho, UUID> {}
