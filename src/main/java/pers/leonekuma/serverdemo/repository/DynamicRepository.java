package pers.leonekuma.serverdemo.repository;

import org.omg.DynamicAny.DynAny;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pers.leonekuma.serverdemo.entity.Dynamic;

import java.util.List;

public interface DynamicRepository extends JpaRepository<Dynamic,Integer> {
    public List<Dynamic> findByUserName(String userName);
    public Dynamic findByUserNameAndDate(String userName,String date);
    //public Page<Dynamic> findAll(Pageable p);
    public List<Dynamic> findDynamicsByDynamicIdAfter(Integer dynamicId,Pageable p);
}
