package com.batyrnosquare.demo.hemoglobin;

import com.batyrnosquare.demo.constants.Gender;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("anemicTreatmentDelegate")
public class AnemicTreatment implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;

    public AnemicTreatment(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        PatientModel patientModel = (PatientModel) delex.getVariable("patient");
        int hemoglobin = (int) delex.getVariable("hemoglobin");
        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);
        Gender gender = patient.getGender();

        delex.setVariable("treatment", "Iron therapy = -" + (gender.getHemoLimit() - hemoglobin) + " g/L");

        int hemoglobinAfterTreatment = gender.getHemoLimit();

        diagnosisRepository.findByName("Anemia").ifPresent(diagnosis -> {
            diagnosis.setName("Recovered Anemia");
            diagnosisRepository.save(diagnosis);
        });

        delex.setVariable("hemoglobinAfterTreatment", gender.getHemoLimit());

        if(hemoglobinAfterTreatment > hemoglobin) {
            delex.setVariable("isAnemic", false);
        }
    }
}
