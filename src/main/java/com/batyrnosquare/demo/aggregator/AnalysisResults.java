package com.batyrnosquare.demo.aggregator;

import com.batyrnosquare.demo.patients.PatientModel;
import com.batyrnosquare.demo.patients.PatientRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.batyrnosquare.demo.glucose.Glucose.parseDouble;

@Component("analysisResultsDelegate")
public class AnalysisResults implements JavaDelegate {
    private final AnalysisRepository analysisRepository;

    private final PatientRepository patientRepository;

    @Autowired
    public AnalysisResults(AnalysisRepository analysisRepository, PatientRepository patientRepository) {
        this.analysisRepository = analysisRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);
        AnalysisModel analysisModel = new AnalysisModel();
        double glucose = parseDouble(delex.getVariable("glucose"), delex.getVariable("glucose").toString());
        analysisModel.setGlucose(glucose);
        analysisModel.setHemoglobin((Integer) delex.getVariable("hemoglobin"));
        analysisModel.setPlatelet((Integer) delex.getVariable("platelet"));
        analysisModel.setPatient(patient);
        analysisRepository.save(analysisModel);
    }


}
