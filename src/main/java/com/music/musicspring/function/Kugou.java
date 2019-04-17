package com.music.musicspring.function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.musicspring.entity.Album;
import com.music.musicspring.entity.Playlist;
import com.music.musicspring.entity.Song;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Base64;

import static com.music.musicspring.function.Functions.*;

public class Kugou {

    public static Album getAlbum(String id){

        String url = "http://mobilecdn.kugou.com/api/v3/album/song?plat=2&page=1&pagesize=-1&version=8990&area_code=1&albumid=" + id;
        JsonNode root = curl(url, null, "kugou", HttpMethod.GET);
        System.out.println(root.toString());
        Album res = new Album();
        if (root != null) {
            root = root.get("data");
            if (root != null) {
                JsonNode json = null;
                res.setId(id);
                json = root.get("total");
                if (json != null) {
                    res.setSize(json.asInt());
                } else return null;
                json = root.get("info");
                ArrayList<Song> songs = new ArrayList<>();
                json.forEach((JsonNode node) -> {
                    Song song = new Song();
                    song.setAlbumId(res.getId());
                    String sid = "{\"hash\":\""+node.get("hash").asText()+"\",\"album_id\":\""+id+"\"}";
                    sid = urlsafeB64encode(sid);
                    String[] filename = node.get("filename").asText().split(" - ");
                    String[] artistname = filename[0].split("、");

                    song.setName(filename[1]);
                    song.setId(sid);
                    song.setLink(null);
                    song.setLyrics(null);
                    song.setTranslations(null);
                    ArrayList<String> artists = new ArrayList<>();
                    for (String i:artistname) {
                        artists.add(i);
                    }
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
        String url = "http://mobilecdn.kugou.com/api/v3/search/album?page=1&pagesize=99&keyword="+name;
        JsonNode rootRaw = curl(url,null,"kugou",HttpMethod.GET);
        ArrayList<Album> albums = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("data") != null){
            rootRaw = rootRaw.get("data");
            if(rootRaw.get("total") != null){
                if(rootRaw.get("total").asInt() == 0){
                    albums.add(null);
                    return  albums;
                }else{
                    rootRaw = rootRaw.get("info");
                    System.out.println(rootRaw.toString());
                    rootRaw.forEach((JsonNode root) -> {
                        Album res = new Album();
                        JsonNode json = null;
                        json = root.get("albumid");
                        if (json != null) {
                            res.setId(json.asText());
                        }
                        json = root.get("publishtime");
                        if (json != null) {
                            res.setPublishTime(json.asText().split("\\s+")[0]);
                        }
                        json = root.get("albumname");
                        if (json != null) {
                            res.setName(json.asText());
                        }
                        json = root.get("singername");
                        if (json != null) {
                            res.setArtist(json.asText());
                        }
                        json = root.get("songcount");
                        if (json != null) {
                            res.setSize(json.asInt());
                        }
                        json = root.get("imgurl");
                        if (json != null) {
                            res.setPicUrl(json.asText().replace("{size}","400"));
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
        String url = "http://mobilecdn.kugou.com/api/v3/special/song?plat=0&&page=1&pagesize=-1&version=8990&area_code=1&specialid="+id;
        JsonNode root = curl(url,null,"kugou",HttpMethod.GET);
        Playlist res = new Playlist();
        if (root != null) {
            root = root.get("data");
            if (root != null) {
                JsonNode json = null;
                res.setId(id);
                json = root.get("total");
                if (json != null) {
                    res.setSize(json.asInt());
                } else return null;
                json = root.get("info");
                ArrayList<Song> songs = new ArrayList<>();
                json.forEach((JsonNode node) -> {
                    Song song = new Song();
                    song.setAlbumId(node.get("album_id").asText());
                    String[] filename = node.get("filename").asText().split(" - ");
                    String[] artistname = filename[0].split("、");
                    for (String x:filename) {
                        System.out.println(x);
                    }
                    String sid = "{\"hash\":\""+node.get("hash").asText()+"\",\"album_id\":\""+id+"\"}";
                    sid = urlsafeB64encode(sid);
                    song.setName(filename[1]);
                    song.setId(sid);
                    song.setLink(null);
                    song.setLyrics(null);
                    song.setTranslations(null);
                    ArrayList<String> artists = new ArrayList<>();
                    for (String i:artistname) {
                        artists.add(i);
                    }
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

    public static ArrayList<Playlist> searchPlaylists(String name){
        String url = "http://specialsearch.kugou.com/special_search?platform=WebFilter&page=1&pagesize=99&iscorrection=1&keyword="+name;
        JsonNode rootRaw = curl(url,null,"kugou",HttpMethod.GET);
        ArrayList<Playlist> playlists = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("data") != null){
            rootRaw = rootRaw.get("data");
            if(rootRaw.get("total") != null){
                if(rootRaw.get("total").asInt() == 0){
                    playlists.add(null);
                    return  playlists;
                }else{
                    rootRaw = rootRaw.get("lists");
                    System.out.println(rootRaw.toString());
                    rootRaw.forEach((JsonNode root) -> {
                        Playlist res = new Playlist();
                        JsonNode json = null;
                        json = root.get("specialid");
                        if (json != null) {
                            res.setId(json.asText());
                        }
                        json = root.get("specialname");
                        if (json != null) {
                            res.setName(json.asText());
                        }
                        json = root.get("img");
                        if(json != null){
                            res.setPicUrl(json.asText());
                        }
                        json = root.get("nickname");
                        if(json != null){
                            res.setCreator(json.asText());
                        }
                        json = root.get("song_count");
                        if(json != null){
                            res.setSize(json.asInt());
                        }
                        res.setSongs(null);
                        playlists.add(res);
                    });
                    return playlists;
                }
            }else return null;
        }else return null;
    }

    public static Song getSong(String id) {
        String hash = null;
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode json = m.readTree(urlsafeB64decode(id));
            hash = json.get("hash").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "http://m.kugou.com/app/i/getSongInfo.php?cmd=playInfo&from=mkugou&hash=" + hash;
        JsonNode jsonRoot = curl(url, null, "netease", HttpMethod.GET);

        Song res = new Song();
        if (jsonRoot != null) {
            res.setId(id);
            String[] filename = jsonRoot.get("fileName").asText().split(" - ");
            String[] artistname = filename[0].split("、");

            res.setName(filename[1]);

            ArrayList<String> artists = new ArrayList<>();
            for (String i : artistname) {
                artists.add(i);
            }
            res.setArtists(artists);

            //getLinkInfo

            res.setLink(null);
            JsonNode node = jsonRoot.get("url");
            if (node != null) {
                res.setLink(node.asText());
            }

            //getAlbumInfo

            node = jsonRoot.get("albumid");
            if (node != null) {
                res.setAlbumId(node.asText());
            }

            res.setAlbumName(null);

            node = jsonRoot.get("album_img");
            if (node != null) {
                res.setPicUrl(node.asText().replace("{size}","400"));
            }

            //getLyricsInfo
            url = "http://krcs.kugou.com/search?keyword=%20-%20&ver=1&client=mobi&man=yes&hash=" + hash;

            jsonRoot = curl(url, null, "qq", HttpMethod.GET);
            if (jsonRoot != null && jsonRoot.get("candidates") != null && jsonRoot.get("candidates").get(0) != null ) {
                String otherUrl = "http://lyrics.kugou.com/download?charset=utf8&client=mobi&fmt=lrc&ver=1&accesskey="+jsonRoot.get("candidates").get(0).get("accesskey").asText()+"&id="+jsonRoot.get("candidates").get(0).get("id").asText();
                System.out.println(otherUrl);
                jsonRoot = curl(otherUrl, null, "qq", HttpMethod.GET);
                if(jsonRoot != null) {
                    //System.out.println(jsonRoot.toString());
                    res.setLyrics(new String(Base64.getDecoder().decode(jsonRoot.get("content").asText().getBytes())));
                }
            }

            return res;
        }else return null;
    }

    public static ArrayList<Song> searchSongs(String name){
        String url = "http://mobilecdn.kugou.com/api/v3/search/song?api_ver=1&area_code=1&correct=1&plat=2&tag=1&sver=5&showtype=10&version=8990&page=1&pagesize=30&keyword="+name;
        JsonNode rootRaw = curl(url,null, "kugou", HttpMethod.POST);
        System.out.println(rootRaw.toString());
        ArrayList<Song> songs = new ArrayList<>();
        if(rootRaw != null && rootRaw.get("data") != null ) {
            rootRaw = rootRaw.get("data");
            if (rootRaw.get("total") != null) {
                if (rootRaw.get("total").asInt() == 0) {
                    songs.add(null);
                    return songs;
                } else {
                    rootRaw = rootRaw.get("info");
                    rootRaw.forEach((JsonNode node) -> {
                        Song song = new Song();
                        song.setAlbumId(node.get("album_id").asText());
                        String sid = "{\"hash\":\""+node.get("hash").asText()+"\",\"album_id\":\""+node.get("album_id").asText()+"\"}";
                        sid = urlsafeB64encode(sid);
                        String[] filename = node.get("filename").asText().split(" - ");
                        String[] artistname = filename[0].split("、");

                        song.setName(filename[1]);
                        song.setId(sid);
                        song.setLink(null);
                        song.setLyrics(null);
                        song.setTranslations(null);
                        ArrayList<String> artists = new ArrayList<>();
                        for (String i:artistname) {
                            artists.add(i);
                        }
                        song.setArtists(artists);
                        songs.add(song);
                    });
                    return songs;
                }
            }else return null;
        }
        return null;
    }

}
