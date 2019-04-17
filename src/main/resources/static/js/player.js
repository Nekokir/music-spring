var player = (function(ImgBlur, Mobile){
	//var player_music = document.getElementById('player_music');
	var player_music = document.createElement('audio'),
		duration,
		player_button = document.getElementById('player_button'),
		player_slider = document.getElementById('player_slider'),
		player_time = document.getElementById('player_time'),
		total_time = document.getElementById('total_time'),
		player_img = document.getElementById('player_img'),
		like = document.getElementById('player_like'),
		rectDOM = document.getElementById('main_player'),
		lrc_list = document.getElementById('lyrics_list'),
		lrc_container = document.getElementById('lyrics_container'),
		player_info = document.getElementById('player_info');

	var player_singer = document.getElementById('player_singer'),
		player_album = document.getElementById('player_album'),
		player_name = document.getElementById('player_name'),
		player_slider_table = document.getElementById('player');
	
	player_music.type = 'audio/mp3';

	var songId = 0, singerId = 0, player_albumId = 0, cur_site = 'netease', cur_artists = '', cur_song_name = '';
	var info_has_lyrics = false;

	var click_like_song, click_player_singer, click_player_album, click_del_song, check_cur_song_favor;

	var cur_light_lrc_dom = null, lrc_time_list = null, cur_time_index = 0;

	var mobile_is_info_show = true, mobile_rect_width = 0, mobile_rect_height = 0;

	var has_song_play = false, is_cur_song_favor = false;
	// Mobile
	if(Mobile.isMobile()){
		lrc_container.classList.add('hidden');

		var len = window.innerHeight
				- document.getElementsByTagName('header')[0].getBoundingClientRect().height
				- player_slider_table.getBoundingClientRect().height;
		player_info.style.height = len + 'px';
		lrc_container.style.height = len + 'px';

		rectDOM.style.marginTop = 0;

		console.log(window.innerHeight);
		player_img.onclick = function(){
			if(mobile_is_info_show){
				lrc_container.classList.remove('hidden');
				player_info.classList.add('hidden');
				mobile_is_info_show = false;
			}else{
				lrc_container.classList.add('hidden');
				player_info.classList.remove('hidden');
				mobile_is_info_show = true;
			}
		};
		lrc_container.onclick = function(){
			if(mobile_is_info_show){
				lrc_container.classList.remove('hidden');
				player_info.classList.add('hidden');
				mobile_is_info_show = false;
			}else{
				lrc_container.classList.add('hidden');
				player_info.classList.remove('hidden');
				mobile_is_info_show = true;
			}
		};
		var rect = rectDOM.getBoundingClientRect();
		mobile_rect_width = rect.width;
		mobile_rect_height = rect.height;
	}

	// blur
	if(ImgBlur.glReady === true){
		var blur = ImgBlur.DOM();

		rectDOM.appendChild(ImgBlur.DOM());
		blur.style.position = 'absolute';
		blur.setAttribute('style', 'position: absolute; z-index: -1;');
	
		player_img.onload = function(){

                if(Mobile.isMobile()){
                    ImgBlur.blur(player_img, player_img.naturalWidth, player_img.naturalHeight, mobile_rect_width, mobile_rect_height, 17, 70);
                }else{
                    var rect = rectDOM.getBoundingClientRect();
                    console.log(rect);
                    ImgBlur.blur(player_img, player_img.naturalWidth, player_img.naturalHeight, rect.width, rect.height, 17, 70);
                }


			
		}
	}
	

	
	

    if(player_button) {
	    player_button.addEventListener("click", play);
    }
	if(player_music) {
        player_music.addEventListener("timeupdate", timeUpdate, false);
    }
    if(player_slider) {
	    player_slider.addEventListener("click", function(event) {
		    player_music.currentTime = duration * clickPercent(event);
		    player_time.innerHTML = (Math.floor(player_music.currentTime / 60) < 10 ? '0' + Math.floor(player_music.currentTime / 60) : Math.floor(player_music.currentTime / 60)) + ':' + (Math.floor(player_music.currentTime % 60) < 10 ? '0' + Math.floor(player_music.currentTime % 60) : Math.floor(player_music.currentTime % 60));
	    }, false);
    }

	function clickPercent(event) {
		return player_slider.value / 100;
	}


    if(player_slider) {
	    player_slider.addEventListener('mousedown', mouseDown, false);
	    window.addEventListener('mouseup', mouseUp, false);
    }

	var onslider = false;

	function mouseDown() {
		onslider = true;
		player_music.removeEventListener('timeupdate', timeUpdate, false);
	}

	function mouseUp(event) {
		if (onslider == true) {
			player_music.currentTime = duration * clickPercent(event);
			player_music.addEventListener('timeupdate', timeUpdate, false);
		}
		onslider = false;
	}

	function timeUpdate() {
		var sliderPercent = player_music.currentTime / duration;
		player_slider.MaterialSlider.change(sliderPercent * 100);
		player_time.innerHTML = (Math.floor(player_music.currentTime / 60) < 10 ? '0' + Math.floor(player_music.currentTime / 60) : Math.floor(player_music.currentTime / 60)) + ':' + (Math.floor(player_music.currentTime % 60) < 10 ? '0' + Math.floor(player_music.currentTime % 60) : Math.floor(player_music.currentTime % 60));
		if (player_music.currentTime >= duration) {
			player_slider.MaterialSlider.change(0);
			player_time.innerHTML = "00:00"
		    player_button.innerHTML = "";
			player_button.innerHTML = "play_arrow";
		}

		let time = Math.floor(player_music.currentTime * 1000);
		if(info_has_lyrics && cur_time_index < lrc_time_list.length){
			
			let cur_lrc_time = lrc_time_list[cur_time_index].time,
				next_lrc_time = lrc_time_list[cur_time_index + 1].time;
			if(next_lrc_time < time){
				let dom = lrc_time_list[cur_time_index + 1].dom,
					cur_light_lrc_dom = lrc_time_list[cur_time_index].dom;
				if(cur_light_lrc_dom !== dom){
					cur_light_lrc_dom.classList.remove('lrc-light');			
					dom.classList.add('lrc-light');

					let offset = lrc_container.getBoundingClientRect().height * 0.35 - (dom.getBoundingClientRect().y - lrc_list.getBoundingClientRect().y);
					lrc_list.style.transform = 'translateY(' + offset + 'px)';

				}
				cur_time_index++;
			}

			
			
		}
	}

	function play() {
		if (player_music.paused) {
			player_music.play();
			player_button.innerHTML = "";
			player_button.innerHTML = "pause";
		} else {
			player_music.pause();
			player_button.innerHTML = "";
			player_button.innerHTML = "play_arrow";
		}
	}

	function fillLyricsList(tag, map, time_list){
		lrc_time_list = [];
		lrc_list.innerHTML = '';
		let first = true;
		let index = 0;
		for(let timeStr in map){
			let li = document.createElement('li');
			li.innerHTML = map[timeStr];
			lrc_list.appendChild(li);

			lrc_time_list[index] = {
				time : time_list[index++],
				dom : li
			};

			if(first){
				first = false;
				cur_light_lrc_dom = li;
				cur_light_lrc_dom.classList.add('lrc-light');
			}
		}
		let high = lrc_container.getBoundingClientRect().height;
		lrc_list.style.transform = 'translateY(' + high * 0.35 + 'px)';
		cur_time_index = 0;
	}

    if(player_music) {
	    player_music.addEventListener("canplaythrough", function() {
		    duration = player_music.duration;
		    total_time.innerHTML = (Math.floor(duration / 60) < 10 ? '0' + Math.floor(duration / 60) : Math.floor(duration / 60)) + ':' + (Math.floor(duration % 60) < 10 ? '0' + Math.floor(duration % 60) : Math.floor(duration % 60));
	    }, false);
    }
    function setMusic({
        link,
        picUrl,
        name,
        artists,
		albumName,
		albumId,
		id,
        lyrics = null,
    }, site, is_login){
		let artistsStr = '';
        for(let artist of artists){
            artistsStr += artist + ',';
        }
        player_music.src = link;
        player_album.innerHTML = 'album: ' + albumName;
        player_singer.innerHTML = 'artist: ' + artistsStr;

        cur_site = site;
        cur_song_name = name;
        cur_artists = artistsStr;

		songId = id;
		player_albumId = albumId;
		player_name.innerHTML = name;

		if(has_song_play === false){
			has_song_play = true;
			if(is_login){
                like.classList.remove('hidden');
			}

		}

		if(picUrl !== null && picUrl !== ''){
			if(site === 'netease'){
                player_img.src = picUrl + "?param=200y200";
			}else{
				$.ajax({
					url : 'pic?url=' + picUrl + '&site=' + site,
                    datatype:"json",
                    success : function (res) {
						if(res.success === true){
							player_img.setAttribute('src', 'data:image;base64,' + res.data);
							//player_img.src = 'data:image/jpeg;base64,' + res.data;
						}else{

						}
                    },
					fail : function () {
						alert("FUCK!");
                    }
				});
			}


		}else{
			player_img.src = 'image/icon.png';
		}
		

		if(lyrics !== null){
			info_has_lyrics = true;
			cur_light_lrc_dom = null;
			
			var ts = lrcToJs(lyrics).initLrc();
			fillLyricsList(ts._tagField, ts._lrcFiled, ts._lrcOrder);
			cur_time_index = 0;
			
		}else{
			info_has_lyrics = false;
			cur_light_lrc_dom = null;
			lrc_list.innerHTML = '';
			//lrc_time_map = null;
		}
    }

	function init(){
		player_album.innerHTML = 'album: none';
		player_singer.innerHTML = 'artist: none';
		player_name.innerHTML = 'none';
		player_img.src = "image/icon.png";
	}

	like.onclick = function(){
		if(!is_cur_song_favor){
            click_like_song(songId, cur_site, cur_song_name, cur_artists);
		}else{
			click_del_song(songId, cur_site);
		}
	};
	player_album.onclick = function(){
		click_player_album(player_albumId);
	};
	player_singer.onclick = function(){
		click_player_singer(singerId);
	};

    return {
        play : play,
		set : setMusic,
		init : init,
		setCallback : function(like_song, singer, album, del_song, check){
			click_like_song = like_song;
			click_player_album = album;
			click_player_singer = singer;
			click_del_song = del_song;
			check_cur_song_favor = check;
		},
		switchFavorIcon : function (isRed) {
			is_cur_song_favor = isRed;
			if(isRed){
				like.innerHTML = 'favorite';
			}else{
                like.innerHTML = 'search';
			}
        },
        displayFavor : function (display) {
            if(display){
				like.classList.remove('hidden');
            }else{
                like.classList.add('hidden');
            }
        },
		checkFavor : function () {
			if(has_song_play){
                check_cur_song_favor(songId, cur_site);
			}
        },
		hasSong : function () {
			if(has_song_play){

                console.log("has song");
			}else{

                console.log("no song");
			}
			return has_song_play;
        }
    };
})(ImgBlur, Mobile);
