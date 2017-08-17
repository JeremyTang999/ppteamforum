/**
 * 
 */
var info;
var validateInfo=new Object();
$(document).ready(function(){

	$.ajax({
		url:"/api/user_security",
		type:"GET",
		success:function(data,status){
			info=data;
			for(i=1;i<=3;i++){
				$("#q"+i+"content").text(info["oldQuestion"+i]);
			}
		}
	});
	

	 
	$("#btn").one("click",function(){
		var data=new Object();
		data.update=false;
		data.oldPassword=$("#passval").val();
		validateInfo.oldPassword=$("#passval").val();
		for(i=1;i<=3;i++){
			data["oldQuestion"+i]=info["oldQuestion"+i];
			data["oldAnswer"+i]=$("#a"+i+"val").val();
			validateInfo["oldQuestion"+i]=info["oldQuestion"+i];
            validateInfo["oldAnswer"+i]=$("#a"+i+"val").val();
		}
		$.ajax({
			url:"/api/user_security",
			type:"PUT",
			data:JSON.stringify(data),
			contentType:"application/json",
			error:function(xhr,status,error){
				alert("验证失败，请重试");
				location.reload(true);
			},
			success:validateSuccessCallBack
		})
	});
});

function validateSuccessCallBack(data,status) {
    alert("验证成功");
	$("#title").text("请输入新密码及新密保问题");
	$("#pass").text("新密码");
	$("#passval").val("");
	for(i=1;i<=3;i++){
		$("#q"+i+"content").html('<input id="q'+i+'val" value="'+$("#q"+i+"content").text()+'"></input>');

	}
    $("#btn").text("确认");
	$("#btn").click(modSecurity);
}

function modSecurity() {

	if($("#passval").val()==""){
		alert("密码不能为空");
		return;
	}
	if($("#q1val").val()=="" ||
        $("#q2val").val()=="" ||
        $("#q3val").val()=="" ||
        $("#a1val").val()=="" ||
        $("#a2val").val()=="" ||
        $("#a3val").val()==""){
		alert("问题及答案不能为空");
		return;
	}

	var data=new Object();
	data.update=true;
	data.oldPassword=validateInfo.oldPassword;
	data.newPassword=$("#passval").val();
	for(i=1;i<=3;i++){
		data["oldQuestion"+i]=validateInfo["oldQuestion"+i];
		data["oldAnswer"+i]=validateInfo["oldAnswer"+i];
	}
	for(i=1;i<=3;i++){
		data["newQuestion"+i]=$("#q"+i+"val").val();
		data["newAnswer"+i]=$("#a"+i+"val").val();
	}
	$.ajax({
		url:"/api/user_security",
		type:"PUT",
		data:JSON.stringify(data),
        contentType:"application/json",
        error:function(xhr,status,error){
        	alert("修改失败，请重试");
    	},
		success:function (data, status) {
			alert("修改成功");
			location.href="/";
        }
	})
}