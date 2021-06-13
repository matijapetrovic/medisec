package com.medisec.hospitalservice.alarms.service_log_alarm;

import com.medisec.hospitalservice.logs.service_log.ServiceLog;

import java.util.*;

public class Logs {
    private final int DAY_MILIS = 24  *  60  *  60  *  1000;
    private List<String> maliciousIps = Arrays.asList(
                                            "103.227.8.154", "198.38.90.126", "160.20.45.145", "134.119.192.123", "103.225.53.235", "122.14.131.208",
                                            "122.14.131.208", "160.20.45.87 ", "203.138.203.200", "200.225.202.93", "160.20.45.15", "160.20.45.180",
                                            "103.48.37.61", "163.172.198.101", "160.20.45.241", "185.14.249.86 ", "103.7.155.9", "103.7.155.12",
                                            "103.7.155.4", "103.7.155.6", "103.7.155.7", "103.7.155.10", "103.7.155.2");

    private final List<String> INACTIVE_ACCOUNTS = Arrays.asList( "inactiveuser1", "inactiveuser2", "inactiveuser3" );

    private static List<ServiceLog> logs;

    public Logs(List<ServiceLog> logs) {
        Logs.logs = logs;
    }

    public static Logs of(List<ServiceLog> logs) {
        return new Logs(logs);
    }

    public List<ServiceLog> getLogs() {
        return logs;
    }

    public List<String> getMaliciousIps() {
        return maliciousIps;
    }

    public List<String> getInactiveAccounts() { return INACTIVE_ACCOUNTS; }

    public boolean hasMultipleFailedLoginAttempts() {
        Map<String, Integer> failedAttempts = new HashMap<>();
        for(ServiceLog log: logs) {
            if(failedAttempts.containsKey(log.parseUsernameParam()))
                return true;
            failedAttempts.put(log.parseUsernameParam(), 1);
        }
        return false;
    }

    private List<ServiceLog> getAllFailedLoginAttempts() {
        List<ServiceLog> failedLogs = new ArrayList<>();
        for(ServiceLog log: logs) {
            if(log.getStatus() == 400 && log.parsePathResource().equals("login")) {
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
        List<ServiceLog> failedLogs = getAllFailedLoginAttempts();
        Comparator<ServiceLog> compareByDateTime = (ServiceLog log1, ServiceLog log2) -> log1.getTime().compareTo(log2.getTime());
        Collections.sort(failedLogs, compareByDateTime);

        if (failedLogs.size() < 2)
            return false;
        return failedLogs.size() >= 30 && Math.abs(System.currentTimeMillis() - failedLogs.get(0).getTime().getTime()) > DAY_MILIS;
    }
}