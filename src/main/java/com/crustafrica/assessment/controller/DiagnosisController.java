package com.crustafrica.assessment.controller;

import com.crustafrica.assessment.dto.input.SymptomsInputDTO;
import com.crustafrica.assessment.dto.output.BasicResponseDTO;
import com.crustafrica.assessment.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class DiagnosisController extends Controller {

    private final DiagnosisService diagnosisService;

    @GetMapping("/symptoms")
    public BasicResponseDTO fetchSymptoms() {
        return responseWithUpdatedHttpStatus(diagnosisService.fetchSymptoms());
    }

    @PostMapping("/diagnosis")
    public BasicResponseDTO getDiagnosis(@RequestBody SymptomsInputDTO symptomsInputDTO) {
        return responseWithUpdatedHttpStatus(diagnosisService.getDiagnosis(symptomsInputDTO));
    }

    @PatchMapping("/diagnosis/{diagnosis_id}/validate")
    public BasicResponseDTO validateDiagnosis(@PathVariable(name = "diagnosis_id") String id) {
        return responseWithUpdatedHttpStatus(diagnosisService.validateDiagnosis(id));
    }

    @GetMapping("/diagnosis/valid")
    public BasicResponseDTO fetchDiagnosisByValidState(@RequestParam(name = "valid") String valid) {
        return responseWithUpdatedHttpStatus(diagnosisService.fetchDiagnosisByValidState(valid));
    }

    @GetMapping("/diagnosis/all")
    public BasicResponseDTO fetchAllDiagnosis() {
        return responseWithUpdatedHttpStatus(diagnosisService.fetchAllDiagnosisResult());
    }


}
