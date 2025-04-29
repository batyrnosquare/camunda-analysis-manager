package com.batyrnosquare.demo.patients;

import com.batyrnosquare.demo.constants.Gender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("patientDelegate")
public class Patient implements JavaDelegate {

    private final PatientRepository patientRepository;

    public Patient(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        PatientModel patient;
        String email = (String) delex.getVariable("email");
        if(patientRepository.existsByEmail(email)){
            patient = patientRepository.findByEmail(email)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        }else {
            patient = new PatientModel();
            patient.setName((String) delex.getVariable("name"));
            patient.setSurname((String) delex.getVariable("surname"));
            patient.setEmail(email);
            patient.setGender(Gender.valueOf((String) delex.getVariable("gender")));

            patientRepository.save(patient);
        }
        delex.setVariable("patientId", patient.getId());
    }
}
