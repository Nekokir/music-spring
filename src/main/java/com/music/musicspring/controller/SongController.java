package com.music.musicspring.controller;

import com.music.musicspring.entity.Fail;
import com.music.musicspring.entity.FavorSong;
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
public class SongController {
    @Autowired
    private IaddFavorService addFavorService;
    @Autowired
    private IgetFavorService getFavorService;
    @RequestMapping("/get_favor_song")
    @ResponseBody
    public JsonResponse getSong(@RequestParam("userId") String userId){
        ArrayList<FavorSong> favors = getFavorService.getSong(userId);
        if(favors != null){
            return new JsonResponse(true,favors);
        }else{
            return new JsonResponse(false,new Fail(0,"未查询到收藏"));
        }
    }
    @RequestMapping("/add_favor_song")
    @ResponseBody
    public JsonResponse addSong(@RequestParam("site") String site, @RequestParam("userId") String userId, @RequestParam("id") String id,
                                 @RequestParam("name") String name, @RequestParam("artists") String artists){
        int code = addFavorService.addSong(site, userId, id, name, artists);
        if(code == 1){
            return new JsonResponse(true);
        }else if(code == 0){
            return new JsonResponse(false,new Fail(code,"输入了无效站点"));
        }else if(code == -1){
            return new JsonResponse(false,new Fail(code,"输入了无效ID"));
        }else {
            return new JsonResponse(false,new Fail(code,"服务器错误"));
        }
    }
}
