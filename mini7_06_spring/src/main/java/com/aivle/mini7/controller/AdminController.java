package com.aivle.mini7.controller;

import com.aivle.mini7.dto.LogDto;
import com.aivle.mini7.service.LogService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final LogService logService;

    @Value("${app.base-url}")
    private String baseUrl;

    private static final String ADMIN_PASSWORD = "1234"; // 관리자 비밀번호 설정

    // 비밀번호 입력 페이지
    @GetMapping("")
    public String adminLoginPage(Model model) {
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("title", "관리자 로그인");
        model.addAttribute("username", "admin");
        return "admin/adminLogin"; // admin-login.mustache로 매핑
    }

    // 비밀번호 검증
    @PostMapping("")
    public String verifyAdminPassword(@RequestParam("password") String password, Model model, HttpSession session) {
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("title", "관리자 로그인");
        model.addAttribute("username", "admin");

        if (ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("isAdmin", true); // 세션에 관리자 권한 저장
            return "redirect:/admin/read"; // 비밀번호가 맞으면 /admin/read로 리다이렉트
        } else {
            model.addAttribute("error", "비밀번호가 틀렸습니다.");
            return "admin/adminLogin"; // 비밀번호가 틀리면 다시 로그인 페이지로
        }
    }

    // 관리자 페이지
    @GetMapping("/read")
    public ModelAndView adminRead(Model model,
                                  @RequestParam(name = "page", defaultValue = "1") int page,
                                  HttpSession session) {
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("title", "Admin");
        model.addAttribute("username", "admin");

        // 세션에서 관리자 권한 확인
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return new ModelAndView("redirect:/admin"); // 비로그인 상태면 로그인 페이지로 리다이렉트
        }

        Pageable pageable = PageRequest.of(page-1, 5);
        Page<LogDto.ResponseList> logPage = logService.getLogList(pageable);

        //페이지네이션
        int totalPages = logPage.getTotalPages();

        List<Map<String, Object>> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .mapToObj(pageNum -> {
                            Map<String, Object> pageMap = new HashMap<>();
                            pageMap.put("pageNumber", pageNum);
                            pageMap.put("isCurrentPage",pageNum == page);
                            return pageMap;
                        }).toList();

        model.addAttribute("logPage", logPage);
        model.addAttribute("prev", page > 1 ? page - 1 : 1);
        model.addAttribute("next", page < totalPages ? page + 1 : totalPages);
        model.addAttribute("hasNext", logPage.hasNext());
        model.addAttribute("hasPrev", logPage.hasPrevious());
        model.addAttribute("pageNumbers", pageNumbers);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/adminRead");
        return mv;
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/admin"; // 로그인 페이지로 리다이렉트
    }
}