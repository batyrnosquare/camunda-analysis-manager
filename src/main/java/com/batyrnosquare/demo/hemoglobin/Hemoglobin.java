package com.batyrnosquare.demo.hemoglobin;

import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.constants.Gender;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("hemoglobinDelegate")
public class Hemoglobin implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(Hemoglobin.class);

    @Override
    public void execute(DelegateExecution delex) throws Exception {

        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);

        log.info("Patient Data from Camunda: " + Spin.JSON(delex.getVariableTyped("patient").getValue()).toString());

        Gender gender = patient.getGender();
        if (gender == null) {
            throw new IllegalStateException("Gender is null for patient: " + patient.getName());
        }

        int hemoglobin = (int) delex.getVariable("hemoglobin");

        boolean isAnemic = gender.isAnemic(hemoglobin);

        if (isAnemic) {
            delex.setVariable("analysisType", "Anemia Analyse");
            delex.setVariable("analysisStatus", "Urgently to Hematology Department!");
        }else{
            delex.setVariable("analysisType", AppConstants.DEFAULT_ANALYSIS_TYPE);
            delex.setVariable("analysisStatus", AppConstants.DEFAULT_ANALYSIS_STATUS);
        }

        delex.setVariable("isAnemic", isAnemic);
        log.info(String.valueOf(isAnemic));
    }
}
