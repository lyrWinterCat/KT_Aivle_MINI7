package com.aivle.mini7.client.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StartHospitalDto {

    private Integer clientId;
    private String hospitalName;
    private String address;
    private Integer duration;

}
