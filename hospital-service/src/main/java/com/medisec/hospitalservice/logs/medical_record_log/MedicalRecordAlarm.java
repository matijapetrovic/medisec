package com.medisec.hospitalservice.logs.medical_record_log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.UUID;

@Document(collection = "medical_record_alarm")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordAlarm {
    @Id
    @Field("id")
    String id;

    @Field("event_id")
    Long eventId;

    @Field("time")
    Date time;

    @Field("severity")
    Severity severity;

    @Field("event_source")
    String eventSource;

    @Field("event_code")
    Long eventCode;

    @Field("message")
    String message;

    @Field("alarm_code")
    Long alarmCode;

    @Transient
    boolean persisted;
}


