package com.medisec.hospitalservice.alarms.service_log_alarm;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public class MaliciousIpsService {
    //@Value("${malicious.ips.config}")
    private static String ipsConfig = "../hospital-service/src/main/resources/maliciousIps.json";

    public static void updateMaliciousIps(List<String> ips) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(ips.toArray());
        FileWriter writer = new FileWriter(ipsConfig);
        writer.write(json);
        writer.flush();
    }

    public static List<String> getMaliciousIps() {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(ipsConfig))
        {
            String[] sources = mapper.readValue(reader, String[].class);
            return Arrays.asList(sources.clone());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
