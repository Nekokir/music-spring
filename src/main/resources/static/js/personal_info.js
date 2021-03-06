var personal_info = (function(){
    var container = document.getElementById('personal_info_container'),
        username = document.getElementById('pi_username');

    var favor = document.getElementsByClassName('demo-list-item')[0].getElementsByTagName('i');
    
    var button_logout = document.getElementById('logout'),
        button_favor_song = document.getElementById('button_favor_song'),
        button_favor_album = document.getElementById('button_favor_album'),
        button_favor_playlist = document.getElementById('button_favor_playlist');

    var cur_useId = '';
    

    function setFavorNum(song, album, playlist){
        favor[0].setAttribute('data-badge', song);
        favor[1].setAttribute('data-badge', album);
        favor[2].setAttribute('data-badge', playlist);
    }
    function setFavorNumAt(num, index) {
        favor[index].setAttribute('data-badge', num);
    }
    return {
        show : function(){
            container.classList.remove('log-hidden');
            container.classList.add('log-show');
        },
        hidden : function(){
            container.classList.remove('log-show');
            container.classList.add('log-hidden');
        },
        set : function(name, favor_song, favor_album, favor_playlist){
            username.innerHTML = name;
            cur_useId = name;
            setFavorNum(0, 0, 0);
        },
        setCallback : function(favor_song, favor_album, favor_playlist, logout){
            button_logout.onclick = logout;
            button_favor_song.onclick = favor_song;
            button_favor_album.onclick = favor_album;
            button_favor_playlist.onclick = favor_playlist;
        },
        useId : function () {
            return cur_useId;
        },
        setFavorNum : function (a, i) {
            setFavorNumAt(a, i);
        }
    }
})();