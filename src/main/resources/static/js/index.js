
$(document).ready(function() {

    $.ajax({
        url:"/api/articles",
        data:{
            "order":"latest",
            "count":5,
            "page":1
        },
        type:"GET",
        success:function (data,status) {
            for(i=0;i<data.length;i++){
                addCarousel('/image/article/'+data[i].thumbnailName,data[i].title,
                    "/article.html?aid="+data[i].id);
                if(i>=1) break;
            }
        }
    });

    $.ajax({
        url:"/api/articles",
        data:{
            "order":"hottest",
            "count":5,
            "page":1
        },
        type:"GET",
        success:function (data,status) {
            for(i=0;i<data.length;i++){
                addCarousel('/image/article/'+data[i].thumbnailName,data[i].title,
                    "/article.html?aid="+data[i].id);
                if(i>=1) break;
            };
        }
    });

    $.ajax({
        url:"/api/articles",
        data:{
            "order":"latest",
            "topic":"sim",
            "count":5,
            "page":1
        },
        type:"GET",
        success:function (data,status) {
            for(i=0;i<data.length;i++){
                addList("simList",{
                    "imageUrl":"/image/article/"+data[i].thumbnailName,
                    "articleUrl":"/article.html?aid="+data[i].id,
                    "title":data[i].title,
                    "creationTime":formatDateTime(data[i].creationTime),
                    "readCount":data[i].readCount
                });
            };
        }
    });
    $.ajax({
        url:"/api/articles",
        data:{
            "order":"latest",
            "topic":"real",
            "count":5,
            "page":1
        },
        type:"GET",
        success:function (data,status) {
            for(i=0;i<data.length;i++){
                addList("realList",{
                    "imageUrl":"/image/article/"+data[i].thumbnailName,
                    "articleUrl":"/article.html?aid="+data[i].id,
                    "title":data[i].title,
                    "creationTime":formatDateTime(data[i].creationTime),
                    "readCount":data[i].readCount
                });
            };
        }
    })

});

var dataSlideTo=0;
function addCarousel(imgUrl,title,url) {
    if(dataSlideTo==0) {
        $("#carouselIndicators").append('<li data-target="#myCarousel" data-slide-to="'+ (dataSlideTo++) + '" class="active"></li>');
        $("#carouselInner").append('<div class="item active">'+
            '<img style="width: 100%" src="'+imgUrl+'"/>'+
            '<a class="carousel-caption" href="'+url+'"><h3>'+title+'</h3></a>'+'</div>');
    }
    else{
        $("#carouselIndicators").append('<li data-target="#myCarousel" data-slide-to="'+(dataSlideTo++)+'"></li>');
        $("#carouselInner").append('<div class="item">'+
            '<img style="width: 100%" src="'+imgUrl+'"/>'+
            '<a class="carousel-caption" href="'+url+'"><h3>'+title+'</h3></a>'+'</div>');
    }

}

/**
 *
 * @param article 有以下属性: imgUrl,articleUrl,title,creationTime,readCount
 */
function addList(htmlId,article) {
    $("#"+htmlId).append(
        '<div class="row">'+
        '<hr/>'+
        '<div class="col-xs-3 col-md-offset-1">'+
        '<img class="img-thumbnail" src="'+article.imageUrl+'"/>'+
        '</div>'+
        '<div class="col-xs-7">'+
        '<h1><a href="'+article.articleUrl+'">'+article.title+'</a></h1>'+
        '<p>'+article.creationTime+' | '+article.readCount+'阅读</p>'+
        '</div>'+
        '</div>');

}

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