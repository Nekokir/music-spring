package com.music.musicspring.service;

import com.music.musicspring.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class RegService implements IRegService {
    @Autowired
    private UserMapper userMapper;
    public boolean regUser(String userId, String pwd){

        boolean flag;
        try {
            flag = userMapper.insertUsers(userId,pwd);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return  flag;
    }
}
