package com.medisec.hospitalservice.alarms.service_log_alarm;

import com.medisec.hospitalservice.logs.service_log.ServiceLog;

import java.util.*;

public class LogHandler {
    private final int DAY_MILIS = 24  *  60  *  60  *  1000;
    private List<String> maliciousIps = Arrays.asList(
                                            "103.227.8.154", "198.38.90.126", "160.20.45.145", "134.119.192.123", "103.225.53.235", "122.14.131.208",
                                            "122.14.131.208", "160.20.45.87 ", "203.138.203.200", "200.225.202.93", "160.20.45.15", "160.20.45.180",
                                            "103.48.37.61", "163.172.198.101", "160.20.45.241", "185.14.249.86 ", "103.7.155.9", "103.7.155.12",
                                            "103.7.155.4", "103.7.155.6", "103.7.155.7", "103.7.155.10", "103.7.155.2");

    private final List<String> INACTIVE_ACCOUNTS = Arrays.asList( "inactiveuser1", "inactiveuser2", "inactiveuser3" );
    private static List<ServiceLog> logs;

    public LogHandler(List<ServiceLog> logs) {
        LogHandler.logs = logs;
    }

    public static LogHandler of(List<ServiceLog> logs) {
        return new LogHandler(logs);
    }

    public List<ServiceLog> getLogs() {
        return logs;
    }

    public List<String> getMaliciousIps() {
        return maliciousIps;
    }

    public List<String> getInactiveAccounts() { return INACTIVE_ACCOUNTS; }

    public boolean ok() {return true;}

    public boolean hasMultipleFailedLoginAttempts() {
        Map<String, Integer> failedAttempts = new HashMap<>();
        for(ServiceLog log: getAllFailedLoginAttempts()) {
            if(failedAttempts.containsKey(log.parseUsernameParam()))
                return true;
            failedAttempts.put(log.parseUsernameParam(), 1);
        }
        return false;
    }

    private List<ServiceLog> getAllFailedLoginAttempts() {
        List<ServiceLog> failedLogs = new ArrayList<>();
        for(ServiceLog log: logs) {
            if(log.getType() == LogType.FAILED_LOGIN) {
                failedLogs.add(log);
            }
        }
        return failedLogs;
    }

    private boolean isDateInLast24Hours(ServiceLog log) {
        return Math.abs(System.currentTimeMillis() - log.getTime().getTime()) < DAY_MILIS;
    }

    private Map<String, Integer> getUsersNumberOfFailedLogins(List<ServiceLog> allFailedAttempts) {
        Map<String, Integer> failedAttempts = new HashMap<>();
        for(ServiceLog log: allFailedAttempts) {
            String sourceIp = log.getSourceIp();
            if(failedAttempts.containsKey(sourceIp) && isDateInLast24Hours(log))
                failedAttempts.put(sourceIp, failedAttempts.get(sourceIp) + 1);
            else {
                failedAttempts.put(log.parseUsernameParam(), 1);
            }
        }
        return failedAttempts;
    }

    public boolean are30LoginAttemptsFailedIn24HoursFromTheSameIpAddress() {
        List<ServiceLog> failedLogins = getAllFailedLoginAttempts();
        Comparator<ServiceLog> compareByDateTime = Comparator.comparing(ServiceLog::getTime);
        Collections.sort(failedLogins, compareByDateTime);
        Map<String, Integer> numOfAllUsersFailedLoginAttempts = getUsersNumberOfFailedLogins(failedLogins);
        System.out.println(numOfAllUsersFailedLoginAttempts.size());
        return Collections.max(numOfAllUsersFailedLoginAttempts.values()) >= 2;
    }

}