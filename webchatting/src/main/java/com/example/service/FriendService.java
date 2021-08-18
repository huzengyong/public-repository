package com.example.service;

import com.example.aliases.FriendList;
import com.example.aliases.User;
import com.example.mapper.FriendListMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FriendService {

    @Resource
    FriendListMapper friendListMapper;

    @Resource
    LoginService loginService;

    @Resource
    ChatService chatService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public List<FriendList> getFriendList(int userNo){
        return friendListMapper.getFriendes(userNo);
    }

    public boolean addFriend(int userNo,int friendNo){
        if (hasFriend(userNo,friendNo)){
            return false;
        }
        stringRedisTemplate.opsForSet().add("friendApply"+friendNo,Integer.toString(userNo));
        return true;
    }

    public void receiveApply(String userNo,String friendNo){
        friendListMapper.insertFriend(new FriendList(Integer.parseInt(userNo),Integer.parseInt(friendNo),loginService.getUsername(friendNo)));
        friendListMapper.insertFriend(new FriendList(Integer.parseInt(friendNo),Integer.parseInt(userNo),loginService.getUsername(userNo)));
        stringRedisTemplate.opsForSet().remove("friendApply"+userNo,friendNo);
    }

    public void refuseApply(String userNo,String friendNo){
        stringRedisTemplate.opsForSet().remove("friendApply"+userNo,friendNo);
    }

    public boolean delFriend(String userNo,String friendNo){
        int userNo_int = Integer.parseInt(userNo),friendNo_int = Integer.parseInt(friendNo);
        if (friendListMapper.getOne(userNo_int,friendNo_int)!=null) {
            friendListMapper.deleteFriend(userNo_int, friendNo_int);
            friendListMapper.deleteFriend(friendNo_int, userNo_int);
            chatService.delChat(userNo, friendNo);
            return true;
        }
        return false;
    }

    public boolean hasFriend(int userNo,int friendNo){
        if (friendListMapper.getOne(userNo,friendNo)!=null){
            return true;
        }
        return false;
    }

    public Set getApplySet(String userNo){
        Set<String> friendNoSet = stringRedisTemplate.opsForSet().members("friendApply"+userNo);
        Set<User> friendSet = new HashSet<>();
        if (friendNoSet!=null){
            if (!friendNoSet.isEmpty()){
                for (String s:friendNoSet){
                    friendSet.add(loginService.getUserByUserNo(s));
                }
                return friendSet;
            }
        }
        return null;
    }
}
