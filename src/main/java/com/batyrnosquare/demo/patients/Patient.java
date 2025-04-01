package com.batyrnosquare.demo.patients;

import com.batyrnosquare.demo.constants.Gender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.plugin.variable.SpinValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("patientDelegate")
public class Patient implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(Patient.class);
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



        log.info("New patient created: " + patient.getEmail());


        PatientDTO patientDTO = new PatientDTO(patient);

        delex.setVariable("patient",
                SpinValues.jsonValue(Spin.JSON(patientDTO).toString()).create());
    }

}
