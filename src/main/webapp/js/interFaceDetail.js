$(function(){
	console.log( $.getUrlParam("userName"))
	$(".loggxnxi").html("登录用户：<span class='colorBlue'>" + $.getUrlParam("userName") + " </span>");
	// 查询接口（首页）,用户信息
	$(".QueryInterface").on("click",function(){
		window.location.href="main.html?userName="+$.getUrlParam("userName");
	});
	//添加接口，用户信息
	$(".AddInterface").on("click",function(){
		window.location.href="interFaceBaseInfo.html?userName="+$.getUrlParam("userName");
	});
	
	
	var userId = $.getUrlParam("id");
	//var userId = 7;
	perHandel(userId);
	
})


function perHandel(userId){
	
	var perReq = perRequest(userId);
	perReq.request();
	
	var perReqQq = perRequestQq(userId);
	perReqQq.requestQq();
	
	var perReqXy = perRequestXy(userId);
	perReqXy.requestXy();
	
}

//获取接口基本信息
function perRequest(userId){
	var data = {
			"interFaceInfo":{
				"id":userId
			}
			
	};
	console.log(addParam(data));
	data.request=function (perData){
		$.ajax({
			type:"post",
			url:"queryInterFaceById.do",
			dataType:'json',
			contentType : "application/json",
			async:"false",
			data:addParam(data),
			success:function(result){
				console.log(result);
				var perData = perRander(result);
				perData.rander();
			},
			error:function(e){
				console.log(e);
			}
		});
		
	}
	return data;
}
//渲染接口基本信息
function perRander(result){
	
	var backData = {};
	backData = result;
	
	backData.rander = function(){
		$(".propName").html(this.interFaceInfo.ifName);
		$(".propDesc").html(this.interFaceInfo.ifDesc);
		$(".propUrl").html(this.interFaceInfo.ifUrl);
		$(".propSystem").html(this.system.sysName);
		$(".propProduct").html(this.pro.proName);
		$(".userName").html(this.userInfo.userName);
		$(".propHttp").html(this.interFaceInfo.ifProtocol);
		if(this.interFaceInfo.ifProtocol=="HTTP"){
			$(".propRequest").html(this.interFaceInfo.ifType);
			$(".method").parent().parent().css({"display":"none"});
			$(".methodVersion").parent().parent().css({"display":"none"});
		}else if(this.interFaceInfo.ifProtocol=="DUBBO"){
			$(".propRequest").parent().parent().css({"display":"none"});
			$(".method").html(this.interFaceInfo.method);
			$(".methodVersion").html(this.interFaceInfo.methodVersion);
		}
		
	}
	return backData;
}


//获取接口请求信息
function perRequestQq(userId){
	
	var data = {
			"interFaceField":{
				"ifId":userId,
				"fieldFlag":"1"
			}
	};
	
	data.requestQq=function (perData){
		
		$.ajax({
			type:"post",
			url:"getInterFaceFieldsJson.do",
			dataType:'json',
			contentType : "application/json",
			async:"true",
			data:addParam(data),
			success:function(result){
				var perData = perRanderQq(result);
				perData.rander();
				
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	return data;
}

function perRanderQq(result){
	var backData = {};
	backData = result;
	backData.rander = function(){
		$(".reqPre").html(this.jsonStr);
	}
	return backData;
}



function perRequestXy(userId){
	var data = {
			"interFaceField":{
				"ifId":userId,
				"fieldFlag":"2"
			}
	};
	
	
	data.requestXy=function (perData){
		
		$.ajax({
			type:"post",
			url:"getInterFaceFieldsJson.do",
			dataType:'json',
			contentType : "application/json",
			async:"true",
			data:addParam(data),
			success:function(result){
				var perData = perRanderXy(result);
				perData.rander();
				
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	return data;
}

function perRanderXy(result){
	var backData = {};
	backData = result;
	backData.rander = function(){
		$(".response").html(this.jsonStr);
	}
	return backData;
}







