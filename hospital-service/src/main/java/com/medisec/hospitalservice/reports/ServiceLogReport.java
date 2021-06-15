package com.medisec.hospitalservice.reports;

import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ServiceLogReport {
    private int totalLogs;
    private int totalLogsLastMonth;
    private PolarAreaData polarArea;

    public ServiceLogReport(int totalLogs, int totalLogsLastMonth){
        this.totalLogs = totalLogs;
        this.totalLogsLastMonth = totalLogsLastMonth;
    }
    public void setPolarArea(List<ServiceLog> logs){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for(ServiceLog log : logs){
            String key = log.getType().toString();
            if(!map.containsKey(key))
                map.put(key, 1);
            else
                map.put(key, map.get(key) + 1);
        }
        Iterator it = map.entrySet().iterator();
        PolarAreaData pd = new PolarAreaData();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String key = pair.getKey().toString();
            Integer value = Integer.parseInt(pair.getValue().toString());
            pd.getLogTypes().add(key);
            pd.getCountEachType().add(value);
            it.remove();
        }

        this.polarArea = pd;
    }
}
