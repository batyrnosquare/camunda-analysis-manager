package com.batyrnosquare.demo.patients;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("healthinessDelegate")
public class Healthiness implements JavaDelegate {

    private final PatientRepository patientRepository;

    public Healthiness(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    @Override
    public void execute(DelegateExecution delex) throws Exception {
        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);

        boolean isAnemic = (boolean) delex.getVariableTyped("isAnemic").getValue();
        boolean isDiabetic = (boolean) delex.getVariableTyped("isDiabetic").getValue();
        boolean isThrombocytopenic = (boolean) delex.getVariableTyped("isThrombocytopenic").getValue();
        boolean isThrombocytosis = (boolean) delex.getVariableTyped("isThrombocytosis").getValue();

        if (!isAnemic && !isDiabetic && !isThrombocytopenic && !isThrombocytosis) {
            patient.setDiagnosis("Healthy");
            patientRepository.save(patient);
        }
    }
}
