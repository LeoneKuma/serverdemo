package pers.leonekuma.serverdemo.entity;

import javax.persistence.*;

@Entity
@Table(name ="tb_comment")
public class Comment {
    @Id
    @Column(name = "commentId",length = 10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer commentId;

    @Column(name = "publisherName",length = 20)
    String publisherName;

    @Column(name = "dynamicId",length = 10)
    Integer dynamicId;

    @Column(name = "content",length = 5000)
    String content;

    @Column(name = "commentTime",length = 20)
    String commentTime;

    @Column(name = "citedCommentId",length = 10)
    String citedCommentId;

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCitedCommentId(String citedCommentId) {
        this.citedCommentId = citedCommentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getContent() {
        return content;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public String getCitedCommentId() {
        return citedCommentId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public String getPublisherName() {
        return publisherName;
    }
}
