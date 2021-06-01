package com.medisec.hospitalservice.exception;

public class PatientDoesNotExist extends Exception {
    public PatientDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}
