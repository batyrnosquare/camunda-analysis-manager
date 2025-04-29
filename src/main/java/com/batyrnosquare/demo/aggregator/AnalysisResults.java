package com.batyrnosquare.demo.aggregator;

import com.batyrnosquare.demo.patients.PatientModel;
import com.batyrnosquare.demo.patients.PatientRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.batyrnosquare.demo.glucose.Glucose.parseDouble;


@Component("analysisResultsDelegate")
public class AnalysisResults implements JavaDelegate {
    private final AnalysisRepository analysisRepository;
    private final PatientRepository patientRepository;
    private final JavaMailSenderImpl mailSender;


    @Autowired
    public AnalysisResults(AnalysisRepository analysisRepository, PatientRepository patientRepository, JavaMailSenderImpl mailSender) {
        this.analysisRepository = analysisRepository;
        this.patientRepository = patientRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        Long patientId= (Long) delex.getVariable("patientId");
        PatientModel patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        AnalysisModel analysisModel = new AnalysisModel();
        double glucose = parseDouble(delex.getVariable("glucose"), delex.getVariable("glucose").toString());
        analysisModel.setGlucose(glucose);
        analysisModel.setHemoglobin((Integer) delex.getVariable("hemoglobin"));
        analysisModel.setPlatelet((Integer) delex.getVariable("platelet"));
        analysisModel.setPatient(patient);
        analysisRepository.save(analysisModel);
        sendEmail(patient.getEmail());
    }

    private void sendEmail(String userEmail){
        SimpleMailMessage mailMessage =new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Analysis Results");
        PatientModel patient = patientRepository.findByEmail(userEmail)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        AnalysisModel analysisModel = analysisRepository.findTopByPatient_IdOrderByIdDesc(patient.getId());
        mailMessage.setText("Dear " + patient.getName() + patient.getSurname()+ ","+
                "\nYour analyse #" + analysisModel.getId() + " results are ready."+
                "\nGlucose: " + analysisModel.getGlucose() +
                "\nHemoglobin: " +analysisModel.getHemoglobin()+
                "\nPlatelet: " + analysisModel.getPlatelet());
        mailSender.send(mailMessage);
    }


}
