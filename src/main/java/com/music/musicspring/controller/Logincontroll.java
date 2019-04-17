package com.music.musicspring.controller;

import com.music.musicspring.entity.JsonResponse;
import com.music.musicspring.entity.Fail;
import com.music.musicspring.function.Functions;
import com.music.musicspring.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@EnableAutoConfiguration
public class Logincontroll {
    @Autowired
    private ILoginService loginService;
    @RequestMapping("/login")
    @ResponseBody
    public JsonResponse login(@RequestParam("loginPwd") String loginNum, @RequestParam("userId") String userId, HttpSession httpSession){

        String pwd = Functions.creatMD5(loginNum);
        System.out.println(userId+":"+pwd);

        int code = loginService.userLogin(userId,pwd);
        if(code == 1){
            System.out.println("yes");
            httpSession.setAttribute("userID",userId);
            return new JsonResponse(true);
        }else if(code == 0){
            System.out.println("fail");
            return new JsonResponse(false,new Fail(code,"密码错误"));
        }else if(code == -1){
            System.out.println("error");
            return new JsonResponse(false,new Fail(code,"用户名不存在"));
        }else{
            System.out.println("error");
            return new JsonResponse(false,new Fail(code,"服务器异常"));
        }
    }
    @RequestMapping("/userSession")
    @ResponseBody
    String userSession(HttpServletRequest request,HttpSession httpSession){
        HttpSession session = request.getSession();
        String username = (String)httpSession.getAttribute("userID");
        return username;
    }
    @RequestMapping("/logout")
    @ResponseBody
    boolean logout(HttpServletRequest request,HttpSession httpSession){
        HttpSession session = request.getSession();
        session.invalidate();
        return true;
    }
}
