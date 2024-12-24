package com.aivle.mini7.controller;

import com.aivle.mini7.model.Board;
import com.aivle.mini7.dto.BoardDto;
import com.aivle.mini7.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;

    @GetMapping("/new")
    public String newArticleForm(Model model) {
        model.addAttribute("title", "게시글 작성");
        return "board"; // 새 게시글 작성 폼 경로
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
