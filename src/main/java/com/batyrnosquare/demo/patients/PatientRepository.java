package com.batyrnosquare.demo.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {
    PatientModel findById(int patientId);

    Optional<PatientModel> findByEmail(String email);

}
