package com.aivle.mini7.controller;

import com.aivle.mini7.dto.BoardDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

    // 게시글 작성 폼 제공
    @GetMapping("/board/new")
    public String newArticleForm(Model model) {
        model.addAttribute("title", "게시글 작성"); // 템플릿에 전달할 데이터 추가
        return "board"; // board.mustache 템플릿 반환
    }

    // 게시글 데이터 처리
    @PostMapping("/board/create")
    public String createArticle(BoardDto post) {
        System.out.println(post.toString());
        return "redirect:/board/new";
    }
}