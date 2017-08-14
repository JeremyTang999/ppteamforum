/**
 * 
 */
$(document).ready(function(){
	$.ajax({
		url:"/api/user",
		data:{"from_context":true},
		type:"GET",
		error:function (xhr,status,error) {
			$("#user").html('<a href="/login.html">登录</a> | <a href="/signup.html">注册</a> ');
        },
        success:function (data,status) {
			$("#user").html(data.username+' | <a href="/space.html">个人中心</a> | <a href="/p_logout">退出</a> ');
        }
	})
})

