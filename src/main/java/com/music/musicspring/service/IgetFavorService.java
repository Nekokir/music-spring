package com.music.musicspring.service;

import com.music.musicspring.entity.Favor;

import java.util.ArrayList;

public interface IgetFavorService {
    ArrayList<Favor> getFavor(String userId,String type);
}
