package com.medisec.hospitalservice.patient;

import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientDetails {
    private int height;
    private int weight;
    private BloodGroup bloodGroup;
    private Dioptry dioptry;


    @Data
    public static class BloodGroup {
        BloodType type;
        Boolean rhPositive;

        public String getBloodTypeString() {
            return type != null ? type.toString() : null;
        }

        public static List<String> getBloodTypes() {
            return Arrays.stream(BloodType.values()).map(Objects::toString).collect(Collectors.toList());
        }

        public enum BloodType {
            A, B, AB, O;
        }
    }


    @Data
    public static class Dioptry {
        Double left;
        Double right;
    }
}
