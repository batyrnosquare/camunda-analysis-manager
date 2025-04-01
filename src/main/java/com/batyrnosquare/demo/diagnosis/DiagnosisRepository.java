package com.batyrnosquare.demo.diagnosis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiagnosisRepository extends JpaRepository<DiagnosisModel, Long> {
    Optional<DiagnosisModel> findById(Long diagnosisId);
}
