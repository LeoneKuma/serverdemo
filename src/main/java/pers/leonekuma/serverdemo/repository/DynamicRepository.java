package pers.leonekuma.serverdemo.repository;

import org.omg.DynamicAny.DynAny;
import org.springframework.data.jpa.repository.JpaRepository;
import pers.leonekuma.serverdemo.entity.Dynamic;

import java.util.List;

public interface DynamicRepository extends JpaRepository<Dynamic,Integer> {
    public List<Dynamic> findByUserName(String userName);
    public Dynamic findByUserNameAndDate(String userName,String date);
}
