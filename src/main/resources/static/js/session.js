$(document).ready(function () {
    $.ajax({
        "url":"http://localhost:1272/userSession",
        "method":"GET",
        "cache":false,
        "async":false,
        success:function(res){
            console.log(res);
            if(res != ""){
                $("#username").text(res);
            }else if(res == ""){
                alert("请重试");
                window.location.href="/";
            }
        },
        error:function () {
            alert("系统异常，请稍后重试！");
            window.location.href="/";
        }
    })
});