package com.aivle.mini7.dto;

import com.aivle.mini7.model.Hospital;
import com.aivle.mini7.model.Log2;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Log2Dto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseList {
        private Integer id;
        private String datetime;
        private String latitude;
        private String longitude;
        private String startAddress;
        private Integer emergencyGrade;
        private String description;
        private String hospitalName;
        private String address;
        private String duration;
        private String real_duration;
        private List<Hospital> hospitals;

        public static ResponseList of(Log2 log2){
            return ResponseList.builder()
                    .id(log2.getId())
                    .datetime(log2.getDatetime())
                    .latitude(log2.getLatitude())
                    .longitude(log2.getLongitude())
                    .startAddress(log2.getStartAddress())
                    .emergencyGrade(log2.getEmergencyGrade())
                    .description(log2.getDescription())
                    .hospitalName(log2.getHospitalName())
                    .address(log2.getAddress())
                    .duration(log2.getDuration())
                    .real_duration(log2.getReal_duration())
                    .hospitals(log2.getHospitals())
                    .build();
        }
    }
}
