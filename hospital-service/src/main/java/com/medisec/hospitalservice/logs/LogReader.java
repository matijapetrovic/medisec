package com.medisec.hospitalservice.logs;


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
