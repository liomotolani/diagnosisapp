package com.crustafrica.assessment.dto.input;

import lombok.Data;

import java.util.List;

@Data
public class SymptomsInputDTO {

    private List<Integer> symptoms;
    private String gender;
    private int yearOfBirth;
}
