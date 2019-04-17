package com.music.musicspring.service;

import com.music.musicspring.entity.Favor;
import com.music.musicspring.mapper.FavorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service()
public class GetFavorService implements IgetFavorService {
    @Autowired
    private FavorMapper favorMapper;
    public ArrayList<Favor> getFavor(String userId, String type){
        ArrayList<Favor> favors;
        if(type.equals("song")){
            favors = favorMapper.findSongByUserid(userId);
        }else if(type.equals("album")){
            favors = favorMapper.findAlbumByUserid(userId);
        }else if(type.equals("playlist")){
            favors = favorMapper.findPlaylistByUserid(userId);
        }else{
            return null;
        }
        return favors;
    }
}
