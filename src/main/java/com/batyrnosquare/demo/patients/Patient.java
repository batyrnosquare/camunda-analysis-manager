package com.batyrnosquare.demo.patients;

import com.batyrnosquare.demo.constants.AppConstants;
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
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PatientModel patient = new PatientModel();
        patient.setName((String) delegateExecution.getVariable("name"));
        patient.setSurname((String) delegateExecution.getVariable("surname"));
        patient.setEmail((String) delegateExecution.getVariable("email"));
        Gender gender = Gender.valueOf((String) delegateExecution.getVariable("gender"));
        patient.setGender(gender);
        patient.setDiagnosis(AppConstants.NO_DIAGNOSIS);
        if (patientRepository.findByEmail(patient.getEmail()) == null) {
            patientRepository.save(patient);
        }else {
            patient = patientRepository.findByEmail(patient.getEmail());
        }
        log.info("Patient: " + patient.getName() + " " + patient.getSurname() + " is saved!");

        delegateExecution.setVariable("patient", SpinValues.jsonValue(Spin.JSON(patient).toString()).create());
    }
}
