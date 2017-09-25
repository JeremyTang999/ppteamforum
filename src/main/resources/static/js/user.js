$(document).ready(function() {
    $.ajax({
        url: "/api/user",
        data: {"from_context": true},
        type: "GET",
        success: function (data, status) {
            $("#btn1").attr({"href": "/space.html"});
            $("#btn1").text("个人中心");
            $("#btn2").attr({"href": "/p_logout"});
            $("#btn2").text("退出");


            $("#username").text(data.username);
        }
    });
});