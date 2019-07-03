package pers.leonekuma.serverdemo.entity;
import javax.persistence.*;

@Entity
@Table(name="tb_user")
public class User {
    @Id
    @Column(name = "userId",length = 10)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String userId;

    @Column(name = "userName",length = 20)
    private String userName;

    @Column(name = "passWord",length = 20)
    private String passWord;

    @Column(name="loginState",length = 1)
    private boolean loginState;

    public User(){}

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord=passWord;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }

    public boolean isLoginState() {
        return loginState;
    }
}
