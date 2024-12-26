package com.aivle.mini7.dto;

import com.aivle.mini7.model.Log;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogDto {

    @Data
    @Builder // 빌더 패턴 지원
    public static class ResponseList {
        private Integer id;
        private String datetime;
        private Double latitude;
        private Double longitude;
        private String startAddress;
        private Integer emergencyGrade;
        private String description;
        private String hospitalName;
        private String address;
        private Integer duration;
        private Integer real_duration;
        private String start_time;
        private String end_time;
        private String input_text;

        public static ResponseList of(Log log) {
            return ResponseList.builder()
                    .id(log.getId())
                    .datetime(log.getDatetime())
                    .latitude(log.getLatitude())
                    .longitude(log.getLongitude())
                    .startAddress(log.getStartAddress())
                    .emergencyGrade(log.getEmergencyGrade())
                    .description(log.getDescription())
                    .hospitalName(log.getHospitalName())
                    .address(log.getAddress())
                    .duration(log.getDuration())
                    .real_duration(log.getReal_duration())
                    .start_time(log.getStart_time())
                    .end_time(log.getEnd_time()) // end_time 매핑
                    .input_text(log.getInputText())
                    .build();
        }
    }
}