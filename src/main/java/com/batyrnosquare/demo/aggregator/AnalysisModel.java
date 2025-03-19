package com.batyrnosquare.demo.aggregator;

import com.batyrnosquare.demo.constants.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "analysis")
public class AnalysisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Gender gender;
    private int hemoglobin;
    private int platelet;
    private double glucose;

    public AnalysisModel(Long id, Gender gender, int hemoglobin, int platelet, double glucose) {
        this.id = id;
        this.gender = gender;
        this.hemoglobin = hemoglobin;
        this.platelet = platelet;
        this.glucose = glucose;
    }

    public AnalysisModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
