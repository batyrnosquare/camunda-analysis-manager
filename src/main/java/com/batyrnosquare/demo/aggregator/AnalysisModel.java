package com.batyrnosquare.demo.aggregator;

import com.batyrnosquare.demo.patients.PatientModel;
import jakarta.persistence.*;

@Entity(name = "analysis")
public class AnalysisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patient;


    private int hemoglobin;
    private int platelet;
    private double glucose;

    public AnalysisModel(Long id, PatientModel patient, int hemoglobin, int platelet, double glucose) {
        this.id = id;
        this.patient = patient;
        this.hemoglobin = hemoglobin;
        this.platelet = platelet;
        this.glucose = glucose;
    }

    public AnalysisModel() {
    }

    public AnalysisModel(PatientModel patient, int hemoglobin, int platelet, double glucose) {
        this.patient = patient;
        this.hemoglobin = hemoglobin;
        this.platelet = platelet;
        this.glucose = glucose;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientModel getPatient() {
        return patient;
    }

    public void setPatient(PatientModel patient) {
        this.patient = patient;
    }

    public int getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(int hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public int getPlatelet() {
        return platelet;
    }

    public void setPlatelet(int platelet) {
        this.platelet = platelet;
    }

    public double getGlucose() {
        return glucose;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }
}
