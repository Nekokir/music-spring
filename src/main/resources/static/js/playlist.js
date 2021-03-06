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
        PA_info_name = document.getElementById('show_info_name'),
        PA_favor = document.getElementById('show_favor'),
        PA_show_type = '',
        PA_click_favor_album = null,
        PA_click_favor_playlist = null,
        PA_del_album = null,
        PA_del_playlist = null,
        PA_check_al = null,
        PA_check_pl = null,
        PA_name = '',
        PA_id = '',
        PA_site = '',
        PA_creator = '',
        PA_artist = '',
        PA_publishtime = '',
        PA_size = 0,
        PA_is_cur_favor = false,
        PA_default_pic = '../static/image/icon.png',
        PA_is_favor_enable = true;

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
        up.classList.add('text-ellipsis');
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
    function addFavorSongDOM(numberStr, songName, artists, id, site) {
        createLi(show_list, id, site, numberStr, songName, artists, "music_note");
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
        if(type === '专辑'){
            PA_show_type = 'album';
            //PA_favor.classList.remove('hidden');
        }else if(type === '歌单'){
            PA_show_type = 'playlist';
            //PA_favor.classList.remove('hidden');
        }else{
            PA_show_type = 'favor_song';
            //PA_favor.classList.add('hidden');
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

    PA_favor.onclick = function () {
        if(PA_is_favor_enable){
            if(PA_show_type === 'album'){
                if(!PA_is_cur_favor){
                    PA_click_favor_album(PA_id, PA_site, PA_name, PA_artist, PA_size, PA_publishtime);
                }else {
                    PA_del_album(PA_id, PA_site);
                }

            }else{
                if(!PA_is_cur_favor){
                    PA_click_favor_playlist(PA_id, PA_site, PA_name, PA_creator, PA_size);
                }else {
                    PA_del_playlist(PA_id, PA_site);
                }

            }
        }

    };

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
            click_s_song(val.replace(/\+/g,"%2B").replace(/\&/g,"%26"), cur_origin);
        }
    };
    search_album_input.onkeydown = function(event){
        var val = search_album_input.value;
        if(event.keyCode === 13 && val !== ''){
            click_s_album(val.replace(/\+/g,"%2B").replace(/\&/g,"%26"), cur_origin);
        }
    };
    search_playlist_input.onkeydown = function(event){
        var val = search_playlist_input.value;
        if(event.keyCode === 13 && val !== ''){
            click_s_playlist(val.replace(/\+/g,"%2B").replace(/\&/g,"%26"), cur_origin);
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
            if(shit.length === 1 && shit[0] === null){
                return;
            }
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
                case 'album-favor':
                    this.clear('album');
                    if(sub === null){
                        for(let i = 0; i < shit.length;){
                            let album = shit[i];
                            addAlbumDOM(++i, album.name, album.artist, album.publishtime, album.id, album.site);
                        }
                    }else{
                        for(let i = 0; i < shit.length;){
                            let album = shit[i];
                            addAlbumDOM(++i, album.name, album.artist, album.publishtime, album.id, sub);
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

                    //let songs = shit.songs;
                    if(shit.length !== 0){
                        for(let i = 0; i < shit.length;){
                            let song = shit[i];
                            addFavorSongDOM(++i, song.name, song.artists, song.id, song.site);
                        }
                        fillShowInfo('like', "收藏的音乐", PA_default_pic, 0, 0);
                    }else{
                        fillShowInfo('like', "收藏的音乐", PA_default_pic, 0, 0);
                    }

                    break;
                }
                //显示这个专辑的内容
                case 'show-album':{
                    this.clear('show');
                    fillShowInfo('专辑', shit.name, shit.picUrl, shit.artist, shit.publishTime);

                    let songs = shit.songs;

                    PA_id = shit.id;
                    PA_name = shit.name;
                    PA_size = songs.length;
                    PA_publishtime = shit.publishTime;
                    PA_site = sub;
                    PA_artist = shit.artist;

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

                     PA_id = shit.id;
                     PA_name = shit.name;
                     PA_size = songs.length;
                     PA_site = sub;
                     PA_creator = shit.creator;

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
        setCallback : function(list_song, list_album, list_playlist,
                               s_song, s_album, s_playlist,
                               favor_album, favor_playlist,
                               del_album, del_playlist,
                               check_al, check_pl
        ){
            click_li_song = list_song;
            click_li_album = list_album;
            click_li_playlist = list_playlist;

            click_s_song = s_song;
            click_s_album = s_album;
            click_s_playlist = s_playlist;

            PA_click_favor_album = favor_album;
            PA_click_favor_playlist = favor_playlist;

            PA_del_album = del_album;
            PA_del_playlist = del_playlist;

            PA_check_al = check_al;
            PA_check_pl = check_pl;
        },
        hiddenSearch : function(){
            search_container.classList.add('search-top-hidden');
        },
        showSearch : function(){
            search_container.classList.remove('search-top-hidden');
        },
        switchFavorIcon : function (isRed) {
           PA_is_cur_favor = isRed;
            if(isRed){
                PA_favor.innerHTML = '已收藏';
            }else{
                PA_favor.innerHTML = '收藏';
            }
        },
        displayFavor : function (display) {
            if(display){
                PA_favor.classList.remove('hidden');
            }else{
                PA_favor.classList.add('hidden');
            }
        },
        checkFavor : function () {
            if(PA_show_type === 'album'){
                PA_check_al(PA_id, PA_site);
            }else if(PA_show_type === 'playlist'){
                PA_check_pl(PA_id, PA_site);
            }
        },
        favorEnable : function (able) {
            PA_is_favor_enable = able;
        }
    };
})();


