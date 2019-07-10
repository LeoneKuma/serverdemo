package pers.leonekuma.serverdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.leonekuma.serverdemo.entity.Dynamic;
import pers.leonekuma.serverdemo.repository.DynamicRepository;
import pers.leonekuma.serverdemo.repository.PictureRepository;
import pers.leonekuma.serverdemo.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class DynamicController {
    @Autowired
    private DynamicRepository dynamicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private PictureController pictureController;
    @PostMapping(value = "/send_dynamic")
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
        dynamic.setLikeNum(0);
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
            resultMap.put("dynamicId",dynamicId);
            bitmapStr=null;
            return resultMap;
        }


    }
    @PostMapping(value = "/get_dynamics")
    public Map getDynamics(
            @RequestParam("username")String userName,
            @RequestParam("gettype")String getType
    )throws Exception{
        System.out.println("进入get_dynamics方法，想获取动态的用户名为："+userName);
        System.out.println("想获取的动态类型为："+getType);
        //根据用户名，获取他应该得到那些动态，即一个动态列表。
        //根据动态列表中每个动态的userName，获取对应的头像，转成String
        //返回的数据应该包括，一个Dynamic数组对应的JSON字符串，
        //一个头像字符串数组对应的JSON字符串
        //一个动态图片字符串数组对应的JSON字符串
        //***********************************
        //获取动态列表
        List<Dynamic> dynamicList=getDynamicList(userName,getType);
        String dynamicsStr=JSON.toJSONString(dynamicList);
        String portraitsStr=pictureController.get_dynamic_portraits(dynamicList);
       // String dynamicPicturesStr=pictureController.get_dynamic_dynamicPic(dynamicList);
        Map resultMap=new HashMap();
        resultMap.put("isGetDynamicsSuccessful",true);
        resultMap.put("dynamics",dynamicsStr);
        resultMap.put("portraits",portraitsStr);
        System.out.println("动态列表字符串的长度为"+dynamicsStr.length());
        //resultMap.put("dynamicPics",dynamicPicturesStr);
        return resultMap;

    }
    public List<Dynamic>getDynamicList(String userName,String getType){
        switch (getType){
            case "square":{
                Sort s=new Sort(Sort.Direction.DESC, "dynamicId");
                Pageable p=PageRequest.of(0,10,s);
                //List<Dynamic> dynamicList=dynamicRepository.findFirst10OrderByDynamicId();
                List<Dynamic> dynamicList=dynamicRepository.findAll(p).getContent();
                for (Dynamic item:dynamicList
                     ) {
                    System.out.println(item.getDynamicId());

                }
               // dynamicRepository.findAll(p).getSize();
                System.out.println("找到"+dynamicList.size()+"个动态");
                return dynamicList;
            }
            default:{
                return null;
            }
        }
    }
    @PostMapping(value = "/test_page")
    public List<Dynamic>testGetDynamicList(
            @RequestParam("username") String userName,
            @RequestParam("gettype") String getType)
    {
        switch (getType){
            case "square":{
                Sort s=new Sort(Sort.Direction.DESC, "dynamicId");
                Pageable p=PageRequest.of(0,10,s);
                //List<Dynamic> dynamicList=dynamicRepository.findFirst10OrderByDynamicId();
                List<Dynamic> dynamicList=dynamicRepository.findAll(p).getContent();
               // List<Dynamic> dynamicList=dynamicRepository.findAll();
                //dynamicRepository.findAll(p).getSize();
                System.out.println("找到"+dynamicList.size()+"个动态");
                return dynamicList;
            }
            case "spId":{
                Sort s=new Sort(Sort.Direction.DESC, "dynamicId");
                Pageable p=PageRequest.of(0,10,s);
                List<Dynamic> dynamicList=dynamicRepository.findDynamicsByDynamicIdAfter(10,p);
                return dynamicList;
            }
            default:{
                return null;
            }
        }
    }
    @PostMapping(value = "/get_dynamic_picture")
    public Map getDynamicPicture(
            @RequestParam("dynamicId" )Integer dynamicId
    ) throws Exception{
        System.out.println("获取动态图片");
        String picStr=pictureController.get_dynamic_dynamicPicById(dynamicId);
        Map resultMap=new HashMap();
        if(picStr.length()==0){
            resultMap.put("isGetDynamicPicSuccessful",false);
            return resultMap;
        }
        resultMap.put("isGetDynamicPicSuccessful",true);
        resultMap.put("bitmapStr",picStr);
        resultMap.put("dynamicId",dynamicId);
        System.out.println("获取动态图片结束");
        return resultMap;

    }

}
