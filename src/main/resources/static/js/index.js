var shit = (function(player, playlist, account, personal_info, Mobile){
    var test = false;
    var url = {
        add_favor_song : 'add_favor_song',
        add_favor_album : 'add_favor_album',
        add_favor_playlist : 'add_favor_playlist',
        get_favor_song : 'add_favor_song',
        get_favor_album : 'add_favor_album',
        get_favor_playlist : 'add_favor_playlist',
        logout : 'test/logout.json',
        login : 'test/login.json',
        reg : 'test/login.json',
        songById : 'test/song.json',
        songByName : 'test/song.json',
        album : 'test/album.json',
        playlist : 'test/playlist.json',
        like_song : '',
        search_song : 'test/search_song.json',
        search_album : 'test/search_album.json',
        search_playlist : 'test/search_pl.json'
    };
    if(!test){
        url = {
            add_favor_song : 'add_favor_song',
            add_favor_album : 'add_favor_album',
            add_favor_playlist : 'add_favor_playlist',
            get_favor_song : 'get_favor_song',
            get_favor_album : 'get_favor_album',
            get_favor_playlist : 'get_favor_playlist',
            del_song : 'delete_favor_song',
            del_al : 'delete_favor_album',
            del_pl : 'delete_favor_playlist',
            logout : 'logout',
            login : 'login',
            reg : 'reg',
            songById : 'song',
            songByName : '',
            album : 'album',
            playlist : 'playlist',
            like_song : 'add_favor',
            search_song : 'search_song',
            search_album : 'search_album',
            search_playlist : 'search_playlist'
        };
    }

    // mobile
    var playerDOM = document.getElementsByClassName('shit-player')[0],
        searchDOM = document.getElementById('search_container').parentNode,
        button_switch_page = document.getElementById('button_switch_page'),
        is_player_show = true;
    if(Mobile.isMobile()){
        document.getElementsByTagName('footer')[0].classList.add('hidden');
        button_switch_page.classList.remove('hidden');

        //searchDOM.classList.add('switch-search');

        //switch
        searchDOM.classList.add('hidden');
        button_switch_page.onclick = function(){
            if(is_player_show){
                button_switch_page.innerHTML = 'keyboard_return';
                searchDOM.classList.remove('hidden');
                playerDOM.classList.add('hidden');
                is_player_show = false;
            }else{
                button_switch_page.innerHTML = 'search';
                searchDOM.classList.add('hidden');
                playerDOM.classList.remove('hidden');
                is_player_show = true;
            }
        }
    }
    /*var playerDOM = document.getElementsByClassName('shit-player')[0],
        searchDOM = document.getElementById('search_container').parentNode,
        button_switch_page = document.getElementById('button_switch_page'),
        is_player_show = true;
    if(Mobile.isMobile()){
        document.getElementsByTagName('footer')[0].classList.add('hidden');
        button_switch_page.classList.remove('hidden');

        //searchDOM.classList.add('hidden');
        button_switch_page.onclick = function(){
            if(is_player_show){
                button_switch_page.setAttribute('href', '#target_player');
                is_player_show = false;
            }else{
                button_switch_page.setAttribute('href', '#target_search');
                is_player_show = true;
            }
        }
    }*/
    function handle_connection_fail(res){
        Dialog.normal('网络连接有些问题....');
    }
    function handle_server_wrong(data){
        if('code' in data){
            if(data.code === -2){
                Dialog.normal('服务器出了些问题....');
                return false;
            }
        }else{
            Dialog.normal('发生了蜜汁错误....');
        }

        return true;
    }

    var is_login = false;

    function isCurSongFavor(urlStr, id, site, userId, func) {
        $.ajax({
            url : urlStr + '?userId=' + userId,
            success : function (res) {
                if(res.success === true){
                    var list = res.data,
                        has = false;
                    console.log(id + ' ' + site);
                    for(var i = 0; i < list.length; i++){
                        console.log(list[i]);
                        if(list[i].id === id && list[i].site === site){
                            has = true;
                            console.log('find');
                            console.log(list[i]);
                            break;
                        }
                    }
                    func(has);
                }else if(handle_server_wrong(res.data)){

                }
            },
            error : handle_connection_fail
        });
    }

    player.init();
    player.displayFavor(false);
    playlist.displayFavor(false);

    player.setCallback(
        function(id, site, name, artists){
            if(is_login){
                $.ajax({
                    url : url.add_favor_song + '?id=' + id + '&site=' + site + '&userId=' + personal_info.useId() + '&name=' + name + '&artists=' + artists,
                    success : function(res){
                        if(res.success === true){
                            Dialog.normal("收藏成功");
                            player.switchFavorIcon(true);
                        }else if(handle_server_wrong(res.data)){
                            if('reason' in res.data){
                                Dialog.normal("收藏失败: " + res.data.reason);
                            }
                        }
                    },
                    error : handle_connection_fail
                });
            }else{
                Dialog.normal("还未登录");
            }

            //playlist.hiddenSearch();
        },
        function(id, site){
            $.ajax({
                url : url.album + '?id=' + id + '&site=' + site,
                success : function(res){
                    if(res.success === true){
                        playlist.jump('jump');
                        playlist.fill(res.data, 'show-album');
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
            });
            //playlist.hiddenSearch();
        },
        function(id, site){
            $.ajax({
                url : url.playlist + '?id=' + id + '&site=' + site,
                success : function(res){
                    if(res.success === true){
                        playlist.jump('jump');
                        playlist.fill(res.data, 'show-playlist');
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
            });
            //playlist.hiddenSearch();
        },
        function (id, site) {
            if(is_login){
                $.ajax({
                    url : url.del_song + '?site=' + site + '&id=' + id + '&userId=' + personal_info.useId(),
                    success : function(res){
                        if(res.success === true){
                            Dialog.normal('取消收藏成功');
                            player.switchFavorIcon(false);
                        }else if(handle_server_wrong(res.data)){
                            if('reason' in res.data){

                            }
                        }
                    },
                    error : handle_connection_fail

                });
            }
        },
        function (id, site) {
            isCurSongFavor(url.get_favor_song, id, site, personal_info.useId(), function (has) {
                player.switchFavorIcon(has);
            });
        }
    );

    playlist.setCallback(
        //点击歌曲列表中的歌曲并播放
        function(id, site){
            $.ajax({
                url : url.songById + '?id=' + id + '&site=' + site,
                success : function(res){
                    if(res.success === true){
                        player.set(res.data, site, is_login);
                        if(Mobile.isMobile()){
                            button_switch_page.click();
                        }
                        if(is_login){
                            isCurSongFavor(url.get_favor_song, id, site, personal_info.useId(), function (has) {
                                player.switchFavorIcon(has);
                            });
                        }

                       
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }else{
                            if(res.data.link === null){
                                //版权
                                Dialog.normal('这首歌有版权, 不能播放...');
                            }
                        }
                    }
                },
                error : handle_connection_fail
            });

        },
        //点击专辑列表中的专辑，获取专辑中歌曲
        function(id, site){
            $.ajax({
                url : url.album + '?id=' + id + '&site=' + site,
                success : function(res){
                    if(res.success === true){
                        playlist.jump('jump');
                        playlist.fill(res.data, 'show-album', site);
                        if(is_login){
                            isCurSongFavor(url.get_favor_album, id, site, personal_info.useId(), function (has) {
                                //player.switchFavorIcon(has);
                                playlist.switchFavorIcon(has);
                            });
                        }
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
            });
        },
        //同上，歌单
        function(id, site){
            $.ajax({
                url : url.playlist + '?id=' + id + '&site=' + site,
                success : function(res){
                    if(res.success === true){
                        playlist.jump('jump');
                        playlist.fill(res.data, 'show-playlist', site);
                        if(is_login){
                            isCurSongFavor(url.get_favor_playlist, id, site, personal_info.useId(), function (has) {
                                //player.switchFavorIcon(has);
                                playlist.switchFavorIcon(has);
                            });
                        }
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
            });
        },
        //search song
        function(name, origin){
            $.ajax({
                url : url.search_song + '?name=' + name + '&site=' + origin,
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'song', origin);
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
                
            });
        },
        function(name, origin){
            $.ajax({
                url : url.search_album + '?name=' + name + '&site=' + origin,
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'album', origin);
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
                
            });
        },
        function(name, origin){
            $.ajax({
                url : url.search_playlist + '?name=' + name + '&site=' + origin,
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'playlist', origin);
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
                
            });
        },
        function (id, site, name, artist, size, publishtime) {
            if(is_login){
                $.ajax({
                    url : url.add_favor_album + '?name=' + name + '&site=' + site + '&id=' + id + '&artist='
                    + artist + '&size=' + size + '&userId=' + personal_info.useId()
                    + '&publishtime=' + publishtime,
                    success : function(res){
                        if(res.success === true){
                            Dialog.normal('收藏成功');
                            playlist.switchFavorIcon(true);
                        }else if(handle_server_wrong(res.data)){
                            if('reason' in res.data){

                            }
                        }
                    },
                    error : handle_connection_fail

                });
            }
        },
        function (id, site, name, creator, size, publishtime) {
            if(is_login){
                $.ajax({
                    url : url.add_favor_playlist + '?name=' + name
                    + '&site=' + site + '&id=' + id + '&creator=' + creator + '&size=' + size
                    + '&userId=' + personal_info.useId(),
                    success : function(res){
                        if(res.success === true){
                            Dialog.normal('收藏成功');
                            playlist.switchFavorIcon(true);
                        }else if(handle_server_wrong(res.data)){
                            if('reason' in res.data){

                            }
                        }
                    },
                    error : handle_connection_fail

                });
            }
        },
        function (id, site) {
            if(is_login){
                $.ajax({
                    url : url.del_al + '?site=' + site + '&id=' + id + '&userId=' + personal_info.useId(),
                    success : function(res){
                        if(res.success === true){
                            Dialog.normal('取消收藏成功');
                            playlist.switchFavorIcon(false);
                        }else if(handle_server_wrong(res.data)){
                            if('reason' in res.data){

                            }
                        }
                    },
                    error : handle_connection_fail

                });
            }
        },
        function (id, site) {
            if(is_login){
                $.ajax({
                    url : url.del_pl + '?site=' + site + '&id=' + id + '&userId=' + personal_info.useId(),
                    success : function(res){
                        if(res.success === true){
                            Dialog.normal('取消收藏成功');
                            playlist.switchFavorIcon(false);
                        }else if(handle_server_wrong(res.data)){
                            if('reason' in res.data){

                            }
                        }
                    },
                    error : handle_connection_fail

                });
            }
        },
        function (id, site) {
            isCurSongFavor(url.get_favor_album, id, site, personal_info.useId(), function (has) {
                playlist.switchFavorIcon(has);
            });
        },
        function (id, site) {
            isCurSongFavor(url.get_favor_playlist, id, site, personal_info.useId(), function (has) {
                playlist.switchFavorIcon(has);
            });
        }
    );

    account.setCallback(
        function(username, password){
            $.ajax({
                url : url.login + '?userId=' + username + '&loginPwd=' + md5(password),
                success : function(res){
                    if(res.success === true){
                        account.hidden();
                        personal_info.show();
                        personal_info.set(username, 20, 3, 5);
                        playlist.displayFavor(true);
                        //player.displayFavor(true);
                        if(player.hasSong()){
                            player.displayFavor(true);
                            player.checkFavor();
                        }
                        playlist.checkFavor();
                        is_login = true;
                    }else{
                        Dialog.normal(res.data.reason);
                    }
                },
                error : handle_connection_fail
            });
        },
        function(username, password){
            $.ajax({
                url : url.reg + '?userId=' + username + '&loginPwd=' + md5(password),
                success : function(res){
                    if(res.success === true){
                        account.hidden();
                        personal_info.show();
                        personal_info.set(username, 20, 3, 5);
                        playlist.displayFavor(true);
                        if(player.hasSong()){
                            player.displayFavor(true);
                            player.checkFavor();
                        }
                        playlist.checkFavor();
                        is_login = true;
                    }else{
                        Dialog.normal(res.data.reason);
                    }
                },
                error : handle_connection_fail
            });
        }
    );
    function getFavor(list) {

    }
    personal_info.setCallback(
        function(){
            $.ajax({
                url : url.get_favor_song + '?userId=' + personal_info.useId(),
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'show');
                        playlist.jump('show');

                        if(Mobile.isMobile()){
                            button_switch_page.click();
                        }

                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                   
                },
                error : handle_connection_fail
            });
            
        },
        function(){
            $.ajax({
                url : url.get_favor_album + '?userId=' + personal_info.useId(),
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'album', null);
                        playlist.jump('search', 'a');

                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
            });
        },
        function(){
            $.ajax({
                url : url.get_favor_playlist + '?userId=' + personal_info.useId(),
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'playlist', null);
                        playlist.jump('search', 'p');
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                error : handle_connection_fail
            });
        },
        //logout
        function(){
            $.ajax({
                url : url.logout,
                success : function(res){
                    /*if(res.success === true){
                        personal_info.hidden();
                        account.show();
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }*/
                    personal_info.hidden();
                    account.show();
                    is_login = false;
                    playlist.displayFavor(false);
                    player.displayFavor(false);
                },
                error : handle_connection_fail
            });
        }
    );
})(player, playlist, account, personal_info, Mobile);