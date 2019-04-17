package com.music.musicspring.controller;

import com.music.musicspring.function.Functions;
import com.music.musicspring.service.IRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class RegController {
    @Autowired
    private IRegService regService;
    @RequestMapping("/")
    String home(){
        return "frontEnd/index";
    }
    @RequestMapping("/welcome")
    String welcome(){
        return "welcome";
    }
    @RequestMapping("/reg")
    @ResponseBody
    boolean reg(@RequestParam("loginPwd") String loginNum, @RequestParam("userId") String userId){
        String pwd = Functions.creatMD5(loginNum);
        System.out.println(userId+":"+loginNum);
        if(regService.regUser(userId,pwd)){
            System.out.println("yes");
            return true;
        }else {
            System.out.println("error");
            return false;
        }
    }
}
