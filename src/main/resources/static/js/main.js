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
            if(res == true){
                alert("登录成功");
                window.location.href="/welcome";
            }else if(res == false){
                alert("密码错误或用户名不存在");
            }else{
                alert("服务器正忙");
            }
        },
        error:function () {
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