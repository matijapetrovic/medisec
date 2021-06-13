package com.medisec.hospitalservice.alarms.service_log_alarm;

import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import com.medisec.hospitalservice.logs.service_log.ServiceLogService;
import com.medisec.hospitalservice.web.config.KnowledgeSessionHelper;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceLogsAlarmGenerator {
    private final ServiceLogService service;

    public void run() {
        Logs logs = Logs.of(service.findAll());
        KieSession kSession = KnowledgeSessionHelper.generateKieSession();

        // TODO: ako je login vise od 100 u 60 sekundi
        // TODO: pogledati sta cemo sa akauntima na koje se nije logovao vise od 90 dana, kako njih obezbediti (za sad imam samo neku random listu)
        kSession.insert(logs);

        for(ServiceLog log: logs.getLogs()) {
            kSession.insert(log);
            kSession.fireAllRules();
            kSession.delete(kSession.getFactHandle(log));
        }
    }
}
