package com.atexo.test.generationservice.repository;

import com.atexo.test.generationservice.model.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {
}
