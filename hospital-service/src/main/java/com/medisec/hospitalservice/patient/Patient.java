package com.medisec.hospitalservice.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "patient")
@Getter
@AllArgsConstructor
public class Patient {
    @Id
    @Field("id")
    Long id;

    @Field("firstName")
    private String firstName;
    @Field("lastName")
    private String lastName;

    @Field("securityNumber")
    private String securityNumber;

    @Field("patientDetails")
    private PatientDetails patientDetails;
}
