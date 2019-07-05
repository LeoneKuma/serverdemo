package pers.leonekuma.serverdemo.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.leonekuma.serverdemo.entity.User;
import pers.leonekuma.serverdemo.entity.UserInfo;
import pers.leonekuma.serverdemo.repository.UserInfoRepository;
import pers.leonekuma.serverdemo.repository.UserRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;

@RestController
public class SettingsController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostMapping(value = "/logout")
    public Map logout(@RequestParam("username")String userNameStr){
        System.out.println("Logout事件");
        Map logoutResultMap=new HashMap();
        String userName=JSON.parseObject(userNameStr,String.class);
        User user=userRepository.findByUserName(userName);
        if (user!=null){
            System.out.println("退出登录成功");
            user.setLoginState(false);
            logoutResultMap.put("isLogoutSuccessful",true);
            userRepository.save(user);
            return logoutResultMap;
        }
        else {
            System.out.println("退出登录失败");
            logoutResultMap.put("isLogoutSuccessful",false);
            return logoutResultMap;
        }
    }
    @PostMapping(value = "/update_password")
    public Map updatePassword(@RequestParam("user")String userStr){
        System.out.println("updatePassword事件");
        Map updatePasswordResultMap=new HashMap();
        User userObj=JSON.parseObject(userStr,User.class);
        User user=userRepository.findByUserName(userObj.getUserName());
        if(user!=null){
            System.out.println("修改成功");
            user.setPassWord(userObj.getPassWord());
            userRepository.save(user);
            updatePasswordResultMap.put("isUpdatePasswordSuccessful",true);
            return updatePasswordResultMap;
        }
        else{
            System.out.println("找不到用户名，修改失败");
            updatePasswordResultMap.put("isUpdatePasswordSuccessful",false);
            return updatePasswordResultMap;
        }
    }
    @PostMapping(value = "/update_userinfo")
    public Map updateUserInfo(@RequestParam("userinfo")String userInfoStr){
        System.out.println("update_userinfo事件");
        Map updateResultMap=new HashMap();
        UserInfo userInfoObj=JSON.parseObject(userInfoStr,UserInfo.class);

        User user=userRepository.findByUserName(userInfoObj.getUserName());
        if(user!=null){
            //修改成功
            System.out.println("修改成功");
            userInfoRepository.save(userInfoObj);
            updateResultMap.put("isUpdateUserInfoSuccessful",true);
            return updateResultMap;
        }
        else {
            System.out.println("找不到用户名，修改失败");
            updateResultMap.put("isUpdateUserInfoSuccessful",false);
            return updateResultMap;
        }
    }
    @PostMapping(value = "/get_userinfo")
    public UserInfo getUserInfo(@RequestParam("username")String userNameStr){
        System.out.println("get_userinfo事件");
        String userName=JSON.parseObject(userNameStr,String.class);
        UserInfo userInfo=userInfoRepository.findByUserName(userName);
        if (userInfo==null){
            //用户第一次进入这个界面，进行初始化
            userInfo= new UserInfo();
            userInfo.setUserName(userName);
            userInfo.setGender("保密");
            userInfo.setNickName("昵称");
            userInfo.setBirthDate("1989-06-04");
            userInfo.setPortrait("");
            userInfo.setFolloweeNum(0);//关注的人的数量
            userInfo.setFollowerNum(0);//粉丝数量
            userInfoRepository.save(userInfo);
            return userInfo;

        }
        else{
            //直接返回找到的用户信息设置
            return userInfo;
        }


    }
}
