package pers.leonekuma.serverdemo.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.leonekuma.serverdemo.entity.User;
import pers.leonekuma.serverdemo.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;


@RestController
public class SignupController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/signup")
    public Map addUser(@RequestParam("user")String userStr){
        System.out.println("Signup请求");
        Map loginResultMap=new HashMap();
        User userObj=JSON.parseObject(userStr,User.class);
        User user=userRepository.findByUserName(userObj.getUserName());
        if(user==null){//该用户名未注册，注册成功
            System.out.println("注册成功");
            userObj.setLoginState(false);
            userRepository.save(userObj);
            loginResultMap.put("isSignupSuccessful",true);
            return loginResultMap;
        }
        else {
            System.out.println("注册失败，用户已存在");
            loginResultMap.put("isSignupSuccessful",false);
            return loginResultMap;
        }

    }
    @PostMapping(value = "signup_name_check")
    public Map checkName(@RequestParam("username")String userNameStr){
        System.out.println("sigup_name_check请求");
        Map nameCheckResultMap=new HashMap();
        String userName=JSON.parseObject(userNameStr,String.class);
        User user=userRepository.findByUserName(userName);
        if(user==null){//该用户名未注册，可以使用这个用户名
            System.out.println("用户名检测完毕，可以使用");
            nameCheckResultMap.put("isNameExist",false);
            return nameCheckResultMap;
        }
        else {
            //该用户名已注册，不可以使用这个用户名
            System.out.println("用户名检测完毕，不可以使用");
            nameCheckResultMap.put("isNameExist",true);
            return nameCheckResultMap;
        }
    }
}
