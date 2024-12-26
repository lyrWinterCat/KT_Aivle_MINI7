package com.aivle.mini7.controller;

import com.aivle.mini7.client.dto.StartHospitalDto;
import com.aivle.mini7.model.Hospital;
import com.aivle.mini7.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PostMapping("/save-start-location")
    public ResponseEntity<String> saveStartLocation(@RequestBody Map<String, Object> requestData) {
        Integer clientId =(Integer) requestData.get("clientId");
        String hospitalName = (String) requestData.get("hospitalName");
        String address = (String) requestData.get("address");
        Integer duration =  Integer.parseInt((String)requestData.get("duration"));

        StartHospitalDto dto = new StartHospitalDto(clientId,hospitalName,address,duration);
        // 데이터베이스에 저장
        logService.updateStartHospital(dto);

        return ResponseEntity.ok("출발 LOG UPDATE");
    }

    @GetMapping("/save-end-location")
    public ResponseEntity<String> saveEndLocation(@RequestParam Integer clientId){
        logService.updateEndHospital(clientId);
        return ResponseEntity.ok("도착 LOG UPDATE");
    }

    @GetMapping("/log-recommended-hospitals")
    public ResponseEntity<List<Hospital>> getRecommendedHospitals(@RequestParam("pk") Integer pk) {
        List<Hospital> recommendedHospitals = logService.getHospitalList(pk);
        return ResponseEntity.ok(recommendedHospitals);
    }
}
