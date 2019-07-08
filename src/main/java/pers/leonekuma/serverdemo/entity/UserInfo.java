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
    @Column(name = "birthDate",length = 10)
    private String birthDate;
    @Column(name = "followeeNum",length = 11)
    private Integer followeeNum;
    @Column(name = "followerNum",length = 11)
    private Integer followerNum;
    @Column(name ="lastCheckinDate",length = 10)
    private String lastCheckinDate;
    @Column(name = "isTodayCheckin",length = 1)
    private boolean isTodayCheckin=false;
    @Column(name ="checkinTotalDays",length = 10)
    private Integer checkinTotalDays=0;


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



    public String getBirthDate() {
        return birthDate;
    }

    public Integer getFolloweeNum() {
        return followeeNum;
    }

    public Integer getFollowerNum() {
        return followerNum;
    }

    public String getLastCheckinDate() {
        return lastCheckinDate;
    }

    public boolean isTodayCheckin() {
        return isTodayCheckin;
    }

    public Integer getCheckinTotalDays() {
        return checkinTotalDays;
    }

    public void setCheckinTotalDays(Integer checkinTotalDays) {
        this.checkinTotalDays = checkinTotalDays;
    }

    public void setLastCheckinDate(String lastCheckinDate) {
        this.lastCheckinDate = lastCheckinDate;
    }

    public void setTodayCheckin(boolean todayCheckin) {
        isTodayCheckin = todayCheckin;
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


    public void setFolloweeNum(Integer followeeNum) {
        this.followeeNum = followeeNum;
    }

    public void setFollowerNum(Integer followerNum) {
        this.followerNum = followerNum;
    }
}
