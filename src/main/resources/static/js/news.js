var page=getQueryString("page");
if(page==null)
    location.href="/news.html?page=1";

var page_count;
$.ajax({
    url:"/api/articles/page_count",
    type:"GET",
    data:"count_per_page=10",
    async:false,
    success:function (data, status) {
        page_count=data
    }
});

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

    if(page_count<=7){
        for(i=1;i<=page_count;i++){
            if(i==page)
                $("#page").append('<li class="active"><a>'+i+'</a></li>');
            else
                $("#page").append('<li><a href="/news.html?page='+i+'">'+i+'</a></li>');
        }
        if(page!=1)
            $("#page").prepend('<li><a href="/news.html?page='+(page-1)+'">&laquo;</a></li>');
        if(page!=page_count)
            $("#page").append('<li><a href="/news.html?page='+(page+1)+'">&raquo;</a></li>');

    }
    $.ajax({
       url:"/api/articles",
       type:"GET",
       data:"order=latest&count=10&page="+page,
        success:function (data,status) {
            for(i=0;i<data.length;i++){
                addList("list",{
                    "imageUrl":"/image/article/"+data[i].thumbnailName,
                    "topic":data[i].topic,
                    "articleUrl":"/article.html?aid="+data[i].id,
                    "title":data[i].title,
                    "creationTime":formatDateTime(data[i].creationTime),
                    "readCount":data[i].readCount
                });
            };
        }
    });

});

/**
 *
 * @param article 有以下属性: imgUrl,articleUrl,title,creationTime,readCount
 */
function addList(htmlId,article) {
    $("#"+htmlId).append(
        '<div class="row">'+
            '<hr/>'+
            '<div class="col-xs-3">'+
                '<img class="img-thumbnail" src="'+article.imageUrl+'"/>'+
            '</div>'+
            '<div class="col-xs-7">'+
                '<h5>'+(article.topic=='sim' ? '模拟资讯' : '赛车动态')+'</h5>'+
                '<h1><a href="'+article.articleUrl+'">'+article.title+'</a></h1>'+
                '<p>'+article.creationTime+' | '+article.readCount+'阅读</p>'+
                '</div>'+
        '</div>');

}