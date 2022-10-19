package com.crustafrica.assessment.repository;

import com.crustafrica.assessment.model.Diagnosis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisResultRepository extends JpaRepository<Diagnosis,String> {

    List<Diagnosis> findAllByValid(boolean isValid);
}
