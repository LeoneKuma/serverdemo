package pers.leonekuma.serverdemo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pers.leonekuma.serverdemo.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    public List<Comment> findByPublisherName(String publisherName);
    public List<Comment> findByDynamicId(Integer dynamicId,Pageable p);
    public List<Comment> findByCitedCommentId(Integer citedCommentId, Pageable p);
}
