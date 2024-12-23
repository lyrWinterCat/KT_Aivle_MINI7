package com.aivle.mini7.dto;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Long commentId;
	private String content;
	private String createTime;
	private String password;
	private String username;
	private Long boardId;
}