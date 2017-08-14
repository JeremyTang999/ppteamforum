/**
 * 
 */

$(document).ready(function(){
	var info;
	$.ajax({
		url:"/user/basicinfo",
		type:"GET",
		async:false,
		success:function(data,status){
			info=data;
		}
	});
	
	$.ajax({
		url:"/user/securityinfo",
		type:"get",
		data:"id="+info.id,
		success:function(data,status){
			for(i=0;i<3;i++){
				$("#q"+(i+1)+"val").text(data.oriQA[i].question);
			}
		}
	});
	 
	$("#btn").click(function(){
		var qa;
		var qas=new Array();
		for(i=1;i<=3;i++){
			qa={"question":$("#q"+i+"val").text(),
				"answer":$("#a"+i+"val").val()};
			qas.push(qa);
		}
		$.ajax({
			url:"/user/securityinfo",
			type:"post",
			traditional:true,
			data:{
				"id":info.id,
				"oriPass":$("#passval").val(),
				"oriQA":qas
			},
			success:function(data,status){
				alert("验证成功");
			},
			error:function(xhr,status,error){
				alert("密码或密保问题错误，请重试");
			}
		})
	});
});