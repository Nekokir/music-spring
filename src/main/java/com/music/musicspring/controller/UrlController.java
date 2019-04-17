package com.music.musicspring.controller;

import com.music.musicspring.entity.JsonResponse;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.music.musicspring.function.Functions.getPic;

@Controller
@EnableAutoConfiguration
public class UrlController {
    @RequestMapping("/pic")
    @ResponseBody
    public JsonResponse pic(@RequestParam("url") String url,@RequestParam("site") String site){
        return getPic(url,site);
    }
}
