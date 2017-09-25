function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute;
};
$(document).ready(function () {
    articleId=getQueryString("aid");
    if(articleId==null || articleId==""){
        $("#err").text("找不到页面哦");
        $("title").text("找不到页面");
        $("#breadcrumb").remove();
        return;
    }

    $.ajax({
        url:"/api/article/"+articleId,
        type:"GET",
        data:"increase_read_count=true",
        success:function (data,status) {
            $("title").text(data.title+" | PP Team");
            $("#title").text(data.title);
            $("#active_crumb").text(data.title);
            var username="";
            $.ajax({
                url:"/api/user",
                async:false,
                type:"GET",
                data:"id="+data.authorId,
                error:function(){

                },
                success:function (data2,status2) {
                    username=data2.username;
                }
            });
            $("#article_info").html('<span class="glyphicon glyphicon-time"></span>'+
                formatDateTime(data.creationTime)+" | "+
                '<span class="glyphicon glyphicon-user"></span>'+
                username+" | "+
                '<span class="glyphicon glyphicon-eye-open"></span>'+
                "阅读("+data.readCount+")");

            $("#cover").attr({"src":"/image/article/"+data.thumbnailName});
            $("#content").html(data.content);
        }
    })

});