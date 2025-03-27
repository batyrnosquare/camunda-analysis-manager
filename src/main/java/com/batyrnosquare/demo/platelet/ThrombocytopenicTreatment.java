package com.batyrnosquare.demo.platelet;

import com.batyrnosquare.demo.constants.AppConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("thrombocytopenicTreatmentDelegate")
public class ThrombocytopenicTreatment implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int platelet = (int) delegateExecution.getVariable("platelet");

        delegateExecution.setVariable("treatment", "Platelet transfusion = +" + (AppConstants.PLATELET_LOWER_LIMIT - platelet) + " x10^9/L");

        int plateletAfterTreatment = AppConstants.PLATELET_LOWER_LIMIT;

        delegateExecution.setVariable("plateletAfterTreatment", plateletAfterTreatment);

        if (plateletAfterTreatment >= AppConstants.PLATELET_LOWER_LIMIT) {
            delegateExecution.setVariable("isThrombocytopenic", false);
        }
    }
}
