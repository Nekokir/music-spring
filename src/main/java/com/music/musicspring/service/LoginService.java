package com.music.musicspring.service;

import com.music.musicspring.entity.User;
import com.music.musicspring.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class LoginService implements ILoginService {
    @Autowired
    private UserMapper userMapper;
    public boolean userLogin(String userId,String pwd){
        System.out.println(userId+":"+pwd);
        User user;
        try {
            user = userMapper.findUserByUserid(userId);
            if( user.getPwd().equals(pwd) ){
                return true;
            }else {
                System.out.println(user.getPwd());
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
