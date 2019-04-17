package com.music.musicspring.service;

import com.music.musicspring.function.Kugou;
import com.music.musicspring.function.Netease;
import com.music.musicspring.function.Qq;
import com.music.musicspring.mapper.FavorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class DeleteFavorService implements IdeleteFavorService {

    @Autowired
    private FavorMapper favorMapper;

    @Override
    public int deleteSong(String userId, String site, String id) {
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
        if (res == null) {
            return -1;
        } else {
            favorMapper.deleteSong(userId, site, id);
            return 1;
        }
    }

    @Override
    public int deleteAlbum(String userId, String site, String id) {
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
        if (res == null) {
            return -1;
        } else {
            favorMapper.deleteAlbum(userId, site, id);
            return 1;
        }
    }

    @Override
    public int deletePlaylist(String userId, String site, String id) {
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
        if (res == null) {
            return -1;
        } else {
            favorMapper.deletePlaylist(userId, site, id);
            return 1;
        }
    }


}
