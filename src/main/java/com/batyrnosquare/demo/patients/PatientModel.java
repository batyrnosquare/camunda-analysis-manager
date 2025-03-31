package com.batyrnosquare.demo.patients;

import com.batyrnosquare.demo.aggregator.AnalysisModel;
import com.batyrnosquare.demo.constants.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "patients")
public class PatientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DiagnosisModel> diagnosis;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<AnalysisModel> analysis;

    public PatientModel(Long id, String name, String surname, String email, Gender gender, List<DiagnosisModel> diagnosis, List<AnalysisModel> analysis) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.analysis = analysis;
    }

    public PatientModel() {
        this.diagnosis = new ArrayList<>();
        this.analysis = new ArrayList<>();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<DiagnosisModel> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<DiagnosisModel> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<AnalysisModel> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<AnalysisModel> analysis) {
        this.analysis = analysis;
    }
}
