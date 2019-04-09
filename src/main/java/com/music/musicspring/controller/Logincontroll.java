package com.music.musicspring.controller;

import com.music.musicspring.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class Logincontroll {
    @Autowired
    private ILoginService loginService;
    @RequestMapping("/login")
    @ResponseBody
    boolean login(@RequestParam("loginPwd")String loginNum, @RequestParam("userId") String userId){

        String pwd = Regcontroll.creatMD5(loginNum);
        System.out.println(userId+":"+pwd);

        if(loginService.userLogin(userId,pwd)){
            System.out.println("yes");
            return true;
        }else {
            System.out.println("error");
            return false;
        }
    }
}
