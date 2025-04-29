package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.diagnosis.DiagnosisModel;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import com.batyrnosquare.demo.patients.PatientRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("plateletDelegate")
public class Platelet implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;
    private final PatientRepository patientRepository;

    public Platelet(DiagnosisRepository diagnosisRepository, PatientRepository patientRepository) {
        this.diagnosisRepository = diagnosisRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        DiagnosisModel diagnosis = new DiagnosisModel();
        Long patientId = (Long) delex.getVariable("patientId");

        PatientModel patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        diagnosis.setPatient(patient);

        int platelet = (int) delex.getVariable("platelet");

        boolean isThrombocytopenic = platelet < AppConstants.PLATELET_LOWER_LIMIT;
        boolean isThrombocytosis = platelet > AppConstants.PLATELET_UPPER_LIMIT;

        if (isThrombocytopenic) {
            delex.setVariable("analysisType", "Thrombocytopenia Analyse");
            delex.setVariable("analysisStatus", "Urgently to Hematology Department!");
            diagnosis.setName("Thrombocytopenia");
        } else if (isThrombocytosis) {
            delex.setVariable("analysisType", "Thrombocytosis Analyse");
            delex.setVariable("analysisStatus", "Urgently to Hematology Department!");
            diagnosis.setName("Thrombocytosis");
        }else{
            delex.setVariable("analysisType", AppConstants.DEFAULT_ANALYSIS_TYPE);
            delex.setVariable("analysisStatus", AppConstants.DEFAULT_ANALYSIS_STATUS);
        }

        delex.setVariable("isThrombocytopenic", isThrombocytopenic);
        delex.setVariable("isThrombocytosis", isThrombocytosis);
        if (diagnosis.getName() != null) {
            diagnosisRepository.save(diagnosis);
        }
    }
}
