package com.example.houseSearch.mapper;

import com.example.houseSearch.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface selectUserMapper {

    ArrayList<User> getAllUser();

    void insertUser(@Param("name") String name,@Param("password") String password);

}
