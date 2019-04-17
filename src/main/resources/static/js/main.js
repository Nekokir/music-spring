$(document).ready(function () {
    $.ajax({
        "url":"http://localhost:1272/userSession",
        "method":"GET",
        "cache":false,
        "async":false,
        success:function(res){
            console.log(res);
            if(res  != "") {
                window.location.href = "/welcome";
            }
        }
    })
    $.ajax({
        "url":"http://localhost:1272/pic",
        "data":{"site":"netease","url":"https://y.gtimg.cn/music/photo_new/T002R300x300M00000065p6e3LYi5Z.jpg"},
        "method":"GET",
        "cache":false,
        "async":false,
        datatype:"json",
        success: function(data) {
            console.log(data);
            //将图片的Base64编码设置给src
            $("#ImagePic").attr("src","data:image;base64,"+data.data);
        },
        error:function(data){
            alert('响应失败！');
        }
    });
});

function login() {
    alert("登录中...");
    var usrname = $("#userId").val();
    var passwd = $("#loginPwd").val();
    var json = {
        "userId":usrname,
        "loginPwd":passwd
    };
    $.ajax({
        type:"get",
        url:"http://localhost:1272/login",
        data:json,
        async:true,
        datatype:"json",
        success:function(res){
            console.log(res);
            if(res.success === true){
                alert("登录成功");
                window.location.href="/welcome";
            }else if(res.data.code == 0){
                alert("密码错误");
            }else if(res.data.code == -1){
                alert("用户名不存在");
            }else{
                alert("服务器异常");
            }
        },
        error:function (res) {
            console.log(res);
            alert("系统异常，请稍后重试！");
        }
    });
}

function signup() {
    alert("注册中...");
    var usrname = $("#userId").val();
    var passwd = $("#loginPwd").val();
    var json = {
        "userId":usrname,
        "loginPwd":passwd
    };
    $.ajax({
        type:"post",
        url:"http://localhost:1272/reg",
        data:json,
        async:true,
        datatype:"json",
        success:function(res){
            console.log(res);
            if(res == true){
                alert("注册成功");
            }else if(res == false){
                alert("用户名已存在!请登录");
            }else{
                alert("服务器正忙");
            }
        },
        error:function () {
            alert("系统异常，请稍后重试！");
        }
    });
}