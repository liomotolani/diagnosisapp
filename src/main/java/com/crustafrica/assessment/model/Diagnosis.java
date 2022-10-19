package com.crustafrica.assessment.model;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;


@Data
@Entity
public class Diagnosis {

    private String id;
    private Issue issue;
    private Specialization specialization;
    private boolean isValid;
    private Date createdTime;
}
