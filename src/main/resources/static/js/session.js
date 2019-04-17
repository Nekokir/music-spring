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
                window.location.href="/";
            }
        },
        error:function () {
            window.location.href="/";
        }
    })
});

function logout() {
    $.ajax({
        "url":"http://localhost:1272/logout",
        "method":"GET",
        "cache":false,
        "async":false,
        success:function(res){
            console.log(res);
            if(res) {
                window.location.href = "/";
            }
        }
    })
}