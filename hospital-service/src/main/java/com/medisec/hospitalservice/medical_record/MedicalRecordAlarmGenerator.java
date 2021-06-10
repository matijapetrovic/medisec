package com.medisec.hospitalservice.medical_record;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.medisec.hospitalservice.medical_record.MedicalRecord;
import com.medisec.hospitalservice.medical_record.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MedicalRecordAlarmGenerator {
    private final MedicalRecordService service;
    private static String path = "C:/FAKS/8 semestar/Bezbednost/medisec/hospital-service/src/main/resources/alarms/alarm.json";

    public void run() {
        List<MedicalRecord> medicalRecords = service.findAll();

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");

        for(MedicalRecord record: medicalRecords) {
            kSession.insert(record);
            kSession.fireAllRules();
            kSession.delete(kSession.getFactHandle(record));
        }
    }

    public static void generateAlarm(
            Long recordId,
            Severity severity,
            String message) throws IOException {
        MedicalRecordAlarm alarm =
            new MedicalRecordAlarm(
                1L,
                new Date(),
                severity,
                "Event source",
                1001L,
                message,
                1L,
                1000L
        );
        writeAlarm(alarm);
    }

    public static void writeAlarm(MedicalRecordAlarm alarm) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //gson samo

        Collection<MedicalRecordAlarm> alarms = getAlarms(gson);
        alarms.add(alarm);

        FileWriter fileWriter = new FileWriter(path);

        String json = gson.toJson(alarms);
        fileWriter.write(json);
        fileWriter.close();
    }

    public static Collection<MedicalRecordAlarm> getAlarms(Gson g) throws IOException {
        Collection<MedicalRecordAlarm> alarms;
//        Gson g = new Gson();
        FileReader fw = new FileReader(path);
        JsonReader reader = new JsonReader(fw);
        Type alarmsType = new TypeToken<Collection<MedicalRecordAlarm>>(){}.getType();
        alarms = g.fromJson(reader, alarmsType);
        reader.close();
        return alarms;
    }
}
