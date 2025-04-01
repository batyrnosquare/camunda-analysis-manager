package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("thrombocytopenicTreatmentDelegate")
public class ThrombocytopenicTreatment implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;

    public ThrombocytopenicTreatment(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PatientModel patient = (PatientModel) delegateExecution.getVariable("patient");
        int platelet = (int) delegateExecution.getVariable("platelet");

        delegateExecution.setVariable("treatment", "Platelet transfusion = +" + (AppConstants.PLATELET_LOWER_LIMIT - platelet) + " x10^9/L");

        int plateletAfterTreatment = AppConstants.PLATELET_LOWER_LIMIT;

        diagnosisRepository.findByName("Thrombocytopenia").ifPresent(diagnosis -> {
            diagnosis.setName("Recovered Thrombocytopenia");
            diagnosisRepository.save(diagnosis);
        });
        delegateExecution.setVariable("plateletAfterTreatment", plateletAfterTreatment);

        if (plateletAfterTreatment >= AppConstants.PLATELET_LOWER_LIMIT) {
            delegateExecution.setVariable("isThrombocytopenic", false);
        }
    }
}
