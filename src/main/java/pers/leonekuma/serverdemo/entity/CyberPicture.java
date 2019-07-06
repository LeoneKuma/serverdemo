package pers.leonekuma.serverdemo.entity;

import javax.persistence.*;

@Entity
@Table(name ="tb_cyber_picture")
public class CyberPicture {
    @Id
    @Column(name = "picId",length = 10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer picId;
    @Column(name = "picType",length = 10)
    private String picType;
    @Column(name="uploadUserName",length = 10)
    private String uploadUserName;
    @Column(name = "relateId",length = 10)
    private Integer relateId;
    @Column(name = "urlPath",length = 255)
    private String UrlPath;

    public Integer getPicId() {
        return picId;
    }

    public Integer getRelateId() {
        return relateId;
    }

    public String getPicType() {
        return picType;
    }

    public String getUploadUserName() {
        return uploadUserName;
    }

    public String getUrlPath() {
        return UrlPath;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public void setUrlPath(String urlPath) {
        UrlPath = urlPath;
    }

}
