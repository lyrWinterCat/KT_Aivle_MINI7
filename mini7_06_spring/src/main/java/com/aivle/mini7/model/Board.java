package com.aivle.mini7.model;

import com.aivle.mini7.dto.BoardDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(name = "board_title", nullable = false) // DB 컬럼 이름과 매핑
    private String title;

    @Column(nullable = false)
    private String content;

    // DTO → Entity 변환 메서드
    public static Board toEntity(BoardDto.Post post) {
        return new Board(null, post.getTitle(), post.getContent());
    }
}
