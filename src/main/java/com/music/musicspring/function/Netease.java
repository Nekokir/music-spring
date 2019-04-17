package com.music.musicspring.function;

import com.fasterxml.jackson.databind.JsonNode;
import com.music.musicspring.entity.Album;
import com.music.musicspring.entity.Playlist;
import com.music.musicspring.entity.Song;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;

import static com.music.musicspring.function.Functions.*;

public class Netease {

    public static Album getAlbum(String id) {

        String url = "http://music.163.com/api/album/" + id;
        MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
        m.add("id", id);
        JsonNode root = curl(url, m, "netease", HttpMethod.GET);
        Album res = new Album();
        if (root != null) {
            root = root.get("album");
            if (root != null) {
                JsonNode json = null;
                json = root.get("id");
                if (json != null) {
                    res.setId(json.asText());
                } else return null;
                json = root.get("publishTime");
                if (json != null) {
                    res.setPublishTime(dateToUnix(json.asText()));
                }
                json = root.get("name");
                if (json != null) {
                    res.setName(json.asText());
                }
                json = root.get("artist").get("name");
                if (json != null) {
                    res.setArtist(json.asText());
                } else {
                    json = root.get("artists").get(0).get("name");
                    if (json != null) {
                        res.setArtist(json.asText());
                    }
                }
                json = root.get("size");
                if (json != null) {
                    res.setSize(json.asInt());
                } else return null;
                json = root.get("picUrl");
                if (json != null) {
                    res.setPicUrl(json.asText());
                }
                json = root.get("songs");
                ArrayList<Song> songs = new ArrayList<>();
                json.forEach((JsonNode node) -> {
                    Song song = new Song();
                    song.setAlbumId(res.getId());
                    song.setAlbumName(res.getName());
                    song.setPicUrl(res.getPicUrl());
                    song.setName(node.get("name").asText());
                    song.setId(node.get("id").asText());
                    song.setLink(null);
                    song.setLyrics(null);
                    song.setTranslations(null);
                    ArrayList<String> artists = new ArrayList<>();
                    node = node.get("artists");
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

    public static ArrayList<Album> searchAlbums(String name) {
        String url = "http://music.163.com/api/search/pc";
        MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
        m.add("offset", "0");
        m.add("limit","99");
        m.add("type","10");
        m.add("s",name);
        JsonNode rootRaw = curl(url,m, "netease", HttpMethod.POST);
        ArrayList<Album> albums = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("result") != null){
            rootRaw = rootRaw.get("result");
            if(rootRaw.get("albumCount") != null){
                if(rootRaw.get("albumCount").asInt() == 0){
                    albums.add(null);
                    return  albums;
                }else{
                    rootRaw = rootRaw.get("albums");
                    System.out.println(rootRaw.toString());
                    rootRaw.forEach((JsonNode root) -> {
                        Album res = new Album();
                        JsonNode json = null;
                        json = root.get("id");
                        if (json != null) {
                            res.setId(json.asText());
                        }
                        json = root.get("publishTime");
                        if (json != null) {
                            res.setPublishTime(dateToUnix(json.asText()));
                        }
                        json = root.get("name");
                        if (json != null) {
                            res.setName(json.asText());
                        }
                        json = root.get("artist").get("name");
                        if (json != null) {
                            res.setArtist(json.asText());
                        } else {
                            json = root.get("artists").get(0).get("name");
                            if (json != null) {
                                res.setArtist(json.asText());
                            }
                        }
                        json = root.get("size");
                        if (json != null) {
                            res.setSize(json.asInt());
                        }
                        json = root.get("picUrl");
                        if (json != null) {
                            res.setPicUrl(json.asText());
                        }
                        json = root.get("songs");
                        ArrayList<Song> songs = new ArrayList<>();
                        json.forEach((JsonNode node) -> {
                            Song song = new Song();
                            song.setAlbumId(res.getId());
                            song.setAlbumName(res.getName());
                            song.setPicUrl(res.getPicUrl());
                            song.setName(node.get("name").asText());
                            song.setId(node.get("id").asText());
                            song.setLink(null);
                            song.setLyrics(null);
                            song.setTranslations(null);
                            ArrayList<String> artists = new ArrayList<>();
                            node = node.get("artists");
                            node.forEach((JsonNode son) -> {
                                artists.add(son.get("name").asText());
                            });
                            song.setArtists(artists);
                            songs.add(song);
                        });
                        res.setSongs(songs);
                        albums.add(res);
                    });
                    return albums;
                }
            }else return null;
        }else return null;
    }

    public static Playlist getPlaylist(String id) {
        String url = "http://music.163.com/api/playlist/detail?id="+id;
        JsonNode root = curl(url,null, "netease", HttpMethod.GET);
        Playlist res = new Playlist();
        if(root != null && root.get("result")!= null){
            root = root.get("result");
            JsonNode json = root.get("id");
            if(json != null){
                res.setId(json.asText());
            }else return null;

            json = root.get("name");
            if(json != null){
                res.setName(json.asText());
            }
            json = root.get("coverImgUrl");
            if(json != null){
                res.setPicUrl(json.asText());
            }
            json = root.get("trackCount");
            if(json != null){
                res.setSize(json.asInt());
            }
            json = root.get("creator");
            if(json != null && json.get("nickname") != null){
                res.setCreator(json.get("nickname").asText());
            }
            ArrayList<Song> songs = new ArrayList<>();
            json = root.get("tracks");
            json.forEach((JsonNode node) -> {
                Song song = new Song();
                song.setAlbumId(node.get("album").get("id").asText());
                song.setAlbumName(node.get("album").get("name").asText());
                song.setPicUrl(node.get("album").get("picUrl").asText());
                song.setName(node.get("name").asText());
                song.setId(node.get("id").asText());
                song.setLink(null);
                song.setLyrics(null);
                song.setTranslations(null);
                ArrayList<String> artists = new ArrayList<>();
                node = node.get("artists");
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

    public static ArrayList<Playlist> searchPlaylists(String name) {
        String url = "http://music.163.com/api/search/pc";
        MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
        m.add("offset", "0");
        m.add("limit","99");
        m.add("type","1000");
        m.add("s",name);
        JsonNode rootRaw = curl(url,m, "netease", HttpMethod.POST);
        System.out.println(rootRaw.toString());
        ArrayList<Playlist> playlists = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("result") != null){
            rootRaw = rootRaw.get("result");
            if(rootRaw.get("playlistCount") != null){
                if(rootRaw.get("playlistCount").asInt() == 0){
                    playlists.add(null);
                    return  playlists;
                }else{
                    rootRaw = rootRaw.get("playlists");
                    System.out.println(rootRaw.toString());
                    rootRaw.forEach((JsonNode root) -> {
                        Playlist res = new Playlist();
                        JsonNode json = root.get("id");
                        if(json != null){
                            res.setId(json.asText());
                        }

                        json = root.get("name");
                        if(json != null){
                            res.setName(json.asText());
                        }
                        json = root.get("coverImgUrl");
                        if(json != null){
                            res.setPicUrl(json.asText());
                        }
                        json = root.get("trackCount");
                        if(json != null){
                            res.setSize(json.asInt());
                        }
                        json = root.get("creator");
                        if(json != null && json.get("nickname") != null){
                            res.setCreator(json.get("nickname").asText());
                        }
                        ArrayList<Song> songs = new ArrayList<>();
                        res.setSongs(null);
                        playlists.add(res);
                    });
                    return playlists;
                }
            }else return null;
        }else return null;
    }

    public static Song getSong(String id) {
        String url = "https://music.163.com/api/song/detail/?id=" + id + "&ids=[" + id + "]";
        JsonNode jsonRoot = curl(url, null, "netease", HttpMethod.GET);

        // Thanks to https://github.com/maicong/music/blob/master/music.php
        String otherUrl = "https://music.163.com/api/song/enhance/player/url?ids=[" + id + "]&br=320000";
        JsonNode otherJson = curl(otherUrl, null, "netease", HttpMethod.GET);
        Song res = new Song();
        if (jsonRoot != null) {
            if (jsonRoot.get("songs") != null) {
                jsonRoot = jsonRoot.get("songs").get(0);
                JsonNode node = jsonRoot.get("id");
                if (node != null) {
                    res.setId(node.asText());
                } else return null;
                node = jsonRoot.get("name");
                if (node != null) {
                    res.setName(node.asText());
                }
                ArrayList<String> artists = new ArrayList<>();
                node = jsonRoot.get("artists");
                node.forEach((JsonNode son) -> {
                    artists.add(son.get("name").asText());
                });
                res.setArtists(artists);

                //getLinkInfo
                res.setLink(null);
                if (otherJson != null && otherJson.get("data") != null && otherJson.get("data").get(0) != null && otherJson.get("data").get(0).get("url") != null) {
                    res.setLink(otherJson.get("data").get(0).get("url").asText());
                } else {
                    if (jsonRoot.get("hMusic") != null && jsonRoot.get("hMusic").get("dfsId") != null && jsonRoot.get("hMusic").get("dfsId").asInt() != 0) {
                        res.setLink("https://p2.music.126.net/" + encrpyId(jsonRoot.get("hMusic").get("dfsId").asText()) + "/" + jsonRoot.get("hMusic").get("dfsId").asText() + ".mp3");
                    } else if (jsonRoot.get("mMusic") != null && jsonRoot.get("mMusic").get("dfsId") != null && jsonRoot.get("mMusic").get("dfsId").asInt() != 0) {
                        res.setLink("https://p2.music.126.net/" + encrpyId(jsonRoot.get("mMusic").get("dfsId").asText()) + "/" + jsonRoot.get("mMusic").get("dfsId").asText() + ".mp3");
                    } else if (jsonRoot.get("bMusic") != null && jsonRoot.get("bMusic").get("dfsId") != null && jsonRoot.get("bMusic").get("dfsId").asInt() != 0) {
                        res.setLink("https://p2.music.126.net/" + encrpyId(jsonRoot.get("bMusic").get("dfsId").asText()) + "/" + jsonRoot.get("bMusic").get("dfsId").asText() + ".mp3");
                    } else if (jsonRoot.get("mp3Url") != null) {
                        res.setLink(jsonRoot.get("mp3Url").asText().replace("http://m2", "http://p2"));
                    }
                }
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
                    node = jsonRoot.get("picUrl");
                    if (node != null) {
                        res.setPicUrl(node.asText());
                    }
                }
                if (res.getPicUrl() != null) {
                    res.setPicUrl(res.getPicUrl().replace("http://", "https://"));
                }

                //getLyricsInfo
                url = "https://music.163.com/api/song/media?id=" + id;
                jsonRoot = curl(url, null, "netease", HttpMethod.GET);
                if (jsonRoot != null && jsonRoot.get("lyric") != null) {
                    res.setLyrics(jsonRoot.get("lyric").asText());
                }

                //geTLyricsInfo
                url = "https://music.163.com/api/song/lyric?tv=-1&id=" + id;
                jsonRoot = curl(url, null, "netease", HttpMethod.GET);
                if (jsonRoot != null && jsonRoot.get("tlyric") != null && jsonRoot.get("tlyric").get("lyric") != null) {
                    res.setTranslations(jsonRoot.get("tlyric").get("lyric").asText());
                }
                return res;
            } else {
                res.setId(null);
                return res;
            }
        } else return null;
    }

    public static ArrayList<Song> searchSongs(String name) {
        String url = "http://music.163.com/api/search/pc";
        MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
        m.add("offset", "0");
        m.add("limit","99");
        m.add("type","1");
        m.add("s",name);
        JsonNode rootRaw = curl(url,m, "netease", HttpMethod.POST);
        System.out.println(rootRaw.toString());
        ArrayList<Song> songs = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("result") != null) {
            rootRaw = rootRaw.get("result");
            if (rootRaw.get("songCount") != null) {
                if (rootRaw.get("songCount").asInt() == 0) {
                    songs.add(null);
                    return songs;
                } else {
                    rootRaw = rootRaw.get("songs");
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
                        node = jsonRoot.get("artists");
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
                            node = jsonRoot.get("picUrl");
                            if (node != null) {
                                res.setPicUrl(node.asText());
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
