package com.music.musicspring.service;

public interface IdeleteFavorService {
    int deleteSong(String userId, String site,String id);
    int deleteAlbum(String userId, String site,String id);
    int deletePlaylist(String userId, String site,String id);
}
