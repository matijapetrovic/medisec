package com.medisec.hospitalservice.medical_record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;

@Document(collection = "medical_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @Id
    @Field("id")
    @JsonIgnore
    private Long id;

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

    public MedicalRecord(
            int age,
            Long patientId,
            Date time,
            int heartBeat,
            int averageHeardBeat,
            String bloodPressure,
            String averageBloodPressure,
            float bodyTemperature,
            boolean vaccinated,
            String surgery) {
        this.age = age;
        this.patientId = patientId;
        this.time = time;
        this.heartBeat = heartBeat;
        this.averageHeartBeat = averageHeardBeat;
        this.bloodPressure = bloodPressure;
        this.averageBloodPressure = averageBloodPressure;
        this.bodyTemperature = bodyTemperature;
        this.vaccinated = vaccinated;
        this.surgery = surgery;
    }

    public int getSystolicBloodPressure() {
        String systolic = bloodPressure.split("/")[0];
        return Integer.parseInt(systolic);
    }

    public int getDiastolicBloodPressure() {
        String diastolic = bloodPressure.split("/")[1];
        return Integer.parseInt(diastolic);
    }

}
