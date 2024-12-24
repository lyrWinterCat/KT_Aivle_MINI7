package com.aivle.mini7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.aivle.mini7.repository.BoardRepository;
import com.aivle.mini7.model.Board;
import com.aivle.mini7.dto.BoardDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	// 게시글 목록 페이징 처리
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	public void saveBoard(String title, String content, String username, String password) {
		// All-Args 생성자로 엔티티 생성
		Board board = new Board(
				null, // boardId는 자동 생성
				title,
				content,
				username,
				password,
				null, // createTime은 @CreationTimestamp로 자동 설정
				null, // updateTime은 @UpdateTimestamp로 자동 설정
				new ArrayList<>()
		);
		boardRepository.save(board);
	}

	public void updateBoard(Long boardId, BoardDto.Update updateDto) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
		board.setTitle(updateDto.getTitle());
		board.setContent(updateDto.getContent());
		board.setUpdateTime(LocalDateTime.now());
		boardRepository.save(board);
	}


}
