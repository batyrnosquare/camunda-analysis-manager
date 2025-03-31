package com.batyrnosquare.demo.patients;

import com.batyrnosquare.demo.aggregator.AnalysisModel;
import com.batyrnosquare.demo.aggregator.AnalysisRepository;
import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.constants.Gender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.plugin.variable.SpinValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Component("patientDelegate")
public class Patient implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(Patient.class);
    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final AnalysisRepository analysisRepository;

    public Patient(PatientRepository patientRepository, DiagnosisRepository diagnosisRepository, AnalysisRepository analysisRepository) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.analysisRepository = analysisRepository;

    }
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String email = (String) delegateExecution.getVariable("email");

        // Check if a patient with this email already exists
        Optional<PatientModel> existingPatient = patientRepository.findByEmail(email);

        PatientModel patient;
        if (existingPatient.isPresent()) {
            patient = existingPatient.get();
            log.info("Patient already exists: " + patient.getEmail());
        } else {
            patient = new PatientModel();
            patient.setName((String) delegateExecution.getVariable("name"));
            patient.setSurname((String) delegateExecution.getVariable("surname"));
            patient.setEmail(email);
            patient.setGender(Gender.valueOf((String) delegateExecution.getVariable("gender")));

            patient = patientRepository.save(patient);

            DiagnosisModel initialDiagnosis = new DiagnosisModel(AppConstants.NO_DIAGNOSIS, patient);
            diagnosisRepository.save(initialDiagnosis);

            patient.getDiagnosis().add(initialDiagnosis);

            AnalysisModel initialAnalysis = new AnalysisModel(patient, 0, 0, 0);
            analysisRepository.save(initialAnalysis);

            patient.getAnalysis().add(initialAnalysis);

            log.info("New patient created: " + patient.getEmail());
        }

        PatientDTO patientDTO = new PatientDTO(patient);

        delegateExecution.setVariable("patient",
                SpinValues.jsonValue(Spin.JSON(patientDTO).toString()).create());
    }

}
