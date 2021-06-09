package com.medisec.hospitalservice.firewall;

import java.util.List;

public class Logs {
    private static List<FirewallLog> logs;

    public Logs(List<FirewallLog> logs) {
        Logs.logs = logs;
    }

    public static Logs of(List<FirewallLog> logs) {
        return new Logs(logs);
    }

    public List<FirewallLog> getLogs() {
        return logs;
    }
}