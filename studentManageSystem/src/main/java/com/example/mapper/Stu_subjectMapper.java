package com.example.mapper;

import com.example.aliases.Stu_subject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface Stu_subjectMapper {

    @Select("select * from stu_subject where stuNo=#{stuNo}")
    List<Stu_subject> getByStuNo(int stuNo);

    @Select("select * from stu_subject where subject=#{subject}")
    List<Stu_subject> getAllStuBySubject(String subject);

    @Select("select * from stu_subject where stuNo=#{stuNo} and subject=#{subject}")
    Stu_subject getOne(int stuNo,String subject);

    @Update("update stu_subject set grade=#{grade} where stuNo=#{stuNo} and subject=#{subject}")
    void changeGrade(String grade,int stuNo,String subject);

    @Insert("insert into stu_subject(stuNo,subject) values(#{stuNo},#{subject})")
    void insertSubject(int stuNo,String subject);

    @Delete("delete from stu_subject where stuNo=#{stuNo} and subject=#{subject}")
    void deleteSubject(int stuNo,String subject);

    @Delete("delete from stu_subject where stuNo=#{stuNo}")
    void deleteStu(int stuNo);
}
