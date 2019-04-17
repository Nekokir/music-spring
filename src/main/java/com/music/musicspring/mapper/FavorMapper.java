package com.music.musicspring.mapper;

import com.music.musicspring.entity.FavorAlbum;
import com.music.musicspring.entity.FavorPlaylist;
import com.music.musicspring.entity.FavorSong;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface FavorMapper {
    @Select("select site,id,name,artists from song where userId = #{userId}")
    ArrayList<FavorSong> findSongByUserid(@Param("userId") String userId);

    @Select("select site,id,name,artist,publishtime,size from album where userId = #{userId}")
    ArrayList<FavorAlbum> findAlbumByUserid(@Param("userId") String userId);

    @Select("select site,id,name,creator,size from playlist where userId = #{userId}")
    ArrayList<FavorPlaylist> findPlaylistByUserid(@Param("userId") String userId);

    @Insert("insert into song(userId,site,id,name,artists) values(#{userId},#{site},#{id},#{name},#{artists})")
    boolean insertSong(@Param("userId") String userId,@Param("site") String site,@Param("id") String id,@Param("name") String name,@Param("artists") String artists);

    @Insert("insert into album(userId,site,id,name,artist,publishtime,size) values(#{userId},#{site},#{id},#{name},#{artist},#{publishtime},#{size})")
    boolean insertAlbum(@Param("userId") String userId,@Param("site") String site,@Param("id") String id,
                        @Param("name") String name, @Param("artist") String artist,@Param("publishtime") String publishtime,@Param("size") int size);

    @Insert("insert into playlist(userId,site,id,name,creator,size) values(#{userId},#{site},#{id},#{name},#{creator},#{size})")
    boolean insertPlaylist(@Param("userId") String userId,@Param("site") String site,@Param("id") String id,
                           @Param("name") String name, @Param("creator") String creator,@Param("size") int size);

    @Delete("delete from song where record in (select a.record  from (select record from song where userID = #{userId} and site = #{site} and id = #{id})a)")
    boolean deleteSong(@Param("userId") String userId,@Param("site") String site,@Param("id") String id);

    @Delete("delete from album where record in (select a.record  from (select record from song where userID = #{userId} and site = #{site} and id = #{id})a)")
    boolean deleteAlbum(@Param("userId") String userId,@Param("site") String site,@Param("id") String id);

    @Delete("delete from playlist where record in (select a.record  from (select record from song where userID = #{userId} and site = #{site} and id = #{id})a)")
    boolean deletePlaylist(@Param("userId") String userId,@Param("site") String site,@Param("id") String id);
}
