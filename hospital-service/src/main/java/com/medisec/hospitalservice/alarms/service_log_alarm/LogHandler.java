package com.medisec.hospitalservice.alarms.service_log_alarm;

import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.io.IOException;
import java.util.*;

public class LogHandler {
    private final Long MINUTE_MILIS = 60  *  1000L;
    private final Long DAY_MILIS = 24  *  60  *  60  *  1000L;
    private final Long NINETY_DAYS_MILIS = 90 * 24 * 60 * 60 * 1000L;

    private static List<ServiceLog> logs;
    private Set<String> inactiveAccounts;
    private List<String> maliciousIps = MaliciousIpsService.getMaliciousIps();

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

    public void updateMaliciousIps(String ip) throws IOException {
        List<String> ips = new ArrayList<>(maliciousIps);
        ips.add(ip);
        MaliciousIpsService.updateMaliciousIps(ips);
    }

    public Set<String> getInactiveAccounts() {
        if (inactiveAccounts == null) {
            inactiveAccounts =  new HashSet<>();
            for(ServiceLog log: logs) {
                if (log.getType() == LogType.VALID_LOGIN && !isDateInRange(log, NINETY_DAYS_MILIS))
                    inactiveAccounts.add(log.parseUsernameParam());
            }
        }
        return inactiveAccounts;
    }

    public void removeInactiveAccount(String account) {
        inactiveAccounts.remove(account);
    }

    public boolean hasMultipleFailedLoginAttempts() {
        Map<String, Integer> failedAttempts = new HashMap<>();
        for(ServiceLog log: getAllLogsOfType(LogType.FAILED_LOGIN)) {
            if(failedAttempts.containsKey(log.parseUsernameParam()))
                return true;
            failedAttempts.put(log.parseUsernameParam(), 1);
        }
        return false;
    }

    private List<ServiceLog> getAllLogsOfType(LogType type) {
        List<ServiceLog> failedLogs = new ArrayList<>();
        for(ServiceLog log: logs) {
            if(log.getType() == type) {
                failedLogs.add(log);
            }
        }
        return failedLogs;
    }

    private boolean isDateInRange(ServiceLog log, Long range) {
        return Math.abs(System.currentTimeMillis() - (Long)log.getTime().getTime()) < range;
    }

    private Map<String, Integer> getUsersNumberOfFailedLoginsIn24Hours(List<ServiceLog> allFailedAttempts) {
        Map<String, Integer> failedAttempts = new HashMap<>();
        for(ServiceLog log: allFailedAttempts) {
            String sourceIp = log.getSourceIp();
            if (isDateInRange(log, DAY_MILIS)) {
                if(failedAttempts.containsKey(sourceIp))
                    failedAttempts.put(sourceIp, failedAttempts.get(sourceIp) + 1);
                else {
                    failedAttempts.put(sourceIp, 1);
                }
            }
        }
        return failedAttempts;
    }

    private Map<String, Integer> getUsersNumberOfFailedLoginsIn60Seconds(List<ServiceLog> allFailedAttempts) {
        Map<String, Integer> failedAttempts = new HashMap<>();
        for(ServiceLog log: allFailedAttempts) {
            String username = log.parseUsernameParam();
            if (isDateInRange(log, MINUTE_MILIS)) {
                if(failedAttempts.containsKey(username))
                    failedAttempts.put(username, failedAttempts.get(username) + 1);
                else {
                    failedAttempts.put(username, 1);
                }
            }
        }
        return failedAttempts;
    }

    private int getNumberOfFailedRequestsInLastMinute(List<ServiceLog> failedRequests) {
        int count = 0;
        for(ServiceLog log: failedRequests) {
            if (isDateInRange(log, MINUTE_MILIS))
                count++;
        }
        return count;
    }

    public boolean are30LoginAttemptsFromTheSameIpAddressFailedIn24Hours() {
        Map<String, Integer> numOfAllUsersFailedLoginAttempts =
                getUsersNumberOfFailedLoginsIn24Hours(getAllLogsOfType(LogType.FAILED_LOGIN));
        if(numOfAllUsersFailedLoginAttempts.size() == 0) return false;
        return Collections.max(numOfAllUsersFailedLoginAttempts.values()) >= 30;
    }

    public boolean are50LoginAttemptsFailedInLastMinuteOccurred() {
        Map<String, Integer> numOfAllUsersFailedLoginAttempts =
                getUsersNumberOfFailedLoginsIn60Seconds(getAllLogsOfType(LogType.FAILED_LOGIN));
        if(numOfAllUsersFailedLoginAttempts.size() == 0) return false;
        return  Collections.max(numOfAllUsersFailedLoginAttempts.values()) >= 50;
    }


    public boolean are50RequestsInLastMinuteOccurred() {
        List<LogType> types = Arrays.asList(LogType.BRUTE_FORCE_ATTACK, LogType.DOS_ATTACK, LogType.ERROR, LogType.MALICIOUS_IP, LogType.INACTIVE_ACCOUNT);
        List<ServiceLog> failedRequests;
        for (LogType type: types) {
            failedRequests = getAllLogsOfType(type);
            if (getNumberOfFailedRequestsInLastMinute(failedRequests) >= 50) return true;
        }
        return false;
    }
}