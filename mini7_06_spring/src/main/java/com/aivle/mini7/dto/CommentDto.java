package com.aivle.mini7.dto;

import com.aivle.mini7.model.Comment;
import lombok.*;

@Data
@ToString
public class CommentDto {
	private Long commentId;
	private String content;
	private String createTime;
	private String password;
	private String username;
	private Long boardId;

	@Data
	public static class Post {
		private String content;
		private String password;
		private String username;

		// 명시적으로 전체 필드 생성자 정의
		public Post(String content,
					String password,
					String username) {

			this.content = content;
			this.password = password;
			this.username = username;
		}
	}
}