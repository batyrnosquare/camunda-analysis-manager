package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("plateletDelegate")
public class Platelet implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int platelet = (int) delegateExecution.getVariable("platelet");

        boolean isThrombocytopenic = platelet < AppConstants.PLATELET_LOWER_LIMIT;
        boolean isThrombocytosis = platelet > AppConstants.PLATELET_UPPER_LIMIT;

        if (isThrombocytopenic) {
            delegateExecution.setVariable("analysisType", "Thrombocytopenia Analyse");
            delegateExecution.setVariable("analysisStatus", "Urgently to Hematology Department!");
        } else if (isThrombocytosis) {
            delegateExecution.setVariable("analysisType", "Thrombocytosis Analyse");
            delegateExecution.setVariable("analysisStatus", "Urgently to Hematology Department!");
        } else {
            delegateExecution.setVariable("analysisType", AppConstants.DEFAULT_ANALYSIS_TYPE);
            delegateExecution.setVariable("analysisStatus", AppConstants.DEFAULT_ANALYSIS_STATUS);
        }

        delegateExecution.setVariable("isThrombocytopenic", isThrombocytopenic);
        delegateExecution.setVariable("isThrombocytosis", isThrombocytosis);
    }
}
