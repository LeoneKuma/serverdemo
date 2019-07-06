package pers.leonekuma.serverdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.leonekuma.serverdemo.entity.CyberPicture;

import java.util.List;

public interface PictureRepository extends JpaRepository<CyberPicture,Integer> {
    //获取评论,动态，封面等相关的图，这两个参数可以锁定唯一的图
    public CyberPicture getCyberPictureByPicTypeAndAndRelateId(String picType,Integer relateId);
    //获取用户头像，只允许有一个实体。修改的时候注意，所以
    public CyberPicture getCyberPictureByPicTypeAndUploadUserName(String picType,String uploadUserName);
    //根据用户名获得该用户上传的所有图片，返回一个列表
    public List<CyberPicture> getCyberPicturesByUploadUserName(String uploadUserName);

}
