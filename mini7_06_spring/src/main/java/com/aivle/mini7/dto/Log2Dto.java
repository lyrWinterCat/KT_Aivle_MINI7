package com.aivle.mini7.dto;

import com.aivle.mini7.model.Log2;
import lombok.*;

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
        private String em_latitude;
        private String em_longitude;
        private Integer em_class;
        private String em_text;
        private String hospitallist;
        private String hospital;
        private String hospitalloc;
        private String exptime;
        private String realtime;

        public static Log2Dto.ResponseList of(Log2 log2){
            return ResponseList.builder()
                    .id(log2.getId())
                    .datetime(log2.getDatetime())
                    .em_latitude(log2.getEm_latitude())
                    .em_longitude(log2.getEm_longitude())
                    .em_class(log2.getEm_class())
                    .em_text(log2.getEm_text())
                    .hospitallist(log2.getHospitallist())
                    .hospital(log2.getHospital())
                    .hospitalloc(log2.getHospitalloc())
                    .exptime(log2.getExptime())
                    .realtime(log2.getRealtime())
                    .build();
        }
    }
}
