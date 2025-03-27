package com.batyrnosquare.demo.aggregator;

import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    private final AnalysisRepository analysisRepository;

    public AnalysisService(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    public void saveAnalysis(AnalysisModel analysisModel) {
        analysisRepository.save(analysisModel);
    }
}
