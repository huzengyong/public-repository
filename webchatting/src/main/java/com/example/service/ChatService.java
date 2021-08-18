package com.example.service;

import com.alibaba.fastjson.JSONObject;
import com.example.aliases.ChatList;
import com.example.mapper.ChatListMapper;
import com.example.mapper.FriendListMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class ChatService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    ChatListMapper chatListMapper;

    @Resource
    FriendListMapper friendListMapper;

    public JSONObject chatList(String userNo,String friendNo){
        JSONObject json = new JSONObject();
        List<ChatList> chatList = chatListMapper.getChat(getChatNo(userNo,friendNo));
        int i=1;
        for (ChatList chat:chatList){
            json.put(Integer.toString(i++),json.toJSONString(chat));
        }
        return json;
    }

    public void chatting(String userNo,String friendNo,String message){
        int userNo_int = Integer.parseInt(userNo);
        int friendNo_int = Integer.parseInt(friendNo);
        if (friendListMapper.getOne(userNo_int,friendNo_int)!=null) {
            ChatList chatList = new ChatList(getChatNo(userNo, friendNo), message, getTime(), userNo_int, friendNo_int);
            chatListMapper.insertChat(chatList);
            stringRedisTemplate.opsForSet().add("signal" + friendNo, userNo);
        }
    }

    public JSONObject chooseFriend(String userNo,String friendNo){
        if (stringRedisTemplate.hasKey("signal"+userNo)) {
            stringRedisTemplate.opsForSet().remove("signal" + userNo, friendNo);
        }
        return chatList(userNo,friendNo);
    }

    public String friendMes(String userNo){
        if (stringRedisTemplate.hasKey("signal"+userNo)) {
            Set<String> set = stringRedisTemplate.opsForSet().members("signal" + userNo);
            if (!set.isEmpty()) {
                StringBuffer buf = new StringBuffer();
                for (String s : set) {
                    buf.append(s + ",");
                }
                return buf.toString();
            }
        }
        return"no message";
    }

    public void delChat(String userNo,String friendNo){
        chatListMapper.deleteChat(getChatNo(userNo,friendNo));
    }

    public static int getChatNo(String userNo, String friendNo){
        int userNo_int=Integer.parseInt(userNo),friendNo_int = Integer.parseInt(friendNo);
        return userNo_int<friendNo_int?(userNo_int*10+friendNo_int):(userNo_int+friendNo_int*10);
    }

    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
        return format.format(date);
    }

}
