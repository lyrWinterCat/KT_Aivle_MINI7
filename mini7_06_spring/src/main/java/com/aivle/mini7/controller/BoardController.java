package com.aivle.mini7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.aivle.mini7.model.Board;
import com.aivle.mini7.repository.BoardRepository;
import com.aivle.mini7.service.BoardService;
import com.aivle.mini7.dto.BoardDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	private final BoardRepository boardRepository;
	
    // 게시글 작성 폼 제공
    @GetMapping("/new")
    public String newArticleForm(Model model) {
        model.addAttribute("title", "게시글 작성"); // 템플릿에 전달할 데이터 추가
        return "board"; // board.mustache 템플릿 반환
    }

    // 게시글 데이터 처리
    @PostMapping("/create")
    public String createArticle(BoardDto post) {
        System.out.println(post.toString());
        return "redirect:/board/new";
    }


	// 새 글 작성 페이지
	@GetMapping("/new2")
	public String newBoardForm(Model model) {
		model.addAttribute("title", "Create New Board");
		model.addAttribute("content", "Create New content");
		model.addAttribute("username", "User Name");
		model.addAttribute("password", "Password");
		return "board/new";
	}

	// 새 글 작성 처리
	@PostMapping("/create2")
	public String createBoard(
			@RequestParam String title,
			@RequestParam String content,
			@RequestParam String username,
			@RequestParam String password
	) {
		// 서비스 계층 호출
		boardService.saveBoard(title, content, username, password);
		return "redirect:/board/list";
	}

	@GetMapping("/list")
	public String getBoardList(Model model){
		List<Board> boardList = boardRepository.findAll();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}
}
