package com.batyrnosquare.demo.glucose;

import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.batyrnosquare.demo.glucose.Glucose.parseDouble;

@Component("diabeticTreatmentDelegate")
public class DiabeticTreatment implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;

    public DiabeticTreatment(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        PatientModel patient = (PatientModel) delex.getVariable("patient");
        double glucose = parseDouble(delex.getVariable("glucose"), delex.getVariable("glucose").toString());

        delex.setVariable("treatment", "Insulin therapy = -" + (glucose - AppConstants.UNISEX_GLUCOSE_LIMIT) + " mol/L");

        double glucoseAfterTreatment = glucose - (glucose - AppConstants.UNISEX_GLUCOSE_LIMIT);

        diagnosisRepository.findByName("Diabetes").ifPresent(diagnosis -> {
            diagnosis.setName("Recovered Diabetes");
            diagnosisRepository.save(diagnosis);
        });

        delex.setVariable("glucoseAfterTreatment", glucoseAfterTreatment);
        if (glucoseAfterTreatment < AppConstants.UNISEX_GLUCOSE_LIMIT) {
            delex.setVariable("isDiabetic", false);
        }


    }
}
