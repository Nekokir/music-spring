package com.music.musicspring.controller;

import com.music.musicspring.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    boolean login(@RequestParam("loginPwd")String loginNum, @RequestParam("userId") String userId, HttpSession httpSession){

        String pwd = Regcontroll.creatMD5(loginNum);
        System.out.println(userId+":"+pwd);

        if(loginService.userLogin(userId,pwd)){
            System.out.println("yes");
            httpSession.setAttribute("userID",userId);
            return true;
        }else {
            System.out.println("error");
            return false;
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
