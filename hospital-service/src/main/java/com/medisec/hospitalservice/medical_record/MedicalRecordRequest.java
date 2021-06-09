package com.medisec.hospitalservice.medical_record;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class MedicalRecordRequest {
    @Positive
    private int age;

    @Positive
    private Long patientId;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:SS")
    private Date time;

    @Positive
    private int heartBeat;

    @Positive
    private int averageHeardBeat;

    @NotBlank
    private String bloodPressure;

    @NotBlank
    private String averageBloodPressure;

    @Positive
    private float bodyTemperature;

    @NotBlank
    private String surgery;

    private boolean vaccinated;
}
