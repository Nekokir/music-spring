var playlist = (function(){
    var song_list = document.getElementById("search-list-song"),
        album_list = document.getElementById("search-list-album"),
        playlist_list = document.getElementById("search-list-playlist"),
        show_list = document.getElementById('playlist_album_show_list');

    var search_song_button = document.getElementById('search_song_button'),
        search_album_button = document.getElementById('search_album_button'),
        search_playlist_button = document.getElementById('search_playlist_button');
    
    var search_song_input = document.getElementById('song'),
        search_album_input = document.getElementById('album'),
        search_playlist_input = document.getElementById('playlist');
    
    var search_origin_list = document.getElementById('search_origin_option').getElementsByTagName('li');

    var search_container = document.getElementById('search_tabs_container'),
        show_container = document.getElementById('playlist_album_show_conatiner'),
        tabs = document.getElementById('search_tabs').getElementsByTagName('a');

    var PA_info_img = document.getElementById('show_info_img'),
        PA_info_name = document.getElementById('show_info_name');

    var lists = {};
    lists['song'] = song_list;
    lists['album'] = album_list;
    lists['playlist'] = playlist_list;
    lists['show'] = show_list;

    var click_li_song, click_li_album, click_li_playlist;
    var click_s_song, click_s_album, click_s_playlist;

    var cur_origin = 'netease';

    function createLi(list, id, site, numberStr, upStr, bottomStr, iconType, iconData = null){
        var li = document.createElement("li"),
            number = document.createElement("h4"),
            span = document.createElement("span"),
            icon = document.createElement("i"),
            up = document.createElement("span"),
            bottom = document.createElement("span");

        li.classList.add("mdl-list__item");
        li.classList.add("mdl-list__item--two-line");
        li.appendChild(number);
        li.appendChild(span);
        li.setAttribute('object_id', id);
        li.setAttribute('site', site);

        number.innerHTML = numberStr;

        up.classList.add("maxlen");
        up.innerHTML = upStr;

        bottom.classList.add("mdl-list__item-sub-title");
        bottom.classList.add("maxlen");
        bottom.innerHTML = bottomStr;

        icon.classList.add("material-icons");
        icon.classList.add("mdl-list__item-avatar");
        if(iconData == null){
            icon.innerHTML = iconType;
        }else{
            icon.innerText = iconType;
            icon.classList.add("mdl-badge");
            icon.classList.add("mdl-badge--overlap");
            icon.setAttribute("data-badge", iconData);
        }
        

        span.classList.add("mdl-list__item-primary-content");
        span.appendChild(icon);
        span.appendChild(up);
        span.appendChild(bottom);

        list.appendChild(li);

        return span;
    }
    function addSongDOM(numberStr, songName, artists, id, site){
        let artistsStr = '';
        for(let artist of artists){
            artistsStr += artist;
        }
        createLi(song_list, id, site, numberStr, songName, artistsStr, "music_note");
    }
    function addShowDOM(numberStr, songName, artists, id, site){
        let artistsStr = '';
        for(let artist of artists){
            artistsStr += artist;
        }
        createLi(show_list, id, site, numberStr, songName, artistsStr, "music_note");
    }
    function addAlbumDOM(numberStr, albumName, artists, time, id, site){
        let artistsStr = '';
        for(let artist of artists){
            artistsStr += artist;
        }
        createLi(album_list, id, site, numberStr, albumName, artistsStr + "@" + time, "album");
    }
    function addPLDOM(numberStr, albumName, singerName, size, id, site){

        createLi(playlist_list, id, site, numberStr, albumName, singerName, "list", size);
    }

    function fillShowInfo(type, name, picUrl, artist, time){
        if(picUrl !== null){
            PA_info_img.src = picUrl;
        }else{

        }

        PA_info_name.innerHTML = '<span class=\'FPA-type\'>' + type + '</span>' + name;
    }

    function getObejctID(target){
        var nodeName = target.nodeName.toUpperCase(),
            li = null;
        
        if(nodeName === 'LI'){
            li = target;
        }else if(nodeName === 'h4' || target.classList.contains('mdl-list__item-primary-content')){
            li = target.parentNode;
        }else{
            li = target.parentNode.parentNode;
        }

        return {
            id : li.getAttribute('object_id'),
            site : li.getAttribute('site')
        };
    }
    
    song_list.addEventListener('click', function(event){
        var o = getObejctID(event.target);
        click_li_song(o.id, o.site);
    });
    album_list.addEventListener('click', function(event){
        var o = getObejctID(event.target);
        click_li_album(o.id, o.site);
    });
    playlist_list.addEventListener('click', function(event){
        var o = getObejctID(event.target);
        click_li_playlist(o.id, o.site);
    });
    show_list.addEventListener('click', function(event){
        var o = getObejctID(event.target);
        click_li_song(o.id, o.site);
    });

    search_song_button.onclick = function(){
        click_s_song(document.getElementById('song').innerHTML, cur_origin);
    };
    search_album_button.onclick = function(){
        click_s_album(document.getElementById('album').innerHTML, cur_origin);
    };
    search_playlist_button.onclick = function(){
        click_s_playlist(document.getElementById('playlist').innerHTML, cur_origin);
    };
    document.getElementById('show_to_search').onclick = function(){
        search_container.classList.remove('hidden');
                show_container.classList.add('hidden');
    };

    search_song_input.onkeydown = function(event){
        var val = search_song_input.value;
        if(event.keyCode === 13 && val !== ''){
            click_s_song(val, cur_origin);
        }
    };
    search_album_input.onkeydown = function(event){
        var val = search_album_input.value;
        if(event.keyCode === 13 && val !== ''){
            click_s_album(val, cur_origin);
        }
    };
    search_playlist_input.onkeydown = function(event){
        var val = search_playlist_input.value;
        if(event.keyCode === 13 && val !== ''){
            click_s_playlist(val, cur_origin);
        }
    };

    search_origin_list[0].onclick = function(){
        cur_origin = 'netease';
        document.getElementById('demo-menu-lower-right').innerHTML = '网易云';
    };
    search_origin_list[1].onclick = function(){
        cur_origin = 'qq';
        document.getElementById('demo-menu-lower-right').innerHTML = 'qq';
    };
    search_origin_list[2].onclick = function(){
        cur_origin = 'kugou';
        document.getElementById('demo-menu-lower-right').innerHTML = '酷狗';
    };
    search_origin_list[3].onclick = function(){
        cur_origin = 'xiami';
        document.getElementById('demo-menu-lower-right').innerHTML = '虾米';
    };

    return {
        fill : function(shit, type, sub){
            
            switch(type){
                //填充单曲的搜索结果
                case 'song':
                this.clear(type);
                for(let i = 0; i < shit.length;){
                    let song = shit[i];
                    addSongDOM(++i, song.name, song.artists, song.id, sub);
                }
                break;
                //填充专辑的搜索结果
                case 'album':
                this.clear(type);
                if(sub === null){
                    for(let i = 0; i < shit.length;){
                        let album = shit[i];
                        addAlbumDOM(++i, album.name, album.artist, album.publishTime, album.id, album.site);
                    }
                }else{
                    for(let i = 0; i < shit.length;){
                        let album = shit[i];
                        addAlbumDOM(++i, album.name, album.artist, album.publishTime, album.id, sub);
                    }
                }
                
                break;
                //填充歌单的搜索结果
                case 'playlist':
                this.clear(type);
                if(sub === null){
                    for(let i = 0; i < shit.length;){
                        let list = shit[i];
                        addPLDOM(++i, list.name, list.creator, list.size, list.id, list.site);
                    }
                }else{
                    for(let i = 0; i < shit.length;){
                        let list = shit[i];
                        addPLDOM(++i, list.name, list.creator, list.size, list.id, sub);
                    }
                }
                break;
                case 'show':{
                    this.clear(type);
                    fillShowInfo('like', "收藏的音乐", null, 0, 0);
                    //let songs = shit.songs;
                    for(let i = 0; i < shit.length;){
                        let song = shit[i];
                        addShowDOM(++i, song.name, song.artists, song.id, song.site);
                    }
                    break;
                }
                //显示这个专辑的内容
                case 'show-album':{
                    this.clear('show');
                    fillShowInfo('专辑', shit.name, shit.picUrl, shit.artist, shit.publishTime);
                    let songs = shit.songs;
                    for(let i = 0; i < songs.length;){
                        let song = songs[i];
                        addShowDOM(++i, song.name, song.artists, song.id, sub);
                    }
                    break;
                }
                 //显示这个歌单的内容
                 case 'show-playlist':{
                    this.clear('show');
                    fillShowInfo('歌单', shit.name, shit.picUrl, shit.creator, 0);
                    let songs = shit.songs;
                    for(let i = 0; i < songs.length;){
                        let song = songs[i];
                        addShowDOM(++i, song.name, song.artists, song.id, sub);
                    }
                    break;
                 }
                 
            }
            
            
        },
        clear : function(target){
            var list = lists[target];
            if(list != null){
                list.innerHTML = '';
            }else{

            }
        },
        jump : function(target, subTarget){
            if(target === 'search'){
                search_container.classList.remove('hidden');
                show_container.classList.add('hidden');
                
                if(subTarget === 'a'){
                    tabs[1].click();
                }else if(subTarget === 'p'){
                    tabs[2].click();
                }
            }else{
                show_container.classList.remove('hidden');
                search_container.classList.add('hidden');
            }
        },
        setCallback : function(list_song, list_album, list_playlist, s_song, s_album, s_playlist){
            click_li_song = list_song;
            click_li_album = list_album;
            click_li_playlist = list_playlist;

            click_s_song = s_song;
            click_s_album = s_album;
            click_s_playlist = s_playlist;
        },
        hiddenSearch : function(){
            search_container.classList.add('search-top-hidden');
        },
        showSearch : function(){
            search_container.classList.remove('search-top-hidden');
        }
    };
})();


