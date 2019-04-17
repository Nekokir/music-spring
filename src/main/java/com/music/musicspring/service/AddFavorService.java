package com.music.musicspring.service;


import com.music.musicspring.function.Kugou;
import com.music.musicspring.function.Netease;
import com.music.musicspring.function.Qq;
import com.music.musicspring.mapper.FavorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class AddFavorService implements IaddFavorService {
    @Autowired
    private FavorMapper favorMapper;
    public int addFavor(String userId, String site,String type,String id){
        Object res;
        if (site.equals("kugou")) {
            res = Kugou.getSong(id);
        } else if (site.equals("netease")) {
            res = Netease.getSong(id);
        } else if (site.equals("qq")) {
            res = Qq.getSong(id);
        } else {
            return 0;
        }
        if(res == null){
            return -1;
        }else{
            if(type.equals("song")){
                favorMapper.insertSong(userId, site, id);
                return 1;
            }else if(type.equals("album")){
                favorMapper.insertAlbum(userId, site, id);
                return 1;
            }else if(type.equals("playlist")){
                favorMapper.insertPlaylist(userId, site, id);
                return 1;
            }else{
                return -2;
            }
        }
    }
}
