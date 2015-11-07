$(function() {
	
	$(".loggxnxi").html("登录用户：<span class='colorBlue'>" + $.getUrlParam("userName") + " </span>");
	// 查询接口（首页）,用户信息
	$(".QueryInterface").on("click",function(){
		window.location.href="main.html?userName="+$.getUrlParam("userName");
	});
	//添加接口，用户信息
	$(".AddInterface").on("click",function(){
		window.location.href="interFaceBaseInfo.html?userName="+$.getUrlParam("userName");
	});
	$("#ifProId").attr("disabled","disabled"); ;

	
	$('#ifSysId').change(function(){ 
	var sysId=	$(this).children('option:selected').val();
	var sendData={
			"interFacePro":{
				   "sysId":sysId
				}
			};
	//加载产品信息
	  gigold.pay.interFace.ajaxHandler({
			"url":"getProInfoBySysId.do",
			"data":JSON.stringify(sendData),
			"onSuccess":function(data){
				if (data.rspCd == "00000") {
					$("#ifProId").removeAttr("disabled");  
					 var optionStr='<option value="0" selected>请选择所属产品</option>';
					$.each(data.proList, function(index, proData) {
                        optionStr += '<option value="'+proData.id+'">'
                                + proData.proName + '</option>';
                       
                    });
					 $("#ifProId").html(optionStr);
              }
			}
		});
	});
		//加载系统信息
    gigold.pay.interFace.ajaxHandler({
			"url":"getAllSysInfo.do",
			"onSuccess":function(data){
				if (data.rspCd == "00000") {
                    $.each(data.sysList, function(index, sysData) {
                        var optionStr = '<option value="'+sysData.id+'"">'
                                + sysData.sysName + '</option>';
                        console.log(optionStr);
                        $("#ifSysId").append(optionStr);
                    });
                }
			}
		});
		
//		//加载产品信息
//		 gigold.pay.interFace.ajaxHandler({
//	            "url":"getAllProInfo.do",
//	            "onSuccess":function(data){
//	            	if (data.rspCd == "00000") {
//	                    $.each(data.proList, function(index, proData) {
//	                        var optionStr = '<option value="'+proData.id+'">'
//	                                + proData.proName + '</option>';
//	                        $("#ifProId").append(optionStr);
//	                    });
//	                }
//	            }
//	        });
		
       //接口基本信息保存按钮功能
		$("#saveInterFaceBtn").click(function() {
			var sendData = {};
			sendData.interFaceInfo = $('#interFaceForm').serializeJson();
			gigold.pay.interFace.ajaxHandler({
	                "url":"addInterface.do",
	                "data":JSON.stringify(sendData),
	                "onSuccess":function(data){
	                	 if (data.rspCd == "00000") {
	                          alert("保存成功");
	                          // $("#ifId").val(data.interFaceInfo.id);
	                           self.location.href="interFaceAdd.html?ifId="+data.interFaceInfo.id+"&userName="+$.getUrlParam("userName");
	                      }else{
	                          alert("保存失败");
	                      }
	                }
	            });
			
		});

	
    

});