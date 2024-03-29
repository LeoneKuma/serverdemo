package pers.leonekuma.serverdemo.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.leonekuma.serverdemo.entity.Student;
import pers.leonekuma.serverdemo.repository.StudentRepository;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "/hello")
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }
    @PostMapping(value = "/insert")
    public List<Student> insertInto(@RequestParam("name")String name, @RequestParam("age") String age){
        System.out.println("进入插入数据方法！");
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        studentRepository.save(student);

        return studentRepository.findAll();
    }

    @PostMapping(value = "/find_by_id")
    public Student findByID(@RequestParam("id")Integer id){
        return studentRepository.findById(id).get();
    }

    @PostMapping(value = "/find_by_name")
    public  List<Student> findByName(@RequestParam("name") String name){
        return studentRepository.findByName(name);
    }

    @PostMapping(value = "/delete_by_name")
    public List<Student> deleteByName(String name){
        System.out.println("进入delete_by_name（）方法");
        List<Student> students = studentRepository.findByName(name);
        if(null != students){
            for(int i = 0;i<students.size();i++){
                int id = students.get(i).getId();
                studentRepository.deleteById(id);
            }
        }
        return studentRepository.findAll();
    }

    @RequestMapping(value = "add_more_students")
    public List<Student> addMoreStudents(@RequestParam("students") String str){
        System.out.println("add_more_students（）方法");
        List<Student> studentList = (List<Student>) JSONArray.parseArray(str,Student.class);
        if(null != studentList){
            for(int i = 0;i<studentList.size();i++){
                insertInto(studentList.get(i).getName(),studentList.get(i).getAge());
            }
        }
        return studentRepository.findAll();
    }

}
