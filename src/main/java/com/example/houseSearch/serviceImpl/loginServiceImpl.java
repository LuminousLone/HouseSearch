package com.example.houseSearch.serviceImpl;

import com.example.houseSearch.bean.User;
import com.example.houseSearch.mapper.selectUserMapper;
import com.example.houseSearch.service.loginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class loginServiceImpl implements loginService {

    @Resource
    selectUserMapper  selectUserMapper;

    @Override
    public Boolean isIn(String name, String password) {

        User temp = new User(name,password);

        ArrayList<User> rt = selectUserMapper.getAllUser();

        for( User user : rt){
            if (user.equals(temp))
                return true;
        }
        return false;
    }

    @Override
    public void insertUser(String name, String password) {

        this.selectUserMapper.insertUser(name, password);

    }
}
