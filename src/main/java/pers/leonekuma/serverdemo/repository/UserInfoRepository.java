package pers.leonekuma.serverdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.leonekuma.serverdemo.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    public UserInfo findByUserName(String userName);
}
