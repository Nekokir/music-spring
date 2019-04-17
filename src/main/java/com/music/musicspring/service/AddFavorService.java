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

    @Override
    public int addSong(String userId, String site, String id, String name, String artists) {
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
            favorMapper.insertSong(userId, site, id, name, artists);
            return 1;
        }
    }

    @Override
    public int addAlbum(String userId, String site, String id, String name, String artist, String publishtime, int size) {
        Object res;
        if (site.equals("kugou")) {
            res = Kugou.getAlbum(id);
        } else if (site.equals("netease")) {
            res = Netease.getAlbum(id);
        } else if (site.equals("qq")) {
            res = Qq.getAlbum(id);
        } else {
            return 0;
        }
        if(res == null){
            return -1;
        }else{
            favorMapper.insertAlbum(userId, site, id, name, artist, publishtime, size);
            return 1;
        }
    }

    @Override
    public int addPlaylist(String userId, String site, String id, String name, String creator, int size) {
        Object res;
        if (site.equals("kugou")) {
            res = Kugou.getPlaylist(id);
        } else if (site.equals("netease")) {
            res = Netease.getPlaylist(id);
        } else if (site.equals("qq")) {
            res = Qq.getPlaylist(id);
        } else {
            return 0;
        }
        if(res == null){
            return -1;
        }else{
            favorMapper.insertPlaylist(userId, site, id, name, creator, size);
            return 1;
        }
    }
}
