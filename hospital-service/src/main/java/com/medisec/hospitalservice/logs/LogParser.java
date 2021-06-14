package com.medisec.hospitalservice.logs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.Map;

public class LogParser {
    private static final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();

    public static ServiceLog parseLog(String line, String pattern) {
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        Grok grok = grokCompiler.compile(pattern);
        Map<String, Object> logFields = gson.fromJson(gson.toJson(new ServiceLog()), new TypeToken<Map<String, String>>(){}.getType());
        Match gm = grok.match(line);
        Map<String, Object> captured = gm.capture();
        if (captured.size() == 0) return null;

        for (String field : logFields.keySet()) {
            if (captured.containsKey(field)) {
                logFields.put(field, captured.get(field));
            }
        }

        return gson.fromJson(gson.toJson(logFields, new TypeToken<Map<String, String>>(){}.getType()), ServiceLog.class);
    }
}
