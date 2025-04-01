package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("thrombocytosisTreatmentDelegate")
public class ThrombocytosisTreatment implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;

    public ThrombocytosisTreatment(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PatientModel patient = (PatientModel) delegateExecution.getVariable("patient");

        int platelet = (int) delegateExecution.getVariable("platelet");

        delegateExecution.setVariable("treatment", "Hydroxyurea = -" + (platelet - AppConstants.PLATELET_UPPER_LIMIT) + " x10^9/L");

        int plateletAfterTreatment = AppConstants.PLATELET_UPPER_LIMIT;

        diagnosisRepository.findByName("Thrombocytosis").ifPresent(diagnosis -> {
            diagnosis.setName("Recovered Thrombocytosis");
            diagnosisRepository.save(diagnosis);
        });

        delegateExecution.setVariable("plateletAfterTreatment", plateletAfterTreatment);
        if (plateletAfterTreatment <= AppConstants.PLATELET_UPPER_LIMIT) {
            delegateExecution.setVariable("isThrombocytosis", false);
        }
    }
}
