package com.medisec.hospitalservice.logs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogSourceService {

    @Value("${log.sources.config}")
    private String logSourcesConfig;

    public void updateSources(List<LogSource> sources) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(sources.toArray());
        FileWriter writer = new FileWriter(logSourcesConfig);
        writer.write(json);
    }

    public List<LogSource> getSources() {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(logSourcesConfig))
        {
            LogSource[] sources = mapper.readValue(reader, LogSource[].class);
            return Arrays.asList(sources.clone());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
