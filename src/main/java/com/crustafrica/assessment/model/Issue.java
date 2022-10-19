package com.crustafrica.assessment.model;

import lombok.Data;

@Data
public class Issue {

    private int id;
    private String name;
    private String profName;
    private String icd;
    private String icdName;
    private String accuracy;
}
