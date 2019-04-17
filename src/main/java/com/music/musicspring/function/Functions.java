package com.music.musicspring.function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.musicspring.entity.*;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Functions {

    public static byte[] creatMD5Byte(String loginNum){

        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("MD5");
            md.update(loginNum.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();;
        }
        return  new BigInteger(1, md.digest()).toByteArray();
    }

    public static String creatMD5(String loginNum){

        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("MD5");
            md.update(loginNum.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();;
        }
        return  new BigInteger(1, md.digest()).toString(16);
    }

    public static String long2ip(long ip) {
        int [] b=new int[4] ;
        b[0] = (int)((ip >> 24) & 0xff);
        b[1] = (int)((ip >> 16) & 0xff);
        b[2] = (int)((ip >> 8) & 0xff);
        b[3] = (int)(ip & 0xff);
        String x;
        Integer p;
        x=(b[0])+"."+b[1]+"."+b[2]+"."+b[3];
        return x;
    }

    public static JsonNode curl(String url,MultiValueMap<String, String> params, String sites, HttpMethod method){
        RestTemplate ch = new RestTemplate();
        //url="http://localhost:5000";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if(sites.equals("netease")){
            Map<String,String> head = new LinkedHashMap<>();

            Random r = new Random();
            System.out.println(long2ip(r.nextLong()%74751 + 1884815360L));
            head.put("X-Real-IP",long2ip(r.nextLong()%74751 + 1884815360L));
            head.put("Cookie","appver=2.0.2");
            head.put("Accept-Language","zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4");
            head.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            head.put("Content-Type","application/x-www-form-urlencoded");
            headers.setAll(head);
            headers.set("Referer","http://music.163.com/");
        }else if(sites.equals("qq")){
            headers.set("Referer","http://y.qq.com/");
            headers.set("Origin","https://y.qq.com");
            headers.set("Content-Type","application/x-www-form-urlencoded");
        }else if(sites.equals("kugou")){
            headers.set("User-Agent","IPhone-8990-searchSong");
            headers.set("UNI-UserAgent","iOS11.4-Phone8990-1009-0-WiFi");
        }else if(sites.equals("xiami")){
            headers.set("Referer","https://h.xiami.com/");
        }
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(params,headers);
        ResponseEntity<String> response = ch.exchange(url,method, requestEntity, String.class);
        System.out.println(response.getBody());
        ObjectMapper m = new ObjectMapper();
        try {
            JsonNode res = m.readTree(response.getBody());
            return res;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static JsonResponse getPic(String url,String site){
        RestTemplate ch = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if(site.equals("netease")){
            Map<String,String> head = new LinkedHashMap<>();
            Random r = new Random();
            System.out.println(long2ip(r.nextLong()%74751 + 1884815360L));
            head.put("X-Real-IP",long2ip(r.nextLong()%74751 + 1884815360L));
            head.put("Cookie","appver=2.0.2");
            head.put("Accept-Language","zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4");
            head.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            head.put("Content-Type","application/x-www-form-urlencoded");
            headers.setAll(head);
            headers.set("Referer","http://music.163.com/");
        }else if(site.equals("qq")){
            headers.set("Referer","http://y.qq.com/");
            headers.set("Origin","https://y.qq.com");
            headers.set("Content-Type","application/x-www-form-urlencoded");
        }else if(site.equals("kugou")){
            headers.set("User-Agent","IPhone-8990-searchSong");
            headers.set("UNI-UserAgent","iOS11.4-Phone8990-1009-0-WiFi");
        }else if(site.equals("xiami")){
            headers.set("Referer","https://h.xiami.com/");
        }
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(null,headers);
        ResponseEntity<String> response = ch.exchange(url, HttpMethod.GET, requestEntity, String.class);
        System.out.println(response.getBody());
        try {
            String data = Base64.getEncoder().encodeToString(response.getBody().getBytes());
            return new JsonResponse(true,data);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResponse(false,new Fail(-1,"请求出错"));
        }
    }

    public static JsonResponse getAlbum(String site,String id){
        System.out.println(site);
        System.out.println(id);
        Album res;
        if(site.equals("kugou")){
            res = Kugou.getAlbum(id);
        }else if(site.equals("netease")){
            res = Netease.getAlbum(id);
        }else if(site.equals("qq")){
            res = Qq.getAlbum(id);
        }else if(site.equals("xiami")){
            res = Xiami.getAlbum(id);
        }else{
            return new JsonResponse(false,new Fail(0,"参数错误"));
        }
        if(res != null) {
            if(res.getId() == null) return new JsonResponse(false,new Fail(-1,"该专辑不存在"));
            return new JsonResponse(true, res);
        }else{
            return new JsonResponse(false,new Fail(-2,"服务器异常"));
        }
    }

    public static JsonResponse getSong(String site,String id) {
        Song res;
        if (site.equals("kugou")) {
            res = Kugou.getSong(id);
        } else if (site.equals("netease")) {
            res = Netease.getSong(id);
        } else if (site.equals("qq")) {
            res = Qq.getSong(id);
        } else if (site.equals("xiami")) {
            res = Xiami.getSong(id);
        } else {
            return new JsonResponse(false, new Fail(0, "参数错误"));
        }
        if (res != null) {
            if(res.getId() == null) return new JsonResponse(false,new Fail(-1,"该歌曲不存在"));
            return new JsonResponse(true, res);
        } else {
            return new JsonResponse(false, new Fail(-2, "服务器异常"));
        }
    }

    public static JsonResponse getPlaylist(String site,String id){
        Playlist res;
        if(site.equals("kugou")){
            res = Kugou.getPlaylist(id);
        }else if(site.equals("netease")){
            res = Netease.getPlaylist(id);
        }else if(site.equals("qq")){
            res = Qq.getPlaylist(id);
        }else if(site.equals("xiami")){
            res = Xiami.getPlaylist(id);
        }else{
            return new JsonResponse(false,new Fail(0,"参数错误"));
        }
        if(res != null) {
            if(res.getId() == null) return new JsonResponse(false,new Fail(-1,"该歌单不存在"));
            return new JsonResponse(true, res);
        }else{
            return new JsonResponse(false,new Fail(-2,"服务器异常"));
        }
    }

    public static JsonResponse searchAlbums(String site,String name){
        System.out.println(site);
        System.out.println(name);
        ArrayList<Album> res;
        if(site.equals("kugou")){
            res = Kugou.searchAlbums(name);
        }else if(site.equals("netease")){
            res = Netease.searchAlbums(name);
        }else if(site.equals("qq")){
            res = Qq.searchAlbums(name);
        }else if(site.equals("xiami")){
            res = Xiami.searchAlbums(name);
        }else{
            return new JsonResponse(false,new Fail(0,"参数错误"));
        }
        if(res != null) {
            if(res.size() == 0) return new JsonResponse(false,new Fail(-1,"该专辑不存在"));
            return new JsonResponse(true, res);
        }else{
            return new JsonResponse(false,new Fail(-1,"服务器异常"));
        }
    }

    public static JsonResponse searchPlaylists(String site,String name){
        ArrayList<Playlist> res;
        if(site.equals("kugou")){
            res = Kugou.searchPlaylists(name);
        }else if(site.equals("netease")){
            res = Netease.searchPlaylists(name);
        }else if(site.equals("qq")){
            res = Qq.searchPlaylists(name);
        }else if(site.equals("xiami")){
            res = Xiami.searchPlaylists(name);
        }else{
            return new JsonResponse(false,new Fail(0,"参数错误"));
        }
        if(res != null) {
            if(res.size() == 0) return new JsonResponse(false,new Fail(-1,"该歌单不存在"));
            return new JsonResponse(true, res);
        }else{
            return new JsonResponse(false,new Fail(-1,"服务器异常"));
        }
    }

    public static JsonResponse searchSongs(String site,String name){
        ArrayList<Song> res;
        if(site.equals("kugou")){
            res = Kugou.searchSongs(name);
        }else if(site.equals("netease")){
            res = Netease.searchSongs(name);
        }else if(site.equals("qq")){
            res = Qq.searchSongs(name);
        }else if(site.equals("xiami")){
            res = Xiami.searchSongs(name);
        }else{
            return new JsonResponse(false,new Fail(0,"参数错误"));
        }
        if(res != null) {
            if(res.size() == 0) return new JsonResponse(false,new Fail(-1,"该歌曲不存在"));
            return new JsonResponse(true, res);
        }else{
            return new JsonResponse(false,new Fail(-1,"服务器异常"));
        }
    }

    public static String dateToUnix(String publishTime){
        Long timestamp = Long.parseLong(publishTime);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(timestamp));
    }

    //  Thanks to https://github.com/metowolf/Meting/blob/master/src/Meting.php
    public static String encrpyId(String id){
        char[] s1 = "3go8&$8*3*3h0k(2)2".toCharArray();
        char[] s2 = id.toCharArray();
        for(int i = 0; i < id.length(); i++){
            s2[i] = (char)((int)s2[i]^(int)s1[i%s1.length]);
        }
        String res = Base64.getEncoder().encodeToString(creatMD5Byte(String.valueOf(s2)));
        res = res.replace('/','+');
        res = res.replace('_','-');
        return res;
    }

    public static String urlsafeB64encode(String s){
        String res = Base64.getEncoder().encodeToString(s.getBytes());
        res = res.replace('/','+');
        res = res.replace('_','-');
        return res;
    }

    public static String urlsafeB64decode(String s){
        s = s.replace('-','_');
        s = s.replace('+','/');
        int mod4 = s.length()%4;
        if(mod4 != 0){
            s += "====".substring(mod4);
        }
        return new String(Base64.getDecoder().decode(s.getBytes()));
    }
}