package com.batyrnosquare.demo.glucose;

import com.batyrnosquare.demo.constants.AppConstants;
import com.batyrnosquare.demo.diagnosis.DiagnosisModel;
import com.batyrnosquare.demo.diagnosis.DiagnosisRepository;
import com.batyrnosquare.demo.patients.PatientModel;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("glucoseDelegate")
public class Glucose implements JavaDelegate {

    private final DiagnosisRepository diagnosisRepository;

    public Glucose(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }


    @Override
    public void execute(DelegateExecution delex) throws Exception {
        DiagnosisModel diagnosis = new DiagnosisModel();

        PatientModel patient = Spin.JSON(delex.getVariableTyped("patient").getValue()).mapTo(PatientModel.class);

        diagnosis.setPatient(patient);

        double glucose = parseDouble(delex.getVariable("glucose"), delex.getVariable("glucose").toString());

        boolean isDiabetic = glucose > AppConstants.UNISEX_GLUCOSE_LIMIT;


        if (isDiabetic) {
            delex.setVariable("analysisType", "Diabetes Analyse");
            delex.setVariable("analysisStatus", "Urgently to Endocrinology Department!");
            diagnosis.setName("Diabetes");
        }else{
            delex.setVariable("analysisType", AppConstants.DEFAULT_ANALYSIS_TYPE);
            delex.setVariable("analysisStatus", AppConstants.DEFAULT_ANALYSIS_STATUS);
        }
        delex.setVariable("isDiabetic", isDiabetic);

        diagnosisRepository.save(diagnosis);

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
