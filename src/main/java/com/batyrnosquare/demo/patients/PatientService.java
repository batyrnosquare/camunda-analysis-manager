package com.batyrnosquare.demo.patients;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("patientServiceDelegate")
public class PatientService implements JavaDelegate {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int patientId = (int) delegateExecution.getVariable("patientId");



    }
}
