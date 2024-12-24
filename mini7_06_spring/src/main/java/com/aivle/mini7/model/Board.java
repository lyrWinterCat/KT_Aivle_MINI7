package com.aivle.mini7.model;

import com.aivle.mini7.dto.BoardDto;
import jakarta.persistence.*;


import java.util.List;
import java.util.ArrayList;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "boardId", updatable = false)
	private Long boardId;

	@Column(name = "board_title", nullable = false) // DB 컬럼 이름과 매핑)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	// 기본값 설정
	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime createTime;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime updateTime;

	@OneToMany(mappedBy = "board")
	private List<Comment> comments;

    // DTO → Entity 변환 메서드
    public static Board toEntity(BoardDto.Post post) {
        return new Board(null,
				post.getTitle(),
				post.getContent(),
				post.getUsername(),
				post.getPassword(),
				null,
				null,
				new ArrayList<>());
    }
}

