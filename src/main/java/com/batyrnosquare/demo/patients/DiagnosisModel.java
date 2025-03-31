package com.batyrnosquare.demo.patients;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity(name = "diagnosis")
public class DiagnosisModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference
    private PatientModel patient;

    @JsonCreator
    public DiagnosisModel(@JsonProperty("name") String name, @JsonProperty("patient") PatientModel patient) {
        this.name = name;
        this.patient = patient;
    }

    @JsonCreator
    public DiagnosisModel(@JsonProperty("name") String name) {
        this.name = name;
    }

    public DiagnosisModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }
}
