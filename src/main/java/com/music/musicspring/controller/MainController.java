package com.music.musicspring.controller;

import com.music.musicspring.entity.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.music.musicspring.function.Functions.*;

@Controller
@EnableAutoConfiguration
public class MainController {
    @RequestMapping("/album")
    @ResponseBody
    public JsonResponse album(@RequestParam("site") String site,@RequestParam("id") String id){
        return getAlbum(site,id);
    }

    @RequestMapping("/song")
    @ResponseBody
    public JsonResponse song(@RequestParam("site")String site,@RequestParam("id")String id){
        return getSong(site,id);
    }

    @RequestMapping("/playlist")
    @ResponseBody
    public JsonResponse playlist(@RequestParam("site")String site,@RequestParam("id")String id){
        return getPlaylist(site,id);
    }

    @RequestMapping("/search_album")
    @ResponseBody
    public JsonResponse albums(@RequestParam("site")String site,@RequestParam("name")String name){
        return searchAlbums(site,name);
    }

    @RequestMapping("/search_song")
    @ResponseBody
    public JsonResponse songs(@RequestParam("site")String site,@RequestParam("name")String name){
        return searchSongs(site,name);
    }

    @RequestMapping("/search_playlist")
    @ResponseBody
    public JsonResponse playlists(@RequestParam("site")String site,@RequestParam("name")String name){
        return searchPlaylists(site,name);
    }
}
