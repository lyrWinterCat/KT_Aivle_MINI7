package com.aivle.mini7.controller;

import com.aivle.mini7.client.api.FastApiClient;
import com.aivle.mini7.client.dto.HospitalInfoResponse;
import com.aivle.mini7.client.dto.HospitalRequest;
import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.model.Hospital;
import com.aivle.mini7.model.Log;
import com.aivle.mini7.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final FastApiClient fastApiClient;
    private final LogService logService;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${naver.map.client-id}")
    private String mapClientId; // application.properties에서 API 키 불러오기

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("title", "병원 추천 요청"); // title 변수를 추가
        model.addAttribute("username", "user");
        return "emergencyReport";
    }

    //  erergency신고페이지 테스트
    @GetMapping("/emergencyReport")
    public String emergencyReport(Model model) {
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("title", "병원 추천 요청"); // title 변수를 추가
        model.addAttribute("username", "user");
        return "emergencyReport"; // templates/emergencyReport.mustache
    }



    // 여기서 추가해야할 기능 : requestParam을 기본적으로 logDB에 저장하고,
    // db에 저장된 테이블의 id를 받아서 가지고 있어야함.
    // 그 id를 가지고 emergency/notEmergency에서 logDB에 한번 더 저장할 수 있어야함
    @PostMapping("/recommend_hospital")
    public ModelAndView recommend_hospital(@RequestParam("navercount") Integer navercount,
                                           @RequestParam("text") String text,
                                           @RequestParam("lat") Double lat,
                                           @RequestParam("lon") Double lon) {

        // HospitalRequest 객체 생성
        HospitalRequest request = new HospitalRequest(navercount, text, lat, lon);

        // FastApiClient 를 호출하여 응답 받기
        HospitalResponse hospitalResponse = fastApiClient.getHospital(request);
        log.info("hospital: {}", hospitalResponse);

        //날짜 포맷팅
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd HH:mm:ss");

        //== log db 저장 ==//
        Log log = Log.builder()
                .inputText(text)
                .datetime(formatter.format(new Date()))
                .latitude(lat)
                .longitude(lon)
                .startAddress(hospitalResponse.getSourceAddr())
                .emergencyGrade(hospitalResponse.getEmergencyGrade())
                .description(hospitalResponse.getDescription())
                .build();

        Log client_log = logService.insertLog(log);


        //== hospital db 저장 ==//
        List<HospitalInfoResponse> dutyList = hospitalResponse.getDutyList(); //Fast api로 받아온 리스트

        for(HospitalInfoResponse duty:dutyList){
            Hospital hpt = Hospital.builder()
                    .name(duty.getHospitalName())
                    .address(duty.getAddress())
                    .tel1(duty.getPhoneNumber1())
                    .tel2(duty.getPhoneNumber3())
                    .type(duty.getEmergencyMedicalInstitutionType())
                    .distance(duty.getDistance())
                    .duration(duty.getDuration())
                    .log(client_log)
                    .build();

            logService.insertHospital(hpt);
        }




        // emergencyGrade에 따라 리다이렉트
        int emergencyGrade = hospitalResponse.getEmergencyGrade();
        if (emergencyGrade >= 1 && emergencyGrade <= 3) {
            ModelAndView mv = new ModelAndView("emergency"); // emergency.mustache로 이동
            mv.addObject("hospitalResponse", hospitalResponse); // 응답 데이터를 모델에 추가
            mv.addObject("client_id",client_log.getId()); //client_id(pk) 넘김
            return emergency(hospitalResponse,lat,lon,client_log.getId());
        } else if (emergencyGrade >= 4 && emergencyGrade <= 5) {
            return notEmergency(hospitalResponse); // notEmergency 메서드 호출
        }

        // 기본적으로 emergency.mustache로 이동
        return new ModelAndView("emergency");
    }

    @GetMapping("/emergency")
    public ModelAndView emergency(HospitalResponse hospitalResponse, Double lat, Double lon,int client_id){
        // 모델 생성
        ModelAndView mv = new ModelAndView("emergency"); // notEmergency.mustache 템플릿 반환
        mv.addObject("baseUrl",baseUrl);
        // 응급 등급 및 프롬프트 메시지 설정
        mv.addObject("emergencyLevel", hospitalResponse.getEmergencyGrade());
        mv.addObject("promptContent", hospitalResponse.getDescription().replace("\n", "<br>")); // 줄바꿈을 <br>로 변환
        mv.addObject("hospitalList", hospitalResponse.getDutyList()); // 병원 리스트 설정

        //내위치
        mv.addObject("myLat", lat);
        mv.addObject("myLng", lon);
        mv.addObject("source_addr", hospitalResponse.getSourceAddr());

        //map api id
        mv.addObject("mapClientId",mapClientId);

        //client id
        mv.addObject("client_id",client_id);

        mv.addObject("title", "응급상황 안내");
        mv.addObject("username", "user");

        return mv;
    }

    // notEmergency 페이지 테스트
    @GetMapping("/notEmergency") // 이 메서드는 이제 직접 호출되지 않음
    public ModelAndView notEmergency(HospitalResponse hospitalResponse) {
        // 모델 생성
        ModelAndView mv = new ModelAndView("notEmergency"); // notEmergency.mustache 템플릿 반환
        mv.addObject("baseUrl",baseUrl);
        // 응급 등급 및 프롬프트 메시지 설정
        mv.addObject("emergencyLevel", hospitalResponse.getEmergencyGrade());
        mv.addObject("promptContent", hospitalResponse.getDescription().replace("\n", "<br>")); // 줄바꿈을 <br>로 변환
        mv.addObject("hospitalList", hospitalResponse.getDutyList()); // 병원 리스트 설정

        mv.addObject("title", "비응급상황 안내");
        mv.addObject("username", "user");

        return mv;
    }
}


