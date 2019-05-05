var SBK = {
	checkLogin : function(){

        /*function getCookie(sName)
        {
        	alert(func);
            var aCookie = document.cookie.split(";");
            for (var i=0; i < aCookie.length; i++)
            {
                var aCrumb = aCookie[i].split("=");
                if (sName == aCrumb[0])
                    return unescape(aCrumb[1]);
            }
            return null;
        }*/
        var _ticket = $.cookie("token");
        // alert("hi"+name)

        var name=getCookie("token")/*sName为要取的key名*/
        // alert("hi"+_ticket)
        if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8089/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到沙巴克购物网！<a href=\"http://localhost:8089/logout\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	SBK.checkLogin();
});