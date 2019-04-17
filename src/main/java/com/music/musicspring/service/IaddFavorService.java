package com.music.musicspring.service;

public interface IaddFavorService {
    int addSong(String userId, String site,String id,String name,String artists);
    int addAlbum(String userId, String site,String id,String name,String artist,String publishtime,int size);
    int addPlaylist(String userId, String site,String id,String name,String creator,int size);
}
