package com.music.musicspring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.music.musicspring.mapper")
public class MusicSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicSpringApplication.class, args);
    }

}
