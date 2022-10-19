package com.crustafrica.assessment.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Patient> patientList;
    private String priadToken;
}
