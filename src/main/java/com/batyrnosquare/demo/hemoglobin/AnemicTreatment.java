package com.batyrnosquare.demo.hemoglobin;

import com.batyrnosquare.demo.constants.Gender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("anemicTreatmentDelegate")
public class AnemicTreatment implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delex) throws Exception {
        int hemoglobin = (int) delex.getVariable("hemoglobin");
        Gender gender = Gender.valueOf((String) delex.getVariable("gender"));

        delex.setVariable("treatment", "Iron therapy = -" + (gender.getHemoLimit() - hemoglobin) + " g/L");

        delex.setVariable("hemoglobinAfterTreatment", gender.getHemoLimit());
    }
}
