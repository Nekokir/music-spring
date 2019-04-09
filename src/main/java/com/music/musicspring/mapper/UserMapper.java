package com.music.musicspring.mapper;

import com.music.musicspring.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where userId = #{userId}")
    User findUserByUserid(@Param("userId") String userId);
    @Insert("insert into user(userId,pwd) values(#{userId},#{pwd})")
    boolean insertUsers(@Param("userId") String userId,@Param("pwd") String pwd);
}
