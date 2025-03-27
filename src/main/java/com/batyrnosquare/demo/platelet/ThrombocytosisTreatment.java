package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("thrombocytosisTreatmentDelegate")
public class ThrombocytosisTreatment implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int platelet = (int) delegateExecution.getVariable("platelet");

        delegateExecution.setVariable("treatment", "Hydroxyurea = -" + (platelet - AppConstants.PLATELET_UPPER_LIMIT) + " x10^9/L");

        int plateletAfterTreatment = AppConstants.PLATELET_UPPER_LIMIT;

        delegateExecution.setVariable("plateletAfterTreatment", plateletAfterTreatment);
        if (plateletAfterTreatment <= AppConstants.PLATELET_UPPER_LIMIT) {
            delegateExecution.setVariable("isThrombocytosis", false);
        }
    }
}
