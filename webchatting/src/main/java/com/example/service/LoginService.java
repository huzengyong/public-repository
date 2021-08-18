package com.example.service;


import com.example.aliases.User;
import com.example.mapper.UserMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;

@Service
public class LoginService {
//    @Resource
//    StringRedisTemplate stringRedisTemplate;

    @Resource
    UserMapper userMapper;


    public boolean hasUser(String username){
        if (userMapper.getByUsername(username)!=null){
            return true;
        }
        return false;
    }

    public String getUsername(String userNo){
        return userMapper.getByUserNo(Integer.parseInt(userNo)).getUsername();
    }

    public int getUserNoByUserName(String username){
        return userMapper.getByUsername(username).getUserNo();
    }

    public User getUserByUserNo(String userNo){
        return userMapper.getByUserNo(Integer.parseInt(userNo));
    }

    public String loginCheck(String username, String password, Model model){
        User user = userMapper.getByUsername(username);
//        if (stringRedisTemplate.opsForSet().members("online").contains(username)){
//            return "online";
//        }
        if (user!=null){
//            if (user.getPassword().equals(password)){
//                stringRedisTemplate.opsForSet().add("online",username);
                return "success";
//            }
        }
        return "false";
    }

    public boolean registerUser(String username,String password){
        if (userMapper.getByUsername(username)==null){
            userMapper.insertOne(username,password);
            return true;
        }
        return false;
    }

//    public void leave(String username){
//        stringRedisTemplate.opsForSet().remove("online",username);
//    }
}
