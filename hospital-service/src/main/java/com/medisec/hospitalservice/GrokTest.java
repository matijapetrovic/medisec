package com.medisec.hospitalservice;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.Map;

public class GrokTest {

    public static void main(String[] args) {
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        String pattern = "%{IP:sourceIp} %{IP:destIp} %{WORD} %{URIPATHPARAM:path} %{WORD:protocol}/%{BASE10NUM:version} %{INT:status} %{DATESTAMP:time} %{INT:packetSize} %{WORD:type}";

        final Grok grok = grokCompiler.compile(pattern);

        String log = "167.42.163.143 41.190.197.88 POST /login?username=username2&password=ok_password HTTP/1.1 200 11/06/2021 17:08:13 1219 VALID_LOGIN ";

        Match gm = grok.match(log);

        final Map<String, Object> capture = gm.capture();

        for (String key : capture.keySet()) {
            System.out.println(key + " - " + capture.get(key));
        }
    }
}
