package com.aivle.mini7.dto;

import com.aivle.mini7.model.Log;
import com.aivle.mini7.model.Hospital;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HospitalDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseList {
        private Integer number;
        private String address;
        private String tel1;
        private String tel2;
        private String type;
        private Double distance;
        private int duration;
        private Log logs;

        public static ResponseList of(Hospital hospital){
            return ResponseList.builder()
                    .number(hospital.getNumber())
                    .address(hospital.getAddress())
                    .tel1(hospital.getTel1())
                    .tel2(hospital.getTel2())
                    .type(hospital.getType())
                    .distance(hospital.getDistance())
                    .duration(hospital.getDuration())
                    .logs(hospital.getLog())
                    .build();
        }
    }
}
