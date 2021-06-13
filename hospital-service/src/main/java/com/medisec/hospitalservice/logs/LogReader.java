package com.medisec.hospitalservice.logs;


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

    @Override
    public void run() {
        try (FileReader reader = new FileReader(logSource.getPath())) {
            BufferedReader br = new BufferedReader(reader);
            while (br.readLine() != null){
            }

            while (true) {
                String line = br.readLine();
                if (line != null) {
                    ServiceLog log = LogParser.parseLog(line, logSource.getFilter());
                    serviceLogRepository.save(log);
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
