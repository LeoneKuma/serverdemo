package pers.leonekuma.serverdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.leonekuma.serverdemo.entity.FileInfo;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo,Integer> {
   public List<FileInfo>findByUploadUserName(String uploadUserName);
   public FileInfo findByUploadUserNameAndUploadTime(String userName,String date);
   public List<FileInfo>findByUploadUserName(String uploadUserName, Pageable p);

}
