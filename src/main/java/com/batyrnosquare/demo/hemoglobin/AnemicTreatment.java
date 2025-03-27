package com.batyrnosquare.demo.hemoglobin;

import com.batyrnosquare.demo.constants.Gender;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("anemicTreatmentDelegate")
public class AnemicTreatment implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delex) throws Exception {
        int hemoglobin = (int) delex.getVariable("hemoglobin");
        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);
        Gender gender = patient.getGender();

        delex.setVariable("treatment", "Iron therapy = -" + (gender.getHemoLimit() - hemoglobin) + " g/L");

        int hemoglobinAfterTreatment = gender.getHemoLimit();

        delex.setVariable("hemoglobinAfterTreatment", gender.getHemoLimit());

        if(hemoglobinAfterTreatment > hemoglobin) {
            delex.setVariable("isAnemic", false);
        }
    }
}
