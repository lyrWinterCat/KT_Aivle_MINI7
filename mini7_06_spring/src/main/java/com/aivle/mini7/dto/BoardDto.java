package com.aivle.mini7.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor // BoardDto 기본 생성자만 유지
@AllArgsConstructor
public class BoardDto {
	private Long boardId;
	private String title;
	private String content;
	private String createTime;
	private String updateTime;
	private String password;
	private String username;
	
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Post {
        private String title;
        private String content;

        // 명시적으로 전체 필드 생성자 정의
        public Post(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
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
