package com.example.mapper;

import com.example.aliases.Admin;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper {

    @Select("select * from admin where account=#{account}")
    Admin getByAccount(int account);

}
