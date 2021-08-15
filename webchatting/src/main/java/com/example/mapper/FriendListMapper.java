package com.example.mapper;

import com.example.aliases.FriendList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FriendListMapper {

    @Insert("insert into friendlist(userNo,friendNo,friendName) values(#{userNo},#{friendNo},#{friendName})")
    void insertFriend(FriendList friend);

    @Select("select*from friendlist where userNo=#{userNo}")
    List<FriendList> getFriendes(int userNo);

    @Select("select * from friendlist where userNo=#{userNo} and friendNo=#{friendNo}")
    FriendList getOne(int userNo,int friendNo);

    @Delete("delete from friendlist where userNo=#{userNo} and friendNo=#{friendNo}")
    void deleteFriend(int userNo,int friendNo);

    @Update("update friendlist set friendName=#{friendName} where userNo=#{useNo}")
    void changeFriendName(int userNo,String friendName);
}
