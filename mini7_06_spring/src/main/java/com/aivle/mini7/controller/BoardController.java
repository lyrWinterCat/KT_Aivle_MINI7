package com.aivle.mini7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.aivle.mini7.model.Board;
import com.aivle.mini7.repository.BoardRepository;
import com.aivle.mini7.service.BoardService;
import com.aivle.mini7.dto.BoardDto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        model.addAttribute("title", "게시글 작성");
        return "board"; // 새 게시글 작성 폼 경로
    }

    // 게시글 데이터 처리
    @PostMapping("/create2")
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

	@GetMapping("/list2")
	public String getBoardList(Model model){
		List<Board> boardList = boardRepository.findAll();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        model.addAttribute("board", board);
        return "detail"; // 상세 보기 템플릿 경로
    }

    @GetMapping("/list")
    public String getBoardList(Model model,
                               @RequestParam(name="page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 7);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        model.addAttribute("newsPage", boardPage);
        model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", boardPage.hasNext());
        model.addAttribute("hasPrev", boardPage.hasPrevious());
        return "list";
    }

    @PostMapping("/create")
    public String createArticle(BoardDto.Post post) {
        // DTO를 엔티티로 변환 후 저장
        Board board = Board.toEntity(post);
        boardRepository.save(board);
        return "redirect:/board/" + board.getBoardId();
    }
}
