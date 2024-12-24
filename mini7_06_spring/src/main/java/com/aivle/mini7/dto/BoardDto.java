package com.aivle.mini7.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // BoardDto 기본 생성자만 유지
public class BoardDto {

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
