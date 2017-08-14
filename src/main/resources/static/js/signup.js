/**
*
*/

function checkName(){
	if(($("#username").val())==""){
		$("#unstate").text("用户名不能为空");
	}
	else{
		$.ajax({
			url:"/api/user",
			type:"GET",
			data:"username="+$("#username").val(),
			error:function(xhr,status,error){
                $("#unstate").text("");
			},
			success:function (data,status) {
                $("#unstate").text("用户名已被使用");
            }
		})
	}
}

function checkPass(){
	if(($("#password").val())==""){
		$("#pwstate").text("密码不能为空");
	}
	else{
		$("#pwstate").text("");
	}
	
}


function regProc(){
	if((($("#password").val())=="")||($("#username").val())==""){
		alert("用户名或密码不能为空");
	}
	else if($("#question1").val()==$("#question2").val()||
			$("#question1").val()==$("#question3").val()||
			$("#question2").val()==$("#question3").val()){
		alert("密保问题不能相同");
		
	}
	else{
//		alert($("input[name='gender']:checked").val());
		var obj={
			"username":$("#username").val(),
			"gender":$("input[name='gender']:checked").val(),
			"password":$("#password").val(),
			"question1":$("#question1").val(),
			"answer1":$("#answer1").val(),
			"question2":$("#question2").val(),
			"answer2":$("#answer2").val(),
			"question3":$("#question3").val(),
			"answer3":$("#answer3").val()
		}
		$.ajax({
			data:JSON.stringify(obj),
			url:"/api/sign_up_info",
			type:"POST",
			contentType:"application/json",
			error:function(xhr,status,error){
				alert("注册失败，请重试");
			},
			success:function(data,status){
				alert("注册成功");
				location.href="/";
			}
		})
	}
}