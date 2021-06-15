package com.medisec.hospitalservice.reports;

import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogAlarm;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogAlarmRepository;
import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import com.medisec.hospitalservice.logs.service_log.ServiceLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class ReportService {
    private final ServiceLogRepository serviceLogRepository;
    private final ServiceLogAlarmRepository serviceLogAlarmRepository;

    public ServiceLogReport getServiceLogReport(){
        List<ServiceLog> logs = serviceLogRepository.findAll();
        Date dateLastMonth = getLastMonth();
        int numberOfLogsLastMonth = serviceLogRepository.findAllByTimeAfter(dateLastMonth).size();
        ServiceLogReport logReport = new ServiceLogReport(logs.size(), numberOfLogsLastMonth);
        logReport.setPolarArea(logs);

        return logReport;
    }

    public ChartAlarmData getServiceAlarmData(){
        List<ServiceLogAlarm> alarms = serviceLogAlarmRepository.findAllByTimeAfter(getLastMonth());
        HashMap<Integer, Integer> alarmsPerDay = getNumberOfAlarmPerDay(alarms);
        ChartAlarmData chartAlarmData = fillEmptyData();

        return createChartAlarmData(alarmsPerDay, chartAlarmData);
    }

    private ChartAlarmData fillEmptyData() {
        ChartAlarmData chartAlarmData = new ChartAlarmData();
        int monthMaxDays = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i = 0; i < monthMaxDays-1; i++) {
            if(!chartAlarmData.getLabels().contains(i+1)) {
                chartAlarmData.getLabels().add(i+1);
                chartAlarmData.getData().add(0);
            }
        }
        return chartAlarmData;
    }

    public ChartAlarmData createChartAlarmData(HashMap<Integer, Integer> alarmsPerDay, ChartAlarmData alarmData){
        Iterator it = alarmsPerDay.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            int key = Integer.parseInt(pair.getKey().toString());
            int value = Integer.parseInt(pair.getValue().toString());
            alarmData.getLabels().add(key-1 , key);
            alarmData.getData().add(key-1, value);
        }
        return alarmData;
    }

    public Date getLastMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get (Calendar.MONTH)-1); // set to the previous month
        return calendar.getTime();
    }
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public HashMap<Integer, Integer> getNumberOfAlarmPerDay(List<ServiceLogAlarm> alarms){
        HashMap<Integer, Integer> alarmsPerDay = new HashMap<Integer, Integer>();
        for(ServiceLogAlarm alarm : alarms){
            Integer key = convertToLocalDateViaInstant(alarm.getTime()).getDayOfMonth();
            if(!alarmsPerDay.containsKey(key))
                alarmsPerDay.put(key, 1);
            else
                alarmsPerDay.put(key, alarmsPerDay.get(key)+1);
        }
        return alarmsPerDay;
    }
}
