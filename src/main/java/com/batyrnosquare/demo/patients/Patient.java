package com.batyrnosquare.demo.patients;

import com.batyrnosquare.demo.constants.Gender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("patientDelegate")
public class Patient implements JavaDelegate {

    private final PatientRepository patientRepository;

    public Patient(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        PatientModel patient = new PatientModel();
        patient.setName((String) delex.getVariable("name"));
        patient.setSurname((String) delex.getVariable("surname"));
        patient.setEmail((String) delex.getVariable("email"));
        patient.setGender(Gender.valueOf((String) delex.getVariable("gender")));

        patientRepository.save(patient);

        delex.setVariable("patient", patient);
    }
}
