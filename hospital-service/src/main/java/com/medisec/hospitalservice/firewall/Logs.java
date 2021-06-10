package com.medisec.hospitalservice.firewall;

import java.util.Arrays;
import java.util.List;

public class Logs {
    private final List<String> MALICIOUS_IPS = Arrays.asList(
                                            "103.227.8.154", "198.38.90.126", "160.20.45.145", "134.119.192.123", "103.225.53.235", "122.14.131.208",
                                            "122.14.131.208", "160.20.45.87 ", "203.138.203.200", "200.225.202.93", "160.20.45.15", "160.20.45.180",
                                            "103.48.37.61", "163.172.198.101", "160.20.45.241", "185.14.249.86 ", "103.7.155.9", "103.7.155.12",
                                            "103.7.155.4", "103.7.155.6", "103.7.155.7", "103.7.155.10", "103.7.155.2");

    private final List<String> INACTIVE_ACCOUNTS = Arrays.asList( "inactiveuser1", "inactiveuser2", "inactiveuser3" );

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

    public List<String> getMaliciousIps() {
        return MALICIOUS_IPS;
    }

    public List<String> getInactiveAccounts() { return INACTIVE_ACCOUNTS; }
}