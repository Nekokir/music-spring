package com.music.musicspring.service;

import com.music.musicspring.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class RegService implements IRegService {
    @Autowired
    private UserMapper userMapper;
    public int regUser(String userId, String pwd){

        Object res = userMapper.findUserByUserid(userId);
        if(res != null){
            return 0;
        }else {
            boolean flag;
            try {
                flag = userMapper.insertUsers(userId, pwd);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
    }
}
