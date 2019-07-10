package pers.leonekuma.serverdemo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.*;

@RestController
public class FileInfoController {
    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PictureController pictureController;

    @PostMapping(value = "/upload_video")
    public Map uploadVideo(
            @RequestParam("username")String userName,
            @RequestParam("filetitle")String fileTitle,
            @RequestParam("videostr")String videoStr,
            @RequestParam("tag")String tagStr,
            @RequestParam("bitmap")String bitmapStr
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
        fileInfo.setTag(tagStr);
        fileInfoRepository.save(fileInfo);
        //获取刚存储的视频的ID
        Integer fileInfoId=fileInfoRepository.findByUploadUserNameAndUploadTime(userName,fileInfo.getUploadTime()).getFileId();
        pictureController.upload_picture(userName,fileInfo.getFileType(),fileInfoId,bitmapStr);
        resultMap.put("isUploadFileSuccessful",true);
      //  resultMap.put("fileId",fileInfoId);
        return resultMap;
    }
    @PostMapping(value = "/get_video_list")
    public Map getVideos(
            @RequestParam("username")String userName,
            @RequestParam("pattern")String pattern ,
            @RequestParam("tag")String tag,
            @RequestParam("searchusername")String searchUserName
    )throws Exception{
        //根据用户名获得他应该获取的FileInfo 的list
        //根据这个list来获取每个视频对应的URL的
        //根据这个list和username来获取一个boolean的list
        //返回结果的map就包含了四个字段：
        //1. isGetVideosSuccessful
        //2. List<FileInfo>的JSON
        //3. List<String>的JSON(url)
        //4. List<String>的JSON(boolean)
        List<FileInfo> video_list=get_videos_list(userName,searchUserName);
        String video_list_str= JSON.toJSONString(video_list);
        List<String> video_url_list=new ArrayList<String>();
        for (FileInfo item:video_list
             ) {
            String url="http://47.100.99.130:8080/CyberClass2077/"+item.getFileUrl()+"/"
                    +item.getFileTitle()+".mp4";
            video_url_list.add(url);

        }
        String video_url_list_str=JSON.toJSONString(video_url_list);

        //获取username对这个列表的视频的喜欢与否
        //先暂时全置否
        List<Boolean>video_like_list=new ArrayList<Boolean>();
        for(int i=0;i<10;i++){
            video_like_list.add(false);
        }
        String video_like_list_str=JSON.toJSONString(video_like_list);
        String portraitsStr=pictureController.get_video_portraits(video_list);
        Map resultMap=new HashMap();
        resultMap.put("isGetVideosSuccessful",true);
        resultMap.put("videoList",video_list_str);
        resultMap.put("videoUrlList",video_url_list_str);
        resultMap.put("videoLikeList",video_like_list_str);
        resultMap.put("portraitsList",portraitsStr);
        return resultMap;


    }

    @PostMapping(value = "/test_get_videos_list")
    public List<FileInfo> get_videos_list(
            @RequestParam("username") String userName,
            @RequestParam("searchusername")String searchUserName) {
        //先简单的获取一些视频
        //之后可以根据用户的喜好来筛选一些视频
        Sort s = new Sort(Sort.Direction.DESC, "fileId");
        Pageable p = PageRequest.of(0, 10, s);
        List<FileInfo> videos_list = new ArrayList<FileInfo>();
        if (searchUserName.equals("none")) {
            videos_list = fileInfoRepository.findAll(p).getContent();
        } else {
            videos_list = fileInfoRepository.findByUploadUserName(searchUserName, p);
        }

        return videos_list;
    }
    @PostMapping(value="/get_video_picture")
    public Map getVideoPicture(
            @RequestParam("fileId")Integer fileId
    )throws Exception{
        String picStr=pictureController.get_video_videoPicById(fileId);
        Map resultMap=new HashMap();
        if(picStr.length()==0){
            resultMap.put("isGetVideoPicSuccessful",false);
            return resultMap;
        }
        resultMap.put("isGetVideoPicSuccessful",true);
        resultMap.put("bitmapStr",picStr);
        resultMap.put("fileId",fileId);
        return resultMap;

    }

}
