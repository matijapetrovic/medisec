package com.medisec.hospitalservice.firewall;

import com.medisec.hospitalservice.medical_record.MedicalRecord;

import java.util.*;

public class Logs {
    private final int DAY_MILIS = 24  *  60  *  60  *  1000;
    private List<String> maliciousIps = Arrays.asList(
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
        return maliciousIps;
    }

    public List<String> getInactiveAccounts() { return INACTIVE_ACCOUNTS; }

    public boolean hasMultipleFailedLoginAttempts() {
        Map<String, Integer> failedAttempts = new HashMap<>();
        for(FirewallLog log: logs) {
            if(failedAttempts.containsKey(log.getUsername()))
                return true;
            failedAttempts.put(log.getUsername(), 1);
        }
        return false;
    }

    private List<FirewallLog> getAllFailedLoginAttempts() {
        List<FirewallLog> failedLogs = new ArrayList<>();
        for(FirewallLog log: logs) {
            if(log.getStatus() == 400 && log.getPathResource() == "login") {
                failedLogs.add(log);
            }
        }
        return failedLogs;
    }

//    private Map<String, FirewallLog> mapLogsToIpAddresses(List<FirewallLog> firewallLogs) {
//        Map<String, FirewallLog> logsMap = new HashMap<>();
//        for(FirewallLog log: firewallLogs) {
//            if(logsMap.containsKey(log.getSourceIp())) {
//
//            }
//        }
//    }

    public boolean are30LoginAttemptsFailedIn24Hours() {
        List<FirewallLog> failedLogs = getAllFailedLoginAttempts();
        Comparator<FirewallLog> compareByDateTime = (FirewallLog log1, FirewallLog log2) -> log1.getTime().compareTo(log2.getTime());
        Collections.sort(failedLogs, compareByDateTime);

        if (failedLogs.size() < 2)
            return false;
        return failedLogs.size() >= 30 && Math.abs(System.currentTimeMillis() - failedLogs.get(0).getTime().getTime()) > DAY_MILIS;
    }
}