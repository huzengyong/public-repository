package com.example.mapper;

import com.example.aliases.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {

    @Select("select * from studentes")
    List<Student> getAllStu();

    @Select("select * from studentes where stuNo=#{stuNo}")
    Student getByStuNo(int stuNo);

    @Insert("insert into studentes(stuName,speciality,stuClass,stuAddr) values(#{stuName},#{speciality},#{stuClass},#{stuAddr})")
    void insertStu(String stuName,String speciality,int stuClass,String stuAddr);

    @Select("select last_insert_id()")
    int getLastInsertId();

    @Delete("delete from studentes where stuNo=#{stuNo}")
    void removeStu(int stuNo);

}
