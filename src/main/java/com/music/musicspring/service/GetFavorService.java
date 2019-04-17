package com.music.musicspring.service;

import com.music.musicspring.entity.FavorAlbum;
import com.music.musicspring.entity.FavorPlaylist;
import com.music.musicspring.entity.FavorSong;
import com.music.musicspring.mapper.FavorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service()
public class GetFavorService implements IgetFavorService {
    @Autowired
    private FavorMapper favorMapper;

    @Override
    public ArrayList<FavorSong> getSong(String userId) {
        ArrayList<FavorSong> favors;
        favors = favorMapper.findSongByUserid(userId);
        return favors;
    }

    @Override
    public ArrayList<FavorAlbum> getAlbum(String userId) {
        ArrayList<FavorAlbum> favors;
        favors = favorMapper.findAlbumByUserid(userId);
        return favors;
    }

    @Override
    public ArrayList<FavorPlaylist> getPlaylist(String userId) {
        ArrayList<FavorPlaylist> favors;
        favors = favorMapper.findPlaylistByUserid(userId);
        return favors;
    }
}
