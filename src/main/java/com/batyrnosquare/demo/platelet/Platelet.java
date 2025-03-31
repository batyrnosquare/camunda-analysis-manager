package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("plateletDelegate")
public class Platelet implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delex) throws Exception {
        int platelet = (int) delex.getVariable("platelet");

        boolean isThrombocytopenic = platelet < AppConstants.PLATELET_LOWER_LIMIT;
        boolean isThrombocytosis = platelet > AppConstants.PLATELET_UPPER_LIMIT;

        if (isThrombocytopenic) {
            delex.setVariable("analysisType", "Thrombocytopenia Analyse");
            delex.setVariable("analysisStatus", "Urgently to Hematology Department!");
        } else if (isThrombocytosis) {
            delex.setVariable("analysisType", "Thrombocytosis Analyse");
            delex.setVariable("analysisStatus", "Urgently to Hematology Department!");
        }else{
            delex.setVariable("analysisType", AppConstants.DEFAULT_ANALYSIS_TYPE);
            delex.setVariable("analysisStatus", AppConstants.DEFAULT_ANALYSIS_STATUS);
        }

        delex.setVariable("isThrombocytopenic", isThrombocytopenic);
        delex.setVariable("isThrombocytosis", isThrombocytosis);
    }
}
