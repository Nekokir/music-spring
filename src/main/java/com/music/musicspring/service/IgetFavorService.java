package com.music.musicspring.service;

import com.music.musicspring.entity.FavorAlbum;
import com.music.musicspring.entity.FavorPlaylist;
import com.music.musicspring.entity.FavorSong;

import java.util.ArrayList;

public interface IgetFavorService {
    ArrayList<FavorSong> getSong(String userId);
    ArrayList<FavorAlbum> getAlbum(String userId);
    ArrayList<FavorPlaylist> getPlaylist(String userId);
}
