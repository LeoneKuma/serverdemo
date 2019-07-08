package pers.leonekuma.serverdemo.entity;

import javax.persistence.*;

@Entity
@Table(name="tb_file")
public class FileInfo {
    @Id
    @Column(name = "fileId",length = 10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer fileId;
    @Column(name = "fileUrl",length = 255)
    String fileUrl;
    @Column(name = "fileType",length = 20)
    String fileType;
    @Column(name = "commmentNum",length = 10)
    Integer commmentNum;
    @Column(name = "uploadUserName",length = 20)
    String uploadUserName;
    @Column(name = "uploadTime",length = 20)
    String uploadTime;
    @Column(name = "examineAdminName",length = 20)
    String examineAdminName;
    @Column(name = "examineTime",length = 10)
    String examineTime;
    @Column(name = "examineResult",length = 20)
    String examineResult;
    @Column(name = "likeNum",length = 10)
    Integer likeNum;
    @Column(name = "fileTitle",length = 255)
    String fileTitle;


    public String getUploadUserName() {
        return uploadUserName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public Integer getCommmentNum() {
        return commmentNum;
    }

    public String getExamineAdminName() {
        return examineAdminName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public String getExamineResult() {
        return examineResult;
    }

    public String getExamineTime() {
        return examineTime;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public void setCommmentNum(Integer commmentNum) {
        this.commmentNum = commmentNum;
    }

    public void setExamineAdminName(String examineAdminName) {
        this.examineAdminName = examineAdminName;
    }

    public void setExamineTime(String examineTime) {
        this.examineTime = examineTime;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setExamineResult(String examineResult) {
        this.examineResult = examineResult;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
}

