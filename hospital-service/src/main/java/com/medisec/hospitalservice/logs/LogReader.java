package com.medisec.hospitalservice.logs;


import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogsAlarmGenerator;
import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import com.medisec.hospitalservice.logs.service_log.ServiceLogRepository;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RequiredArgsConstructor
public class LogReader implements Runnable {
    private final LogSource logSource;
    private final ServiceLogRepository serviceLogRepository;
    private final ServiceLogsAlarmGenerator serviceLogsAlarmGenerator;

    @Override
    public void run() {
        try (FileReader reader = new FileReader(logSource.getPath())) {
            BufferedReader br = new BufferedReader(reader);
            while (br.readLine() != null){
            }
            while (true) {
                System.out.println("READING LOGS");
                String line = br.readLine();
                if (line != null) {
                    System.out.println("FOUND NEW LOG");
                    ServiceLog log = LogParser.parseLog(line, logSource.getFilter());
                    serviceLogRepository.save(log);
                    serviceLogsAlarmGenerator.run();
                }
                else {
                    Thread.sleep(logSource.getReadFrequency());
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
