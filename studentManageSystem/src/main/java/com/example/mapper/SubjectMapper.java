package com.example.mapper;

import com.example.aliases.Subject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SubjectMapper {

    @Select("select*from subject")
    List<Subject> getAll();

    @Select("select* from subject where subject=#{subject}")
    Subject getOne(String subject);

    @Insert("insert into subject(subject,teacher) values(#{subject},#{teacher})")
    void insertSubject(String subject,String teacher);



}
