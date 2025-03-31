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

    private final DiagnosisRepository diagnosisRepository;

    public Diagnosis(PatientRepository patientRepository, DiagnosisRepository diagnosisRepository) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);
        if (patient.getDiagnosis() == null) {
            patient.setDiagnosis(new ArrayList<>());
        }

        List<DiagnosisModel> diagnosis = new ArrayList<>();
        boolean isAnemic = (boolean) delex.getVariable("isAnemic");
        boolean isDiabetic = (boolean) delex.getVariable("isDiabetic");
        boolean isThrombocytopenic = (boolean) delex.getVariable("isThrombocytopenic");
        boolean isThrombocytosis = (boolean) delex.getVariable("isThrombocytosis");

        if (isAnemic) {
            diagnosis.add(new DiagnosisModel("Anemia", patient));
        }
        if (isDiabetic) {
            diagnosis.add(new DiagnosisModel("Diabetes", patient));
        }
        if (isThrombocytopenic) {
            diagnosis.add(new DiagnosisModel("Thrombocytopenia", patient));
        }
        if (isThrombocytosis) {
            diagnosis.add(new DiagnosisModel("Thrombocytosis", patient));
        }
        if (!diagnosis.isEmpty()) {
            patient.getDiagnosis().clear();
            patient.getDiagnosis().addAll(diagnosis);
        }else{
            patient.setDiagnosis(List.of(new DiagnosisModel("Healthy", patient)));
        }
        patientRepository.save(patient);
    }
}

