package com.batyrnosquare.demo.hemoglobin;

import com.batyrnosquare.demo.constants.Gender;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import com.batyrnosquare.demo.patients.PatientRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("anemicTreatmentDelegate")
public class AnemicTreatment implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;
    private final PatientRepository patientRepository;

    public AnemicTreatment(DiagnosisRepository diagnosisRepository, PatientRepository patientRepository) {
        this.diagnosisRepository = diagnosisRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        int hemoglobin = (int) delex.getVariable("hemoglobin");
        Long patientId = (Long) delex.getVariable("patientId");

        PatientModel patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
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
