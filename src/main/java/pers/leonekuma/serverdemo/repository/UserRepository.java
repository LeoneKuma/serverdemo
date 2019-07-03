package pers.leonekuma.serverdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.leonekuma.serverdemo.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String>{
    public User findByUserName(String userName);
    //public User findByUserId(String userId);
}
