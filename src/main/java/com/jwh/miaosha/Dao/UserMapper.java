package com.jwh.miaosha.Dao;

import com.jwh.miaosha.Data.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectByName(String name);

    User selectByMobile(String mobile);

}