package com.medisec.hospitalservice.medical_record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicalRecordAlarm {
    Long eventId;
    Date time;
    Severity severity;
    String eventSource;
    Long eventCode;
    String message;
    Long alarmId;
    Long alarmCode;

}


