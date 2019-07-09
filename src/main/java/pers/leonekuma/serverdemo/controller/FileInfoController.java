package pers.leonekuma.serverdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.leonekuma.serverdemo.entity.FileInfo;
import pers.leonekuma.serverdemo.repository.FileInfoRepository;
import pers.leonekuma.serverdemo.repository.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FileInfoController {
    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/upload_video")
    public Map uploadVideo(
            @RequestParam("username")String userName,
            @RequestParam("filetitle")String fileTitle,
            @RequestParam("videostr")String videoStr
    )throws Exception{
        System.out.println("进入upload_video方法");
        byte[] videoBytes = Base64.getDecoder().decode(videoStr);
        Map resultMap = new HashMap();
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmm");
        String dateStr=ft.format(dNow);
        String absoluteDir="/home/leonekuma/tomcat9/apache-tomcat-9.0.21/webapps/CyberClass2077/";
        String dir="videos/"
                +userName+"/"
                +dateStr;
        File videoDir=new File(absoluteDir+dir);
        if (!videoDir.exists()) {
            //如果该路径不存在，则创造路径
            videoDir.mkdirs();
            System.out.println("创造路径");
        }
        String videoName=fileTitle.concat(".mp4");
        File videoFile=new File(videoDir,videoName);
        videoFile.createNewFile();
        InputStream fis = new ByteArrayInputStream(videoBytes);
        FileOutputStream fos = new FileOutputStream(videoFile);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = fis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.close();
        fis.close();
        System.out.println("接收用户上传视频结束");
        FileInfo fileInfo=new FileInfo();
        SimpleDateFormat ft2 = new SimpleDateFormat ("yyyy-MM-dd-hh-mm");
        fileInfo.setUploadTime(ft2.format(dNow));
        fileInfo.setUploadUserName(userName);
        fileInfo.setFileTitle(fileTitle);
        fileInfo.setFileUrl(dir);
        fileInfo.setFileType("video");
        fileInfoRepository.save(fileInfo);
        resultMap.put("isUploadFileSuccessful",true);
        return resultMap;
    }
}
