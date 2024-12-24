package com.aivle.mini7.controller;

import com.aivle.mini7.client.api.FastApiClient;
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

    @GetMapping("/emergencyReport")
    public String emergencyReport(Model model) {
        model.addAttribute("title", "병원 추천 요청"); // title 변수를 추가
        model.addAttribute("username", "LYR");
        return "emergencyReportTest"; // templates/emergencyReportTest.mustache
    }

    @GetMapping("/mustache")
    public String home(Model model) {
        model.addAttribute("title", "Bootstrap Test Page");
        model.addAttribute("username", "LYR");
        return "index";
    }

    @GetMapping("/dy")
    public String dy(Model model) {
        model.addAttribute("title", "Bootstrap Test Page");
        model.addAttribute("username", "LYR");
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


