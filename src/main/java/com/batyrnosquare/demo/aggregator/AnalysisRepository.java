package com.batyrnosquare.demo.aggregator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<AnalysisModel, Long>{
    AnalysisModel findTopByPatient_IdOrderByIdDesc(Long patientId);
}
