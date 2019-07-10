package pers.leonekuma.serverdemo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tb_dynamic")
public class Dynamic implements Serializable {
    @Id
    @Column(name = "dynamicId",length = 10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dynamicId;
    @Column(name = "userName",length = 20)
    private String userName;
    @Column(name = "content",length = 5000)
    private String content;
    @Column(name = "date",length = 20)
    private String date;
    @Column(name = "commentNum",length = 10)
    private Integer commentNum;
    @Column(name="likeNum",length = 10)
    private Integer likeNum;

    public String getUserName() {
        return userName;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

}
