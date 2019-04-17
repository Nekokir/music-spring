package com.music.musicspring.function;

import com.fasterxml.jackson.databind.JsonNode;
import com.music.musicspring.entity.Album;
import com.music.musicspring.entity.Playlist;
import com.music.musicspring.entity.Song;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.http.HttpMethod;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import static com.music.musicspring.function.Functions.curl;

public class Qq {

    public static Album getAlbum(String id){
        String url = "http://c.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg?inCharset=utf8&outCharset=utf-8&albumid="+id;
        JsonNode root = curl(url, null, "qq", HttpMethod.GET);
        System.out.println(root.toString());
        Album res = new Album();
        if (root != null) {
            root = root.get("data");
            if (root != null) {
                JsonNode json = null;
                json = root.get("id");
                if (json != null) {
                    res.setId(json.asText());
                } else return null;
                json = root.get("aDate");
                if (json != null) {
                    res.setPublishTime(json.asText());
                }
                json = root.get("name");
                if (json != null) {
                    res.setName(json.asText());
                }
                json = root.get("singername");
                if (json != null) {
                    res.setArtist(json.asText());
                }
                json = root.get("cur_song_num");
                if (json != null) {
                    res.setSize(json.asInt());
                } else return null;
                json = root.get("mid");
                if (json != null) {
                    res.setPicUrl("https://y.gtimg.cn/music/photo_new/T002R300x300M000"+json.asText()+".jpg");
                }
                json = root.get("list");
                ArrayList<Song> songs = new ArrayList<>();
                json.forEach((JsonNode node) -> {
                    Song song = new Song();
                    song.setAlbumId(res.getId());
                    song.setAlbumName(res.getName());
                    song.setPicUrl(res.getPicUrl());
                    song.setName(node.get("songname").asText());
                    song.setId(node.get("songid").asText());
                    song.setLink(null);
                    song.setLyrics(null);
                    song.setTranslations(null);
                    ArrayList<String> artists = new ArrayList<>();
                    node = node.get("singer");
                    node.forEach((JsonNode son) -> {
                        artists.add(son.get("name").asText());
                    });
                    song.setArtists(artists);
                    songs.add(song);
                });
                res.setSongs(songs);
                return res;
            } else return null;
        } else {
            res.setId(null);
            return res;
        }
    }

    public static ArrayList<Album> searchAlbums(String name){
        String url = "http://c.y.qq.com/soso/fcgi-bin/client_search_cp?t=8&p=1&n=30&inCharset=utf8&outCharset=utf-8&format=json&w="+name;
        JsonNode rootRaw = curl(url,null, "qq", HttpMethod.GET);
        ArrayList<Album> albums = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("data") != null && rootRaw.get("data").get("album") != null){
            rootRaw = rootRaw.get("data").get("album");
            if(rootRaw.get("totalnum") != null){
                if(rootRaw.get("totalnum").asInt() == 0){
                    albums.add(null);
                    return  albums;
                }else{
                    rootRaw = rootRaw.get("list");
                    System.out.println(rootRaw.toString());
                    rootRaw.forEach((JsonNode root) -> {
                        Album res = new Album();
                        JsonNode json = null;
                        json = root.get("albumID");
                        if (json != null) {
                            res.setId(json.asText());
                        }
                        json = root.get("publicTime");
                        if (json != null) {
                            res.setPublishTime(json.asText());
                        }
                        json = root.get("albumName");
                        if (json != null) {
                            res.setName(json.asText());
                        }
                        json = root.get("singerName");
                        if (json != null) {
                            res.setArtist(json.asText());
                        }else{
                            json = root.get("singer_list");
                            if (json != null) {
                                res.setArtist(json.get(0).asText());
                            }
                        }
                        json = root.get("song_count");
                        if (json != null) {
                            res.setSize(json.asInt());
                        }
                        json = root.get("albumMID");
                        if (json != null) {
                            res.setPicUrl("https://y.gtimg.cn/music/photo_new/T002R300x300M000"+json.asText()+".jpg");
                        }
                        res.setSongs(null);
                        albums.add(res);
                    });
                    return albums;
                }
            }else return null;
        }else return null;
    }

    public static Playlist getPlaylist(String id){
        String url = "https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg?inCharset=utf8&outCharset=utf-8&format=json&type=1&disstid="+id;
        JsonNode root = curl(url,null, "qq", HttpMethod.GET);
        Playlist res = new Playlist();
        if(root != null && root.get("cdlist")!= null){
            root = root.get("cdlist").get(0);
            JsonNode json = root.get("disstid");
            if(json != null){
                res.setId(json.asText());
            }else return null;
            json = root.get("dissname");
            if(json != null){
                res.setName(json.asText());
            }
            json = root.get("logo");
            if(json != null){
                res.setPicUrl(json.asText());
            }
            json = root.get("total_song_num");
            if(json != null){
                res.setSize(json.asInt());
            }
            json = root.get("nickname");
            if(json != null ){
                res.setCreator(json.asText());
            }
            ArrayList<Song> songs = new ArrayList<>();
            json = root.get("songlist");
            json.forEach((JsonNode node) -> {
                Song song = new Song();
                song.setAlbumId(node.get("albumid").asText());
                song.setAlbumName(node.get("albumname").asText());
                song.setPicUrl("https://y.gtimg.cn/music/photo_new/T002R300x300M000"+node.get("albummid").asText()+".jpg");
                song.setName(node.get("songname").asText());
                song.setId(node.get("songid").asText());
                song.setLink(null);
                song.setLyrics(null);
                song.setTranslations(null);
                ArrayList<String> artists = new ArrayList<>();
                node = node.get("singer");
                node.forEach((JsonNode son) -> {
                    artists.add(son.get("name").asText());
                });
                song.setArtists(artists);
                songs.add(song);
            });
            if(songs != null){
                res.setSongs(songs);
            }
            return res;
        }else {
            res.setId(null);
            return res;
        }
    }

    public static ArrayList<Playlist> searchPlaylists(String name){
        String url = "https://c.y.qq.com/soso/fcgi-bin/client_music_search_songlist?page_no=0&num_per_page=30&inCharset=utf8&outCharset=utf-8&format=json&query="+name;
        JsonNode rootRaw = curl(url,null, "qq", HttpMethod.GET);
        System.out.println(rootRaw.toString());
        ArrayList<Playlist> playlists = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("data") != null){
            rootRaw = rootRaw.get("data");
            if(rootRaw.get("sum") != null) {
                if(rootRaw.get("sum").asInt() == 0){
                    playlists.add(null);
                    return  playlists;
                }else{
                    rootRaw = rootRaw.get("list");
                    rootRaw.forEach((JsonNode root) -> {
                        Playlist res = new Playlist();
                        JsonNode json = root.get("dissid");
                        if(json != null){
                            res.setId(json.asText());
                        }
                        json = root.get("dissname");
                        if(json != null){
                            res.setName(StringEscapeUtils.unescapeHtml4(json.asText()));
                        }
                        json = root.get("imgurl");
                        if(json != null){
                            res.setPicUrl(json.asText());
                        }
                        json = root.get("song_count");
                        if(json != null){
                            res.setSize(json.asInt());
                        }
                        json = root.get("creator");
                        if(json != null && json.get("name") != null){
                            res.setCreator(StringEscapeUtils.unescapeHtml4(json.get("name").asText()));
                        }
                        res.setSongs(null);
                        playlists.add(res);
                    });
                    return playlists;
                }
            }else return null;
        }else return null;
    }

    public static Song getSong(String id){
        String url = "http://c.y.qq.com/v8/fcg-bin/fcg_play_single_song.fcg?inCharset=utf8&outCharset=utf-8&format=json&songid="+id;
        JsonNode jsonRoot = curl(url,null, "qq", HttpMethod.GET);
        System.out.println(jsonRoot.toString());

        Song res = new Song();
        if (jsonRoot != null) {
            if (jsonRoot.get("data") != null) {
                jsonRoot = jsonRoot.get("data").get(0);
                JsonNode node = jsonRoot.get("id");
                if (node != null) {
                    res.setId(node.asText());
                } else return null;
                node = jsonRoot.get("name");
                if (node != null) {
                    res.setName(node.asText());
                }
                ArrayList<String> artists = new ArrayList<>();
                node = jsonRoot.get("singer");
                node.forEach((JsonNode son) -> {
                    artists.add(son.get("name").asText());
                });
                res.setArtists(artists);

                Random r=new Random();
                String guid = ""+r.nextLong()%10000000000L;
                String songmid = jsonRoot.get("mid").asText();
                String songtype = jsonRoot.get("type").asText();
                //getLinkInfo

                String link = null;
                try {
                    String data = "{\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\""+guid+"\",\"songmid\":[\""+songmid+"\"],\"songtype\":["+songtype+"],\"uin\":\"0\",\"loginflag\":1,\"platform\":\"20\"}}}";
                    data = URLEncoder.encode(data, "UTF-8");
                    String linkUrl = "https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=0&data=" + data;
                    JsonNode linkRoot = curl(linkUrl, null, "qq", HttpMethod.GET);
                    System.out.println(linkRoot);
                    if(linkRoot.get("req_0") != null) {
                        link = linkRoot.get("req_0").get("data").get("sip").get(0).asText()+linkRoot.get("req_0").get("data").get("midurlinfo").get(0).get("purl").asText();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                res.setLink(link);
                if (res.getLink() != null) {
                    res.setLink(res.getLink().replace("http://", "https://"));
                }

                //getAlbumInfo
                jsonRoot = jsonRoot.get("album");
                if (jsonRoot != null) {
                    node = jsonRoot.get("id");
                    if (node != null) {
                        res.setAlbumId(node.asText());
                    }
                    node = jsonRoot.get("name");
                    if (node != null) {
                        res.setAlbumName(node.asText());
                    }
                    node = jsonRoot.get("mid");
                    if (node != null) {
                        res.setPicUrl("https://y.gtimg.cn/music/photo_new/T002R300x300M000"+node.asText()+".jpg");
                    }
                }
                if (res.getPicUrl() != null) {
                    res.setPicUrl(res.getPicUrl().replace("http://", "https://"));
                }

                //getLyricsInfo
                url = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=0&g_tk=5381&songmid="+songmid;
                jsonRoot = curl(url, null, "qq", HttpMethod.GET);
                System.out.println(jsonRoot.toString());
                if (jsonRoot != null && jsonRoot.get("lyric") != null && jsonRoot.get("trans")!= null) {
                    res.setLyrics(new String(Base64.getDecoder().decode(jsonRoot.get("lyric").asText())));
                    res.setTranslations(new String(Base64.getDecoder().decode(jsonRoot.get("trans").asText())));
                }
                return res;
            } else {
                res.setId(null);
                return res;
            }
        } else return null;
    }

    public static ArrayList<Song> searchSongs(String name){
        String url = "http://c.y.qq.com/soso/fcgi-bin/client_search_cp?new_json=1&cr=1&p=1&n=30&inCharset=utf8&outCharset=utf-8&format=json&w="+name;
        JsonNode rootRaw = curl(url,null, "qq", HttpMethod.GET);

        System.out.println(rootRaw.toString());

        ArrayList<Song> songs = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("data") != null) {
            rootRaw = rootRaw.get("data").get("song");
            if (rootRaw.get("totalnum") != null) {
                if (rootRaw.get("totalnum").asInt() == 0) {
                    songs.add(null);
                    return songs;
                } else {
                    rootRaw = rootRaw.get("list");
                    System.out.println(rootRaw.toString());
                    rootRaw.forEach((JsonNode jsonRoot) -> {
                        Song res = new Song();
                        JsonNode node = jsonRoot.get("id");
                        if (node != null) {
                            res.setId(node.asText());
                        }
                        node = jsonRoot.get("name");
                        if (node != null) {
                            res.setName(node.asText());
                        }
                        ArrayList<String> artists = new ArrayList<>();
                        node = jsonRoot.get("singer");
                        node.forEach((JsonNode son) -> {
                            artists.add(son.get("name").asText());
                        });
                        res.setArtists(artists);

                        //getAlbumInfo
                        jsonRoot = jsonRoot.get("album");
                        if (jsonRoot != null) {
                            node = jsonRoot.get("id");
                            if (node != null) {
                                res.setAlbumId(node.asText());
                            }
                            node = jsonRoot.get("name");
                            if (node != null) {
                                res.setAlbumName(node.asText());
                            }
                            node = jsonRoot.get("mid");
                            if (node != null) {
                                res.setPicUrl("https://y.gtimg.cn/music/photo_new/T002R300x300M000"+node.asText()+".jpg");
                            }
                        }
                        if (res.getPicUrl() != null) {
                            res.setPicUrl(res.getPicUrl().replace("http://", "https://"));
                        }

                        songs.add(res);
                    });
                    return songs;
                }
            }else return null;
        }else return null;
    }
}
