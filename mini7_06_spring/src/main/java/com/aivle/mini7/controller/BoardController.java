package com.aivle.mini7.controller;

import org.springframework.beans.factory.annotation.Value;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	@Value("${app.base-url}") //nav바에서 바로가기를 위함 (개발/배포 설정시)
	private String baseUrl;

	// 새 글 작성 페이지
	@GetMapping("/new")
	public String newBoardForm(Model model) {
		model.addAttribute("baseUrl", baseUrl);

		model.addAttribute("title", "Create New Board");
		model.addAttribute("content", "Create New content");
		model.addAttribute("username", "User Name");
		model.addAttribute("password", "Password");
		return "board/new";
	}

    // 게시글 작성
    @PostMapping("/create")
    public String createArticle(BoardDto.Post post) {

        // DTO를 엔티티로 변환 후 저장
        Board board = Board.toEntity(post);
        boardRepository.save(board);
        return "redirect:/board/" + board.getBoardId();
    }

    // 게시글 삭제
    @GetMapping("/{boardId}/delete")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        boardRepository.deleteById(boardId);
        return "redirect:/board/list";
    }

    // 게시글 수정
    @PostMapping("/{boardId}/update")
    public String updateBoard(
            @PathVariable("boardId") Long boardId,
            @ModelAttribute BoardDto.Update updateDto) {
        // 기존 게시글 가져오기
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 수정 사항 반영
        board.setTitle(updateDto.getTitle());
        board.setContent(updateDto.getContent());
        board.setUpdateTime(LocalDateTime.now());

        // 변경된 데이터 저장
        boardRepository.save(board);

        // 게시글 상세 페이지로 리다이렉트
        return "redirect:/board/" + boardId;
    }

    @GetMapping("/{boardId}/edit")
    public String editBoardForm(@PathVariable("boardId") Long boardId, Model model) {
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("title", "게시글 수정");
        model.addAttribute("username", "LYR");
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        model.addAttribute("board", board);
        return "board/edit"; // 템플릿 파일 이름
    }

	// 게시글 상세 페이지
	@GetMapping("/{boardId}")
	public String getBoard(@PathVariable("boardId") String boardId, Model model) {
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("title", "게시글");
        model.addAttribute("username", "LYR");
        try {
            Long id = Long.parseLong(boardId); // 문자열을 Long으로 변환
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			model.addAttribute("board", board);

			String formattedCreateTime = board.getCreateTime().format(formatter);
			String formattedUpdateTime = board.getUpdateTime() != null ? board.getUpdateTime().format(formatter) : null;

			// 수정 시간이 존재하면 추가
			if (board.getUpdateTime() != null) {
				model.addAttribute("formattedUpdateTime", formattedUpdateTime);
			} else {
				model.addAttribute("formattedUpdateTIme", formattedCreateTime);
			}

            return "board/detail"; // 상세 보기 템플릿 경로
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "잘못된 게시글 ID 형식입니다.");
            return "errorPage"; // 에러 페이지 템플릿
        }
    }
    	

	// 게시판 목록
	@GetMapping("/list")
	public String getBoardList(Model model,
							   @RequestParam(name = "page", defaultValue = "1") int page) {
		model.addAttribute("baseUrl", baseUrl);
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

		return "board/list"; // Mustache 템플릿 이름
	}
}
	