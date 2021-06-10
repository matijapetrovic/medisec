package com.medisec.hospitalservice.firewall;

import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirewallAlarm {
    private final FirewallLogService service;



    public void run() {
        Logs logs = Logs.of(service.findAll());

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");

        // TODO: ako je login vise od 100 u 60 sekundi
        // TODO: pogledati sta cemo sa akauntima koji nisu deaktivrani vise od 90 dana, kako njih obezbediti (za sad imam samo neku random listu)
        kSession.insert(logs);

        for(FirewallLog log: logs.getLogs()) {
            kSession.insert(log);
            kSession.fireAllRules();
            kSession.delete(kSession.getFactHandle(log));
        }
    }
}


