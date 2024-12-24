package com.aivle.mini7.controller;

import com.aivle.mini7.model.Board;
import com.aivle.mini7.repository.BoardRepository;
import com.aivle.mini7.repository.CommentRepository;
import com.aivle.mini7.dto.CommentDto;
import com.aivle.mini7.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;

	// 댓글 작성
	@PostMapping("create/{boardId}")
	public String createComment(@PathVariable("boardId") String boardId, CommentDto.Post post) {
		// String을 Long으로 변환
		Long boardid = Long.parseLong(boardId);

		// 게시글 조회
		Board findBoard = boardRepository.findById(boardid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"해당 게시글이 존재하지 않습니다."));

		// DTO를 엔터티로 변환
		Comment comment = Comment.toEntity(post);
		comment.setBoard(findBoard); // 댓글에 게시글 정보 설정

		// 댓글 저장
		commentRepository.save(comment);

		// 저장 후 게시글 상세 페이지로 리다이렉트
		return "redirect:/board/" + boardId;
	}

	// 댓글 삭제하기
	@PostMapping("/delete/{commentId}")
	public String deleteComment(@PathVariable("commentId") String commentId,
								@RequestParam("password") String password) {

		Long commentid = Long.parseLong(commentId);

		Comment findComment = findVerifiedComment(commentid);
		Board findBoard = findComment.getBoard();

		if(!findComment.getPassword().equals(password)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					"비밀번호가 일치하지 않습니다.");
		}
		commentRepository.delete(findComment);

		return "redirect:/board/" + findBoard.getBoardId();
	}
	public Comment findVerifiedComment(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"해당 댓글이 존재하지 않습니다."));
	}
}
