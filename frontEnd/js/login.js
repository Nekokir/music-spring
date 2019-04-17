var account = (function(){
    var login_form = document.getElementById('login_form'),
        reg_form = document.getElementById('reg_form'),
        container = document.getElementById('log_container');

    var login_tab_button = document.getElementById('login_tab_button'),
        reg_tab_button = document.getElementById('reg_tab_button');

    var login_button = document.getElementById('log_button'),
        reg_button = document.getElementById('reg_button');
    
    var log_username = document.getElementById('log_username'),
        log_password = document.getElementById('log_password'),
        reg_username = document.getElementById('reg_username'),
        reg_password = document.getElementById('reg_password');

    var is_login_show = true;

    var click_login, click_reg;

    function fuck(str){
        return true;
    }

    login_tab_button.onclick = function(){
        if(!is_login_show){

            reg_form.classList.remove('log-show');
            reg_form.classList.add('log-hidden');

            login_form.classList.remove('log-hidden');
            login_form.classList.add('log-show');

            is_login_show = true;

        }
    }

    reg_tab_button.onclick = function(){
        if(is_login_show){

            login_form.classList.remove('log-show');
            login_form.classList.add('log-hidden');

            reg_form.classList.remove('log-hidden');
            reg_form.classList.add('log-show');
            
            is_login_show = false;

        }
    }

    login_button.onclick = function(){
        var username = log_username.value,
            password = log_password.value;
        if(username !== '' && password !== ''){
            if(fuck(username) && fuck(password)){
                click_login(username, password);
            }
        }else{

        }
        
    }

    reg_button.onclick = function(){
        var username = reg_username.nodeValue,
            password = reg_password.nodeValue;
            if(username !== '' && password !== ''){
                if(fuck(username) && fuck(password)){
                    click_reg(username, password);
                }
            }else{
    
            }
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
        setCallback : function(login, reg){
            click_login = login;
            click_reg = reg;
        }
    };
})();