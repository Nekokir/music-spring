var Mobile = (function(){
    var isMobile = false;
    if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
        isMobile = true;
        console.log('mobile!!!');
        // 移动端操作
    } else {
        isMobile = false;
        // PC端操作
    }
    return {
        isMobile : function(){
            return isMobile;
        }
    };
})();