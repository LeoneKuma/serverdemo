package pers.leonekuma.serverdemo.repository;
import pers.leonekuma.serverdemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


    public interface StudentRepository extends JpaRepository<Student,Integer>  {
    public  List<Student> findByName(String name);

    public List<Student> deleteByName(String name);
}
