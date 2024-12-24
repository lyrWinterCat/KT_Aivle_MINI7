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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    // 새 글 작성 페이지
    @GetMapping("/new")
    public String newBoardForm(Model model) {
        model.addAttribute("title", "Create New Board");
        model.addAttribute("content", "Create New content");
        model.addAttribute("username", "User Name");
        model.addAttribute("password", "Password");
        return "board/new";
    }

    @PostMapping("/create")
    public String createArticle(BoardDto.Post post) {
        // DTO를 엔티티로 변환 후 저장
        Board board = Board.toEntity(post);
        boardRepository.save(board);
        return "redirect:/board/" + board.getBoardId();
    }

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable("boardId") String boardId, Model model) {
        model.addAttribute("title", "게시글"); // title 변수를 추가
        model.addAttribute("username", "LYR");
        try {
            Long id = Long.parseLong(boardId); // 문자열을 Long으로 변환
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
            model.addAttribute("board", board);
            return "detail"; // 상세 보기 템플릿 경로
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "잘못된 게시글 ID 형식입니다.");
            return "errorPage"; // 에러 페이지 템플릿
        }
    }
    
    // 게시판 목록
    @GetMapping("/list")
    public String getBoardList(Model model,
                               @RequestParam(name="page", defaultValue = "1") int page) {
        // 페이지 요청 준비 (Spring Data Pageable)
        Pageable pageable = PageRequest.of(page - 1, 10);
        model.addAttribute("title", "게시판 목록"); // title 변수를 추가
        model.addAttribute("username", "LYR");
        Page<Board> boardPage = boardRepository.findAll(pageable);

        int totalPages = boardPage.getTotalPages(); // 전체 페이지 수

        // 페이지 번호와 현재 페이지 여부를 저장할 리스트
        List<Map<String, Object>> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .mapToObj(pageNum -> {
                    Map<String, Object> pageMap = new HashMap<>();
                    pageMap.put("pageNumber", pageNum);
                    pageMap.put("isCurrentPage", pageNum == page);
                    return pageMap;
                }).toList();

        // 모델 속성 추가
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("hasPrev", boardPage.hasPrevious());
        model.addAttribute("hasNext", boardPage.hasNext());
        model.addAttribute("prev", page > 1 ? page - 1 : 1);
        model.addAttribute("next", page < totalPages ? page + 1 : totalPages);

        return "list"; // Mustache 템플릿 이름
    }
}

