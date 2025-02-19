package com.aivle.mini7.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalInfoResponse {
    private String hospitalName;
    private String address;
    private String emergencyMedicalInstitutionType;
    private String phoneNumber1;
    private String phoneNumber3;
    private double latitude;
    private double longitude;
    private double distance;
    private int duration;
}
