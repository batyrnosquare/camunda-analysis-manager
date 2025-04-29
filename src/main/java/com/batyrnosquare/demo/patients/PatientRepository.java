package com.batyrnosquare.demo.patients;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {
    Optional<PatientModel> findByEmail(String email);

    Boolean existsByEmail(@Email String email);

}
