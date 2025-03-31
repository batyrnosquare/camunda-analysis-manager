package com.batyrnosquare.demo.patients;

import com.batyrnosquare.demo.constants.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDTO {
    private String name;
    private String surname;
    private String email;
    private Gender gender;
    private List<String> diagnosis;

    public PatientDTO(PatientModel patientModel) {
        this.name = patientModel.getName();
        this.surname = patientModel.getSurname();
        this.email = patientModel.getEmail();
        this.gender = patientModel.getGender();
        this.diagnosis = patientModel.getDiagnosis().stream().map(DiagnosisModel::getName).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public List<String> getDiagnosis() {
        return diagnosis;
    }
}
