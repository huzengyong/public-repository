package com.example.service;

import com.example.aliases.Stu_subject;
import com.example.mapper.Stu_subjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Stu_SubjectService {

    @Resource
    Stu_subjectMapper stu_sub_mapper;

    public List<Stu_subject> getByStuNo(int stuNo){
        return stu_sub_mapper.getByStuNo(stuNo);
    }

    public List<Stu_subject> getBySubject(String subject){
        return stu_sub_mapper.getAllStuBySubject(subject);
    }

    public boolean contain(String stuNo,String subject){
        Stu_subject signal = stu_sub_mapper.getOne(Integer.parseInt(stuNo),subject);
        if (signal!=null)
            return true;
        else return false;
    }

    public void insert(String stuNo,String subject){
        stu_sub_mapper.insertSubject(Integer.parseInt(stuNo),subject);
    }

    public void updateGrade(String stuNo,String subject,String grade){
        stu_sub_mapper.changeGrade(grade,Integer.parseInt(stuNo),subject);
    }

    public void removeStu(String stuNo){
        stu_sub_mapper.deleteStu(Integer.parseInt(stuNo));
    }
}
