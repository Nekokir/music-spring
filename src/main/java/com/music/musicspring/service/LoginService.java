package com.music.musicspring.service;

import com.music.musicspring.entity.User;
import com.music.musicspring.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class LoginService implements ILoginService {
    @Autowired
    private UserMapper userMapper;
    public int userLogin(String userId,String pwd){
        System.out.println(userId+":"+pwd);
        User user;
        try {
            user = userMapper.findUserByUserid(userId);
            if(user != null){
                if( user.getPwd().equals(pwd) ){
                    return 1;
                }else {
                    System.out.println(user.getPwd());
                    return 0;
                }
            }else{
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
            return -2;
        }
    }
}
