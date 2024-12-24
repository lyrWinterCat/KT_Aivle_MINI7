package com.aivle.mini7.repository;

import com.aivle.mini7.model.Board;
import com.aivle.mini7.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	// 특정 게시글의 댓글 조회
	List<Comment> findByBoard_BoardId(Board boardId);
}
