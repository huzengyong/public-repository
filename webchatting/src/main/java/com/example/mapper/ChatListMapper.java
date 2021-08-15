package com.example.mapper;

import com.example.aliases.ChatList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChatListMapper {
    @Insert("insert into chatlist(chatNo,chatMessage,chatTime,sendNo,receiveNo) values(#{chatNo},#{chatMessage},#{chatTime},#{sendNo},#{receiveNo})")
    void insertChat(ChatList chatlist);
    
    @Select("select * from chatlist where chatNo=#{chatNo}")
    List<ChatList> getChat(int chatNo);

    @Delete("delete from chatlist where chatNo=#{chatNo}")
    void deleteChat(int chatNo);
}
