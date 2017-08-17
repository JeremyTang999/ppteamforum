/**
 * 
 */
var username="",id;
var info;
$(document).ready(function(){
	
	$.ajax({
		url:"/api/user",
		type:"GET",
		data:{"from_context":true},
		async:false,
		success:function(data,status){
			id=data.id;
			username=data.username;
			$("#username").text(data.username);
		}
	});
	
	$.ajax({
		url:"/api/user_info",
		type:"GET",
		data:"id="+id,
		success:function(data,status){
			info=data;
			switch(data.gender){
			case "male":gender="男";break;
			case "female":gender="女";break;
			case "other":gender="其他";break;
			case "unknown":gender="保密";break;
			}
			$("#gender").text(gender);
			if(data.photoPath!="" && data.photoPath!=null){
				$("#p").attr("src",data.photoPath);
			}
			if(data.personalSignature!=null){
				$("#sig").text(data.personalSignature);
			}
		}
	});
	
})

function modInfo(){
	
	$("#prompt").text("无需修改的请保持原样");
	
	
	$("#gender").html('<input type="radio"'+
			'name="gendervalue" id="male" value="male">'+
			'男</input>'+
			'<input type="radio"'+
			'name="gendervalue" id="female" value="female">'+
			'女</input>'+
			'<input type="radio"'+
			'name="gendervalue" id="other" value="other">'+
			'其他</input>'+
			'<input type="radio"'+
			'name="gendervalue" id="unknown" value="unknown">'+
			'保密</input>');
	//alert(info.gender);
	/*if(info.gender=="male"){
		$("#male").attr("checked","true");
	}
	else{
		$("#female").attr("checked","true");
	}*/
	$("#"+info.gender).attr("checked","true");
	$("#sig").html('<textarea id="sigvalue">'+
		(info.personalSignature==null?'':info.personalSignature)+'</textarea>');
	$("#btnarea").html('<button id="submit" onclick="infoSubmit()">提交</button>')
}

function infoSubmit(){
	var obj={
		"id":id,
		"gender":$("input[name='gendervalue']:checked").val(),
		"photoPath":"",
		"personalSignature":$("#sigvalue").val()
	};
	$.ajax({
		data:JSON.stringify(obj),
		url:"/api/user_info",
		type:"PUT",
		contentType:"application/json",
		error:function(xhr,status,error){
			alert("修改失败，请重试");
		},
		success:function(data,status){
			alert("修改成功");
			location.reload(true);
		}
	});
}

