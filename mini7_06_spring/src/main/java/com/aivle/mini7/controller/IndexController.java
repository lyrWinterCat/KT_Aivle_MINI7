package com.aivle.mini7.controller;

import com.aivle.mini7.client.api.FastApiClient;
import com.aivle.mini7.client.dto.HospitalInfoResponse;
import com.aivle.mini7.client.dto.HospitalRequest;
import com.aivle.mini7.client.dto.HospitalResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final FastApiClient fastApiClient;

    @GetMapping("/")
    public String index() {
        return "emergencyReport";
    }


    //  erergency신고페이지 테스트
    @GetMapping("/emergencyReport")
    public String emergencyReport(Model model) {
        model.addAttribute("title", "병원 추천 요청"); // title 변수를 추가
        model.addAttribute("username", "user");
        return "emergencyReportTest"; // templates/emergencyReportTest.mustache
    }

    // notEmergency 페이지 테스트
    @GetMapping("/notEmergency")
    public String notEmergency(Model model) {
        // 예시 데이터 생성
        List<HospitalInfoResponse> hospitalList = new ArrayList<>();

        // 병원 정보 추가
        hospitalList.add(createHospitalInfoResponse("예시1", "주소1", "센터1", "전화번호1", "전화번호2", 37.123456, 127.123456, 1.5));
        hospitalList.add(createHospitalInfoResponse("예시2", "주소2", "센터2", "전화번호1", "전화번호2", 37.654321, 127.654321, 2.0));
        hospitalList.add(createHospitalInfoResponse("예시3", "주소3", "센터3", "전화번호1", "전화번호2", 37.789012, 127.789012, 3.0));

        // HospitalResponse 객체 생성
        HospitalResponse hospitalResponse = new HospitalResponse();
        hospitalResponse.setEmergencyGrade(4); // 예시 응급 등급
        hospitalResponse.setDescription("1. 상황 이해: 환자는 등급 4인 비응급상황입니다.\n2. 응급처치:\n- 1. 가벼운 상처 부위를 청결하게 세정하여 소독합니다.\n- 2. 부위에 상처 패드나 거즈를 사용해 압박하고 고정합니다.\n- 3. 아이스팩을 이용해 종아리를 냉각합니다.\n- 4. 통증을 완화하기 위해 목상이나 정형대를 사용할 수 있습니다.\n3. 추가 지침:\n- 구급대 도착 전, 환자의 무리한 움직임을 방지하고 안정된 자세를 유지하도록 도와주세요.\n- 무리한 운동이나 무거운 물건을 든다거나 다치는 부위에 압력을 가하지 말아주세요."
                        .replace("\n", "<br>")); // 줄바꿈을 <br>로 변환 // 예시 프롬프트 메시지
        hospitalResponse.setDutyList(hospitalList); // 병원 리스트 설정

        // 모델에 추가
        model.addAttribute("emergencyLevel", hospitalResponse.getEmergencyGrade());
        model.addAttribute("promptContent", hospitalResponse.getDescription());
        model.addAttribute("hospitalList", hospitalResponse.getDutyList());

        model.addAttribute("title", "응급상황 아님");
        model.addAttribute("username", "user");
        return "notEmergency"; // notEmergency.mustache 템플릿 반환
    }

    private HospitalInfoResponse createHospitalInfoResponse(String name, String address, String center, String phone1, String phone2, double latitude, double longitude, double distance) {
        HospitalInfoResponse hospitalInfo = new HospitalInfoResponse();
        hospitalInfo.setHospitalName(name);
        hospitalInfo.setAddress(address);
        hospitalInfo.setEmergencyMedicalInstitutionType(center);
        hospitalInfo.setPhoneNumber1(phone1);
        hospitalInfo.setPhoneNumber3(phone2);
        hospitalInfo.setLatitude(latitude);
        hospitalInfo.setLongitude(longitude);
        hospitalInfo.setDistance(distance);
        return hospitalInfo;
    }

    @GetMapping("/mustache")
    public String home(Model model) {
        model.addAttribute("title", "Bootstrap Test Page");
        model.addAttribute("username", "mustache");
        return "index";
    }

    @GetMapping("/dy")
    public String dy(Model model) {
        model.addAttribute("title", "Bootstrap Test Page");
        model.addAttribute("username", "bootstrap");
        return "emergency";
    }

    @PostMapping("/recommend_hospital")
    public ModelAndView recommend_hospital(@RequestParam("navercount") Integer navercount,
                                           @RequestParam("text") String text,
                                           @RequestParam("lat") Double lat,
                                           @RequestParam("lon") Double lon) {
        // HospitalRequest 객체 생성
        HospitalRequest request = new HospitalRequest(navercount, text, lat, lon);

//        FastApiClient 를 호출한다.
        HospitalResponse hospitalList = fastApiClient.getHospital(request);
        log.info("hospital: {}", hospitalList);
//        if(hospitalList.isEmpty()){
//            //리스트 없을 때
//            //근데 팀과제는 1~3- 병원리스트. 4-병원추천. 5-건강증진멘트
//        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("recommend_hospital");
        mv.addObject("hospitalList", hospitalList);

        return mv;
    }
}


