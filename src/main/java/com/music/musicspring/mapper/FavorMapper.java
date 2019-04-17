package com.music.musicspring.mapper;

import com.music.musicspring.entity.Favor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface FavorMapper {
    @Select("select site,id from song where userId = #{userId}")
    ArrayList<Favor> findSongByUserid(@Param("userId") String userId);

    @Select("select site,id from album where userId = #{userId}")
    ArrayList<Favor> findAlbumByUserid(@Param("userId") String userId);

    @Select("select site,id from playlist where userId = #{userId}")
    ArrayList<Favor> findPlaylistByUserid(@Param("userId") String userId);

    @Insert("insert into song(userId,site,id) values(#{userId},#{site},#{id})")
    boolean insertSong(@Param("userId") String userId,@Param("site") String site,@Param("id") String id);

    @Insert("insert into album(userId,site,id) values(#{userId},#{site},#{id})")
    boolean insertAlbum(@Param("userId") String userId,@Param("site") String site,@Param("id") String id);

    @Insert("insert into playlist(userId,site,id) values(#{userId},#{site},#{id})")
    boolean insertPlaylist(@Param("userId") String userId,@Param("site") String site,@Param("id") String id);
}
