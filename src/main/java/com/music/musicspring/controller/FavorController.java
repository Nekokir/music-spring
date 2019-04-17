package com.music.musicspring.controller;

import com.music.musicspring.entity.Fail;
import com.music.musicspring.entity.Favor;
import com.music.musicspring.entity.JsonResponse;
import com.music.musicspring.service.IaddFavorService;
import com.music.musicspring.service.IgetFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@EnableAutoConfiguration
public class FavorController {
    @Autowired
    private IaddFavorService addFavorService;
    @Autowired
    private IgetFavorService getFavorService;
    @RequestMapping("/get_favor")
    @ResponseBody
    public JsonResponse getFavor(@RequestParam("type") String type, @RequestParam("userId") String userId){
        ArrayList<Favor> favors = getFavorService.getFavor(userId,type);
        if(favors != null){
            return new JsonResponse(true,favors);
        }else{
            return new JsonResponse(false,new Fail(0,"未查询到收藏"));
        }
    }
    @RequestMapping("/add_favor")
    @ResponseBody
    public JsonResponse addFavor(@RequestParam("type") String type, @RequestParam("site") String site, @RequestParam("userId") String userId, @RequestParam("id") String id){
        int code = addFavorService.addFavor(userId,site,type,id);
        if(code == 1){
            return new JsonResponse(true);
        }else if(code == 0){
            return new JsonResponse(false,new Fail(code,"输入了无效站点"));
        }else if(code == -1){
            return new JsonResponse(false,new Fail(code,"输入了无效ID"));
        }else if(code == -2){
            return new JsonResponse(false,new Fail(code,"输入了无效type"));
        }else {
            return new JsonResponse(false,new Fail(code,"服务器错误"));
        }
    }

}
