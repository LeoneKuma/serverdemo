package pers.leonekuma.serverdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.leonekuma.serverdemo.entity.Dynamic;
import pers.leonekuma.serverdemo.repository.DynamicRepository;
import pers.leonekuma.serverdemo.repository.PictureRepository;
import pers.leonekuma.serverdemo.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class DynamicController {
    @Autowired
    private DynamicRepository dynamicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private PictureController pictureController;
    @PostMapping(value = "send_dynamic")
    public Map sendDynamic(
            @RequestParam("username")String userName,
            @RequestParam("dynamic")String dynamicStr,
            @RequestParam("bitmap")String bitmapStr

    ) throws Exception{
        //首先new一个dynamic，保存进repository，然后根据候选键（userName和date）找出
        //刚才存进去的dynamic的ID，最后调用pictureController的方法将图片存进去。
        //返回值是包含boolean变量isSendDynamicSuccessful的HashMap
        System.out.println("进入send_dynamic");
        Map resultMap=new HashMap();
        //将获取的动态转为Dynamic类
        Dynamic dynamic =JSON.parseObject(dynamicStr,Dynamic.class);
        System.out.println("获取的dynamic其用户名为： "+dynamic.getUserName());
        dynamic.setCommentNum(0);
        dynamicRepository.save(dynamic);
        //保存后自动生成一个ID,这时将ID找出来
        Integer dynamicId=dynamicRepository.findByUserNameAndDate(dynamic.getUserName(),dynamic.getDate()).getDynamicId();
        System.out.println("刚才保存的的动态的ID是"+dynamicId);
        //将图片保存到pictureRepository中去。
        Map picSaveResultMap=new HashMap();
        picSaveResultMap=pictureController.upload_picture(dynamic.getUserName(),"dynamic",dynamicId,bitmapStr);
        if (!picSaveResultMap.containsKey("isSavePictureSuccessful")){
            resultMap.put("isSendDynamicSuccessful",false);
            System.out.println("储存图片失败");
            return resultMap;
        }
        else {

            System.out.println("存储图片成功");
            resultMap.put("isSendDynamicSuccessful",true);
            return resultMap;
        }


    }
}
