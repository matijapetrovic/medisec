package com.medisec.hospitalservice.reports;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ChartAlarmData {
    private ArrayList<Integer> labels;
    private ArrayList<Integer> data;

    public ChartAlarmData(){
        labels = new ArrayList<Integer>();
        data = new ArrayList<Integer>();
    }
}
