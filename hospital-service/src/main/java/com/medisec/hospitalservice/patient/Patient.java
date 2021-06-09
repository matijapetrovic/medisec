package com.medisec.hospitalservice.patient;

import lombok.Getter;

@Getter
public class Patient {

    Long id;

    private String firstName;
    private String lastName;

    private MedicalRecord medicalRecord;
}
