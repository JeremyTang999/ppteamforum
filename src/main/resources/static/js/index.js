
$(document).ready(function() {
    $.ajax({
        url: "/api/user",
        data: {"from_context": true},
        type: "GET",
        success: function (data, status) {
            $("#btn1").attr({"href":"/space.html"});
            $("#btn1").text("个人中心");
            $("#btn2").attr({"href":"/p_logout"});
            $("#btn2").text("退出");


            $("#username").text(data.username);
        }
    });

    $.ajax({
        url:"/api/articles",
        data:{
            "order":"latest",
            "count":2,
            "page":1
        },
        type:"GET",
        success:function (data,status) {
            for(i=0;i<data.length;i++){
                addCarousel('/image/article/'+data[i].thumbnailName,data[i].title);
            }
        }
    });

    $.ajax({
        url:"/api/articles",
        data:{
            "order":"hottest",
            "count":2,
            "page":1
        },
        type:"GET",
        success:function (data,status) {
            for(i=0;i<data.length;i++){
                addCarousel('/image/article/'+data[i].thumbnailName,data[i].title);
            }
        }
    });

});
var dataSlideTo=0;
function addCarousel(imgUrl,title) {
    if(dataSlideTo==0) {
        $("#carouselIndicators").append('<li data-target="#myCarousel" data-slide-to="'+ (dataSlideTo++) + '" class="active"></li>');
        $("#carouselInner").append('<div class="item active">'+
            '<img style="width: 100%" src="'+imgUrl+'"/>'+
            '<a class="carousel-caption" href=""><h3>'+title+'</h3></a>'+'</div>');
    }
    else{
        $("#carouselIndicators").append('<li data-target="#myCarousel" data-slide-to="'+(dataSlideTo++)+'"></li>');
        $("#carouselInner").append('<div class="item">'+
            '<img style="width: 100%" src="'+imgUrl+'"/>'+
            '<a class="carousel-caption" href=""><h3>'+title+'</h3></a>'+'</div>');
    }

}