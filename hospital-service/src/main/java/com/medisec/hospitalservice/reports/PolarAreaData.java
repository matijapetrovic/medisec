package com.medisec.hospitalservice.reports;

import lombok.Data;
import java.util.ArrayList;

@Data
public class PolarAreaData {
    private ArrayList<Integer> countEachType;
    private ArrayList<String> logTypes;

    public PolarAreaData(){
        this.countEachType = new ArrayList<>();
        this.logTypes = new ArrayList<>();
    }
}
