package com.aivle.mini7.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HospitalResponse {

    private String duty_name;
    private String duty_addr;
    private Double wgs84lon;
    private Double wgs84lat;
    private String dutyTel;
    private String dutyEmclsName;
    private Double road_distance;
}



