package com.medisec.hospitalservice.firewall;

import com.medisec.hospitalservice.firewall.FirewallLog;
import com.medisec.hospitalservice.firewall.FirewallLogService;
import com.medisec.hospitalservice.medical_record.MedicalRecord;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FirewallAlarm {
    private final FirewallLogService service;

    public void run() {
        List<FirewallLog> firewallLogs = service.findAll();

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");

        for(FirewallLog log: firewallLogs) {
            kSession.insert(log);
            kSession.fireAllRules();
            kSession.delete(kSession.getFactHandle(log));
        }
    }
}
