var shit = (function(player, playlist, account, personal_info, Mobile){
    var test = true;
    var url = {
        favor_song : 'test/favor_song.json',
        favor_album : 'test/favor_album.json',
        favor_playlist : 'test/favor_playlist.json',
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
            favor_song : 'favor_song',
            favor_album : 'test/favor_album.json',
            favor_playlist : 'test/favor_playlist.json',
            logout : 'test/logout.json',
            login : 'test/login.json',
            songById : 'song',
            songByName : 'test/song.json',
            album : 'test/favor_song.json',
            playlist : 'test/favor_song.json',
            like_song : ''
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
        }

        return true;
    }

    player.init();

    player.setCallback(
        function(id, site){
            $.ajax({
               url : url.like_song + '?id=' + id + '&site=' + site,
                success : function(res){
                    if(res.success === true){
                        
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                fail : handle_connection_fail
            });
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
                fail : handle_connection_fail
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
                fail : handle_connection_fail
            });
            //playlist.hiddenSearch();
        }
    );

    playlist.setCallback(
        //点击歌曲列表中的歌曲并播放
        function(id, site){
            $.ajax({
                url : url.songById + '?id=' + id + '&site=' + site,
                success : function(res){
                    if(res.success === true){
                        player.set(res.data);
                        if(Mobile.isMobile()){
                            button_switch_page.click();
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
                fail : handle_connection_fail
            });

        },
        //点击专辑列表中的专辑，获取专辑中歌曲
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
                fail : handle_connection_fail
            });
        },
        //同上，歌单
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
                fail : handle_connection_fail
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
                fail : handle_connection_fail
                
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
                fail : handle_connection_fail
                
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
                fail : handle_connection_fail
                
            });
        }
    );

    account.setCallback(
        function(username, password){
            $.ajax({
                url : url.login + '?username=' + username + '&password=' + md5(password),
                success : function(res){
                    if(res.success === true){
                        account.hidden();
                        personal_info.show();
                        personal_info.set(username, 20, 3, 5);
                    }else{
                        
                    }
                },
                fail : handle_connection_fail
            });
        },
        function(username, password){
            $.ajax({
                url : url.reg + '?username=' + username + '&password=' + md5(password),
                success : function(res){
                    if(res.success === true){
                        account.hidden();
                        personal_info.show();
                        personal_info.set(username, 20, 3, 5);
                    }else{
                        
                    }
                },
                fail : handle_connection_fail
            });
        }
    );

    personal_info.setCallback(
        function(){
            $.ajax({
                url : url.favor_song,
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
                    console.log(res);
                   
                },
                fail : handle_connection_fail
            });
            
        },
        function(){
            $.ajax({
                url : url.favor_album,
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'album', null);
                        playlist.jump('search', 'a');
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                fail : handle_connection_fail
            });
        },
        function(){
            $.ajax({
                url : url.favor_playlist,
                success : function(res){
                    if(res.success === true){
                        playlist.fill(res.data, 'playlist', null);
                        playlist.jump('search', 'p');
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                fail : handle_connection_fail
            });
        },
        //logout
        function(){
            $.ajax({
                url : url.logout,
                success : function(res){
                    if(res.success === true){
                        personal_info.hidden();
                        account.show();
                    }else if(handle_server_wrong(res.data)){
                        if('reason' in res.data){

                        }
                    }
                },
                fail : handle_connection_fail
            });
        }
    );
})(player, playlist, account, personal_info, Mobile);