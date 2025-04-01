package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.diagnosis.DiagnosisModel;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("plateletDelegate")
public class Platelet implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;

    public Platelet(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        DiagnosisModel diagnosis = new DiagnosisModel();
        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);
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
        diagnosisRepository.save(diagnosis);
    }
}
