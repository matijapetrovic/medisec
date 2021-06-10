package com.medisec.hospitalservice.logs.medical_record_log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@NoArgsConstructor
public class MedicalRecordLogRequest {
    @Positive
    private int age;

    @Positive
    private Long patientId;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:SS")
    private Date time;

    @Positive
    private int heartBeat;

    @Positive
    private int averageHeartBeat;

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
