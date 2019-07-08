package pers.leonekuma.serverdemo.controller;

import com.alibaba.fastjson.JSON;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.leonekuma.serverdemo.entity.CyberPicture;
import pers.leonekuma.serverdemo.repository.PictureRepository;
import pers.leonekuma.serverdemo.repository.UserRepository;

import java.io.*;
import java.util.*;

@RestController
public class PictureController {
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/set_portrait_picture")
    public Map uploadPortrait(@RequestParam("username")String userName,@RequestParam("portrait")String picStr) throws Exception {
        //@RequestParam("portrait")String picStr
        System.out.println("进入set_portrait_picture");
        //List<Byte> picBytes=JSON.parseArray(picStr,Byte.class);

        byte[] picBytes = Base64.getDecoder().decode(picStr);
        Map resultMap = new HashMap();
        String dir;
        CyberPicture pic = pictureRepository.getCyberPictureByPicTypeAndUploadUserName("portrait", userName);
        if (pic == null) {
            //如果用户没设置过
            pic = new CyberPicture();
            pic.setUploadUserName(userName);
            pic.setPicType("portrait");
            dir = "pictures/"
                    + pic.getPicType()
                    + "/"
                    + pic.getUploadUserName();
            pic.setUrlPath(dir);
            pictureRepository.save(pic);
            resultMap.put("isSetPortraitSuccessful", true);

        } else {
            //如果用户设置过
            dir = pic.getUrlPath();
            resultMap.put("isSetPortraitSuccessful", true);
        }
        //**************************
        //文件操作

        File picDir = new File(dir);
        if (!picDir.exists()) {
            //如果该路径不存在，则创造路径
            picDir.mkdirs();
            System.out.println("创造路径");
        }
        //设置图片名称
        String picName = pic.getUploadUserName().concat(".cyberpic");
        //获取文件输出流
        File picFile = new File(picDir, picName);
        /*if (picFile.exists()){
            //如果存在，则删除它再重建
            System.out.println("重建文件");
            picFile.delete();
            picFile.createNewFile();
        }
        else {
            //如果不存在，则创建它
            System.out.println("新建文件");
            picFile.createNewFile();
        }*/
        //直接重建就行了，不需要先delete
       /* System.out.println("查看父路径:" + picFile.getParent());
        System.out.println("查看路径:" + picFile.getPath());
        System.out.println("查看绝对路径:" + picFile.getAbsoluteFile());*/

        //System.out.println("查看父路径:"+picFile.getParent());
        //picFile.getParentFile().mkdirs();
        picFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(picFile);
        //将字节数列表形式的picBytes全部写入fos中（写进文件）
        System.out.println(picBytes.length);
        int i = 1;
        /*for (Byte picByte:picBytes) {
            fos.write(picByte);
            //System.out.println(i++);

        }*/
        InputStream fis = new ByteArrayInputStream(picBytes);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = fis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
            fos.close();
            fis.close();
            System.out.println("结束");
            return resultMap;
    }
    @PostMapping(value = "/get_portrait_picture")
    public Map getPortrait (
            @RequestParam(value = "username")String userNameStr
    )throws Exception{
        String userName=JSON.parseObject(userNameStr,String.class);
        System.out.println("/get_portrait_picture");
        Map resultMap=new HashMap();
       // System.out.println(userName);
       // System.out.println();
        CyberPicture cyberPicture=pictureRepository.getCyberPictureByPicTypeAndUploadUserName("portrait",userName);
        String picDir;
        String picName;
        if (cyberPicture==null){
            //用户没设置过头像
            //System.out.println(pictureRepository.findAll().size());
            /*String testName=pictureRepository.findAll().get(0).getUploadUserName();
            String testType=pictureRepository.findAll().get(0).getPicType();
            System.out.println(testType+" : "+testName);
            System.out.println(testType+" : "+userName);
            System.out.println(testName.equals(userName));
            System.out.println(testType.equals("portrait"));*/
            //resultMap.put("picStr","null");
            //resultMap.put("isGetPortraitSuccessful",false);
            //return resultMap;
            picDir="pictures/portrait";
            picName="defaultportrait.png";
        }
        else {
            picDir=cyberPicture.getUrlPath();
            picName=cyberPicture.getUploadUserName().concat(".cyberpic");
        }

        File picFile=new File(picDir,picName);
        //读取文件
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) picFile.length());
        BufferedInputStream in = null;

        in = new BufferedInputStream(new FileInputStream(picFile));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }

        //String picString = Base64.encodeBase64String(bos.toByteArray());
        String picString=java.util.Base64.getEncoder().encodeToString(bos.toByteArray());
        resultMap.put("isGetPortraitSuccessful",true);
        resultMap.put("picStr",picString);
        in.close();
        bos.close();
        return resultMap;




    }


}