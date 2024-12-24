package com.aivle.mini7.controller;

import com.aivle.mini7.dto.Log2Dto;
import com.aivle.mini7.model.Log2;
import com.aivle.mini7.service.Log2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final Log2Service logService;
    private static final String ADMIN_PASSWORD = "1234"; // 관리자 비밀번호 설정

    // 비밀번호 입력 페이지
    @GetMapping("")
    public String adminLoginPage(Model model) {
        model.addAttribute("title","관리자 로그인");
        model.addAttribute("username","admin");
        return "admin/adminLogin"; // admin-login.mustache로 매핑
    }
    @PostMapping("")
    public String verifyAdminPassword(@RequestParam("password") String password, Model model) {
        model.addAttribute("title","관리자 로그인");
        model.addAttribute("username","admin");
        if (ADMIN_PASSWORD.equals(password)) {
            return "redirect:/admin/read"; // 비밀번호가 맞으면 /admin/read로 리다이렉트
        } else {
            model.addAttribute("error", "비밀번호가 틀렸습니다.");
            return "admin/adminLogin"; // 비밀번호가 틀리면 다시 로그인 페이지로
        }
    }

    @GetMapping("/read")
    public ModelAndView adminRead(Model model,@RequestParam(name = "page",defaultValue = "0") int page){
        model.addAttribute("title","Admin");
        model.addAttribute("username","admin");

        Pageable pageable = PageRequest.of(page, 5);
        Page<Log2Dto.ResponseList> logPage = logService.getLogList(pageable);

        model.addAttribute("logPage", logPage);

        model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", logPage.hasNext());
        model.addAttribute("hasPrev", logPage.hasPrevious());

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/adminRead");
        return mv;
    }


}
