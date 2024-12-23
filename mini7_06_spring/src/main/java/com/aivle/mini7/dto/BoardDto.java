package com.aivle.mini7.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	private Long boardId;
	private String boardTitle;
	private String boardContent;
	private String createTime;
	private String updateTime;
	private String password;
	private String username;
}

//public class BoardDto{
//	@Getter
//	@Setter
//	@ToString
//	@NoArgsConstructor
//	@AllArgsConstructor
//	public static class Post {
//		private String title;
//		private String content;
//	}
//}