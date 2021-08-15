package com.example.service;

import com.example.aliases.Student;
import com.example.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class StuManageService {

    @Resource
    StudentMapper stuMapper;

    public List<Student> getAllStu(){
        return stuMapper.getAllStu();
    }

    public Student getByStuNo(String stuNo){
        return stuMapper.getByStuNo(Integer.parseInt(stuNo));
    }

    public int insertStu(String stuName,String speciality,int stuClass,String stuAddr){
        stuMapper.insertStu(stuName,speciality,stuClass,stuAddr);
        return stuMapper.getLastInsertId();
    }

    public boolean stuLoginCheck(String stuNo,String password){
        Student stu =stuMapper.getByStuNo(Integer.parseInt(stuNo));
        if(stu!=null && stu.getPassword().equals(password)){
            return true;
        }
        else return false;
    }

    public void removeStu(String stuNo){
        stuMapper.removeStu(Integer.parseInt(stuNo));
    }
}
