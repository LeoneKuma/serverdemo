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
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/login")
    public Map findByName(@RequestParam("user")String userStr){
        System.out.println("login请求");
        Map loginResultMap=new HashMap();
        User userObj=JSON.parseObject(userStr,User.class);
        System.out.println(userObj.getUserName());
        User user=userRepository.findByUserName(userObj.getUserName());
        if(user==null){//如果找不到用户名
            System.out.println("找不到用户名");
            loginResultMap.put("isLoginSuccessful",false);
            loginResultMap.put("isAlreadyLogin",false);
            return loginResultMap;
        }
        if (user.getPassWord().equals(userObj.getPassWord())&&user.isLoginState()==false){
            //正常登录成功
            System.out.println("正常登录成功");
            user.setLoginState(true);
            loginResultMap.put("isLoginSuccessful",true);
            loginResultMap.put("isAlreadyLogin",false);
        }
        else if(!user.getPassWord().equals(userObj.getPassWord())&&user.isLoginState()==true){
            //已经登录，重新登录
            System.out.println("重新登录成功");
            user.setLoginState(true);
            loginResultMap.put("isLoginSuccessful",true);
            loginResultMap.put("isAlreadyLogin",true);
        }
        else if(!user.getPassWord().equals(userObj.getPassWord())&&user.isLoginState()==false){
            //密码错误
            System.out.println("密码错误");
            user.setLoginState(false);
            loginResultMap.put("isLoginSuccessful",false);
            loginResultMap.put("isAlreadyLogin",false);
        }

        //在实体表中保存这一项
        userRepository.save(user);
        return loginResultMap;
    }
}
