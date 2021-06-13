package com.medisec.hospitalservice.web.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class KnowledgeSessionHelper {
    public static KieContainer createRuleBase() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        return kieContainer;
    }

//    public static StatelessKieSession getStatlessKnowledgeSession(KieContainer kieContainer, String sessionName) {
//        StatelessKieSession kSession = kieContainer.newStatelessKieSession(sessionName);
//        return kSession;
//    }

    public static KieSession getStatefulKnowledgeSession(KieContainer kieContainer, String sessionName) {
        KieSession kSession = kieContainer.newKieSession(sessionName);
        return kSession;
    }

    public static KieSession generateKieSession() {
       String kSessionName = "ksession-rules";
        KieContainer kieContainer = KnowledgeSessionHelper.createRuleBase();
        KieSession kSession = kieContainer.newKieSession(kSessionName);
        return kSession;
    }
}
