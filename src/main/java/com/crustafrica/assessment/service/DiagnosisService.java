package com.crustafrica.assessment.service;

import com.crustafrica.assessment.dto.Status;
import com.crustafrica.assessment.dto.input.SymptomsInputDTO;
import com.crustafrica.assessment.dto.output.BasicResponseDTO;
import com.crustafrica.assessment.model.Diagnosis;
import com.crustafrica.assessment.repository.DiagnosisResultRepository;
import com.crustafrica.assessment.util.ApimedicUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final ApimedicUtil apimedicUtil;

    private final DiagnosisResultRepository diagnosisResultRepository;


    public BasicResponseDTO fetchSymptoms(){
        log.info("symptoms {}", apimedicUtil.fetchSymptoms());
        return new BasicResponseDTO(Status.SUCCESS,apimedicUtil.fetchSymptoms());
    }

    public BasicResponseDTO getDiagnosis(SymptomsInputDTO symptomsInputDTO) {
        return new BasicResponseDTO(Status.SUCCESS, apimedicUtil.getDiagnosisResult(symptomsInputDTO.getSymptoms()
        ,symptomsInputDTO.getGender(), symptomsInputDTO.getYearOfBirth()));
    }

    public BasicResponseDTO validateDiagnosis(String id){
        Optional<Diagnosis> diagnosis = diagnosisResultRepository.findById(id);
        if(!diagnosis.isPresent()) {
            return new BasicResponseDTO(Status.NOT_FOUND,"Diagnosis not found");
        }
        diagnosis.get().setValid(true);
        diagnosisResultRepository.save(diagnosis.get());
        return new BasicResponseDTO(Status.SUCCESS,diagnosis.get());
    }

    public BasicResponseDTO fetchDiagnosisByValidState(String value) {
        List<Diagnosis> diagnosis = new ArrayList<>();
        if(value.equals("true")){
            diagnosis = diagnosisResultRepository.findAllByValid(true);
        }else{
            diagnosis = diagnosisResultRepository.findAllByValid(false);
        }
        return new BasicResponseDTO(Status.SUCCESS, diagnosis);
    }

    public BasicResponseDTO fetchAllDiagnosisResult() {
        List<Diagnosis> diagnosis = new ArrayList<>();
        diagnosis = diagnosisResultRepository.findAll();
        return new BasicResponseDTO(Status.SUCCESS, diagnosis);
    }

}
