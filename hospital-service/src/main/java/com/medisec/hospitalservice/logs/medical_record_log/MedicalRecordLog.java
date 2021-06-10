package com.medisec.hospitalservice.logs.medical_record_log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.UUID;

import javax.persistence.Id;
import java.util.Date;

@Document(collection = "medical_record_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordLog {
    @Id
    @Field("id")
    @JsonIgnore
    private String id;

    @Field("age")
    private int age;

    @Field("patient_id")
    private Long patientId;

    @Field("time")
    private Date time;

    @Field("heart_beat")
    private int heartBeat;

    @Field("average_heart_beat")
    private int averageHeartBeat;

    @Field("blood_pressure")
    private String bloodPressure;

    @Field("average_blood_pressure")
    private String averageBloodPressure;

    @Field("body_temperature")
    private float bodyTemperature;

    @Field("vaccinated")
    private boolean vaccinated;

    @Field("surgery")
    private String surgery;

    public int getSystolicBloodPressure() {
        String systolic = bloodPressure.split("/")[0];
        return Integer.parseInt(systolic);
    }

    public int getDiastolicBloodPressure() {
        String diastolic = bloodPressure.split("/")[1];
        return Integer.parseInt(diastolic);
    }

}
