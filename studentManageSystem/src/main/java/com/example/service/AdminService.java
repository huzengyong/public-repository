package com.example.service;

import com.example.aliases.Admin;
import com.example.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class AdminService {

    @Resource
    AdminMapper adminmapper;

    public boolean adminCheck(String account,String password){
        Admin admin = adminmapper.getByAccount(Integer.parseInt(account));
        if (admin!=null && admin.getPassword().equals(password))
            return true;
        else return false;
    }

}
