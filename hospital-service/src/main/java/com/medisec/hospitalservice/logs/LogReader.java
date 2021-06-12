package com.medisec.hospitalservice.logs;

import com.medisec.hospitalservice.firewall.FirewallLog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogReader implements Runnable {
    private LogSource logSource;

    @Override
    public void run() {
        try (FileReader reader = new FileReader(logSource.getPath())) {
            BufferedReader br = new BufferedReader(reader);
            while (br.readLine() != null){
            }

            while (true) {
                String line = br.readLine();
                if (line != null) {
                    FirewallLog log = new FirewallLog();
                    // ucitaj log
                    // save to db
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
