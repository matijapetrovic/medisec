package com.medisec.hospitalservice.alarms.service_log_alarm;

import com.medisec.hospitalservice.logs.medical_record_log.Severity;
import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import com.medisec.hospitalservice.logs.service_log.ServiceLogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.UUID;

@Document(collection = "service_log_alarm")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceLogAlarm {
    @Id
    @Field("id")
    String id;

    @Field("time")
    Date time;

    @Field("severity")
    Severity severity;

    @Field("message")
    String message;

    @Field("type")
    LogType type;

    @Transient
    boolean persisted;

    public ServiceLogAlarm(
        Date time,
        Severity severity,
        String message
    ) {
        this.id = UUID.randomUUID().toString();
        this.time = time;
        this.severity = severity;
        this.message = message;
        this.persisted = false;
    }
}


