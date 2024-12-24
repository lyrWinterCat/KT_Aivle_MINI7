package com.aivle.mini7.controller;

import com.aivle.mini7.dto.Log2Dto;
import com.aivle.mini7.service.Log2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final Log2Service logService;

//     pageable default value
//    @GetMapping("")
//    public ModelAndView index(Pageable pageable) {
//
//
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("admin/index");
//        Page<LogDto.ResponseList> logList = logService.getLogList(pageable);
//        mv.addObject("logList", logList);
//
//        return mv;
//    }

    @GetMapping("")
    public ModelAndView index(Pageable pageable, Model model) {
        model.addAttribute("title","Admin");
        model.addAttribute("username","admin");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/main");
        Page<Log2Dto.ResponseList> logList = logService.getLogList(pageable);
        mv.addObject("logList", logList);
        return mv;
    }
}
