var Dialog = (function(){
    var server_wrong = document.getElementById('dialog_server_wrong'),
        normal_content = server_wrong.getElementsByTagName('p')[0];
    //server_wrong.style.display = 'block';
    
    document.getElementsByClassName('close')[0].onclick = function(){
        server_wrong.close();
    }
    return {
        normal : function(str){
            normal_content.innerHTML = str;
            server_wrong.showModal();
        }
    };
})();