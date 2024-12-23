package com.aivle.mini7.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HospitalResponse {
    private int emergencyGrade;
    private String description;
    private List<HospitalInfoResponse> dutyList;
}