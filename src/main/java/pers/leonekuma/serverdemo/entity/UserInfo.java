package pers.leonekuma.serverdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_user_info")
public class UserInfo {
    @Id
    @Column(name = "userName",length = 20)
    private String userName;
    @Column(name = "nickName",length = 20)
    private String nickName;
    @Column(name = "gender",length = 2)
    private String gender;
    @Column(name = "portrait",length = 255)
    private String portrait;
    @Column(name = "birthDate",length = 10)
    private String birthDate;
    @Column(name = "followeeNum",length = 11)
    private Integer followeeNum;
    @Column(name = "followerNum",length = 11)
    private Integer followerNum;

    public UserInfo(){}

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getGender() {
        return gender;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Integer getFolloweeNum() {
        return followeeNum;
    }

    public Integer getFollowerNum() {
        return followerNum;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public void setFolloweeNum(Integer followeeNum) {
        this.followeeNum = followeeNum;
    }

    public void setFollowerNum(Integer followerNum) {
        this.followerNum = followerNum;
    }
}
