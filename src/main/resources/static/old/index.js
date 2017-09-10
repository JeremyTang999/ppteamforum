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
	});

	var latestArticle,hottestArticle;
	$.ajax({
		url:"/api/articles",
		data:{
			"order":"latest",
			"topic":"real",
			"count":1,
			"page":1
		},
		type:"GET",
		async:false,
		success:function (data, status) {
			latestArticle=data[0];
        }
	});
    $.ajax({
        url:"/api/articles",
        data:{
            "order":"hottest",
            "topic":"real",
            "count":1,
            "page":1
        },
        type:"GET",
        success:function (data, status) {
            hottestArticle=data[0];
        }
    });
    $("#sim").html('<img width=500 src="/image/article/'+latestArticle.thumbnailName+'"/>'+
		'<h4>'+latestArticle.title+'</h4>');

})

