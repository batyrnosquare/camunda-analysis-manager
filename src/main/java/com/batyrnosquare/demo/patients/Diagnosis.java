package com.batyrnosquare.demo.patients;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("diagnosisDelegate")
public class Diagnosis implements JavaDelegate {

    private final PatientRepository patientRepository;

    public Diagnosis(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    @Override
    public void execute(DelegateExecution delex) throws Exception {
        List<String> diagnosis = new ArrayList<>();
        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);
        if ((boolean) delex.getVariableTyped("isAnemic").getValue()) {
            diagnosis.add("Anemia");
        }
        if ((boolean) delex.getVariableTyped("isDiabetic").getValue()) {
            diagnosis.add("Diabetes");
        }
        if ((boolean) delex.getVariableTyped("isThrombocytopenic").getValue()) {
            diagnosis.add("Thrombocytopenia");
        }
        if ((boolean) delex.getVariableTyped("isThrombocytosis").getValue()) {
            diagnosis.add("Thrombocytosis");
        }
        if (diagnosis.isEmpty()) {
            diagnosis.add("Healthy");
        }

        patient.setDiagnosis(String.join(", ", diagnosis));
        patientRepository.save(patient);
    }
}
