package com.music.musicspring.controller;

import com.music.musicspring.entity.Fail;
import com.music.musicspring.entity.JsonResponse;
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
    JsonResponse reg(@RequestParam("loginPwd") String loginNum, @RequestParam("userId") String userId){
        String pwd = Functions.creatMD5(loginNum);
        System.out.println(userId+":"+loginNum);
        int code = regService.regUser(userId,pwd);
        if(code == 1){
            System.out.println("yes");
            return new JsonResponse(true);
        }else if(code == 0){
            System.out.println("error");
            return new JsonResponse(false,new Fail(code,"用户已存在，请登录"));
        }else {
            return new JsonResponse(false,new Fail(code,"服务器出错"));
        }
    }
}
