package com.music.musicspring.controller;

import com.music.musicspring.service.IRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
@EnableAutoConfiguration
public class Regcontroll {

    @Autowired

    private IRegService regService;
    @RequestMapping("/")
    String home(){
        return "index";
    }
    @RequestMapping("/welcome")
    String welcome(){
        return "welcome";
    }
    @RequestMapping("/reg")
    @ResponseBody
    boolean reg(@RequestParam("loginPwd") String loginNum, @RequestParam("userId") String userId){
        String pwd = creatMD5(loginNum);
        System.out.println(userId+":"+loginNum);
        if(regService.regUser(userId,pwd)){
            System.out.println("yes");
            return true;
        }else {
            System.out.println("error");
            return false;
        }
    }
    public static String creatMD5(String loginNum){

        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("MD5");
            md.update(loginNum.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();;
        }
        return  new BigInteger(1, md.digest()).toString(16);
    }
}
