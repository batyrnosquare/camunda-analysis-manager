package com.batyrnosquare.demo.glucose;

import com.batyrnosquare.demo.constants.AppConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("glucoseDelegate")
public class Glucose implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delex) throws Exception {

        double glucose = parseDouble(delex.getVariable("glucose"), delex.getVariable("glucose").toString());

        if (glucose > AppConstants.UNISEX_GLUCOSE_LIMIT) {
            delex.setVariable("analysisType", "Diabetes Analyse");
            delex.setVariable("analysisStatus", "Urgently to Endocrinology Department!");
            delex.setVariable("isDiabetic", true);
        }else{
            delex.setVariable("analysisType", AppConstants.DEFAULT_ANALYSIS_TYPE);
            delex.setVariable("analysisStatus", AppConstants.DEFAULT_ANALYSIS_STATUS);
            delex.setVariable("isDiabetic", false);
        }
    }

    public static double parseDouble(Object value, String glucoseStr) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot parse null value to double: " + glucoseStr);
        }try {
            return Double.parseDouble(glucoseStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse value to double: " + glucoseStr, e);
        }
    }
}
