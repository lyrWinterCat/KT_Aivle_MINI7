package com.aivle.mini7.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalRequest {
    private Integer navercount;
    private String text;
    private Double lat;
    private Double lon;
}