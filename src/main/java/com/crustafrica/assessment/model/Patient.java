package com.crustafrica.assessment.model;

import jdk.jshell.Diag;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private int yearOfBirth;
    private String email;
//    private List<Diagnosis> diagnosisList;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

}
