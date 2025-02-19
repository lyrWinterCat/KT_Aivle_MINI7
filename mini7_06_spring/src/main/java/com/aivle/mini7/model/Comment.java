package com.aivle.mini7.model;

import com.aivle.mini7.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String username;

	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id", nullable = false)
	@JsonBackReference
	private Board board;

	// DTO → Entity 변환 메서드
	public static Comment toEntity(CommentDto.Post post) {
		return new Comment(null,
							post.getContent(),
							post.getPassword(),
							post.getUsername(),
							null,
							null);
	}
}