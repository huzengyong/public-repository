package com.example.mapper;

import com.example.aliases.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Select("select * from user where userNo=#{userNo}")
    User getByUserNo(int userNo);

    @Select("select * from user")
    List<User> getAll();

    @Select("select userNo from user")
    int[] getAllUserNo();

    @Select("select * from user where username=#{username}")
    User getByUsername(String username);

    @Insert("insert into user(username,password) values(#{username},#{password})")
    void insertOne(String username,String password);

    @Update("update user set password=#{password} where userNo=#{userNo}")
    void changePassword(String userNo,String password);

    @Delete("delete from user where userNo=#{userNo}")
    void deleteByUserNo(int userNo);


}
