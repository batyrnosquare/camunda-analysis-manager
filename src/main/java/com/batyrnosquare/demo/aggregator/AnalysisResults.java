package com.batyrnosquare.demo.aggregator;

import com.batyrnosquare.demo.constants.Gender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.batyrnosquare.demo.glucose.Glucose.parseDouble;

@Component("analysisResultsDelegate")
public class AnalysisResults implements JavaDelegate {
    private final AnalysisRepository analysisRepository;

    @Autowired
    public AnalysisResults(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        AnalysisModel analysisModel = new AnalysisModel();
        double glucose = parseDouble(delex.getVariable("glucose"), delex.getVariable("glucose").toString());
        analysisModel.setGlucose(glucose);
        analysisModel.setHemoglobin((Integer) delex.getVariable("hemoglobin"));
        analysisModel.setPlatelet((Integer) delex.getVariable("platelet"));
        Gender gender = Gender.valueOf((String) delex.getVariable("gender"));
        analysisModel.setGender(gender);
        analysisRepository.save(analysisModel);
    }
}
