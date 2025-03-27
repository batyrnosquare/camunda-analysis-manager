package com.batyrnosquare.demo.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {
    PatientModel findById(int patientId);

    PatientModel findByEmail(String email);
}
