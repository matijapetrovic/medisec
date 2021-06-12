package com.medisec.hospitalservice.patient;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientDetails {
    private int age;
    private int height;
    private int weight;
    private BloodGroup bloodGroup;
    private Dioptry dioptry;
    private List<Vaccination> vaccinations;
    private List<Surgery> surgeries;

    @Data
    public static class Vaccination {
        final String name;
        final Date date;
    }

    @Data
    public static class Surgery {
        final String name;
        final Date date;
    }

    @Data
    public static class BloodGroup {
        final BloodType type;
        final Boolean rhPositive;

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
        final double left;
        final double right;
    }
}
