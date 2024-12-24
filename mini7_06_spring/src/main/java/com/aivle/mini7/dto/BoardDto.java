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
		private String password;
		private String username;

        // 명시적으로 전체 필드 생성자 정의
        public Post(String title,
					String content,
					String password,
					String username) {

            this.title = title;
            this.content = content;
			this.password = password;
			this.username = username;

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
