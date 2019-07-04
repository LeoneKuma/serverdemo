package pers.leonekuma.serverdemo.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.leonekuma.serverdemo.entity.User;
import pers.leonekuma.serverdemo.repository.UserRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
public class SettingsController {
    @Autowired
    private UserRepository userRepository;
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
}
