package com.sesap.UGTSIC.repositories;

import com.sesap.UGTSIC.models.CandidateModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<CandidateModel, Long> {
}
