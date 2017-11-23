﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/style.css"/>
<link rel="stylesheet" href="css/bootstrap.min.css"/>
<link rel="stylesheet" href="css/font-awesome.min.css"/>
<link rel="stylesheet" href="css/bootstrap-spinner.css"/>
<link rel="stylesheet" href="static/ace/css/datepicker.css"/>
<link rel="stylesheet" href="static/ace/css/chosen.css"/>
<link rel="stylesheet" href="static/ace/css/ace.css"/>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.spinner.min.js"></script>
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script src="static/ace/js/bootstrap.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
//$('.f_uli li').eq(0).addClass("active");
//$('.f_uli li').eq(1).removeClass("active");
function setFieldCls(obj, fieldName){
	$("#"+fieldName+"Id li").each(function(){//$("#id li")
		if($(this).hasClass("active")){
			$(this).removeClass("active");
		}
	});
	
	$(obj).addClass("active");
}

function setFieldValue(obj, fieldName, fieldValue){
	$("#"+fieldName).val(fieldValue);
	setFieldCls(obj, fieldName);
}

//点击推荐配置
function setRecommendType(obj, fieldName, fieldValue){
	setFieldCls(obj, fieldName);
	var cpuCode=fieldValue.substring(0, 1);
	var memoryCode=fieldValue.substring(1);
	setFieldValue(document.getElementById("cpuId"+cpuCode), 'cpu', cpuCode);
	setFieldValue(document.getElementById("memoryId"+memoryCode), 'memory', memoryCode);
}

//设置软件参数
function setSoftParam(indx){
	$("#softParam"+(indx==0?"":indx)).val(indx);
}

//永久到期时间选择
function checkExpireDate(isChk){
	if(isChk){//复选框选择
		if($("#expireDateChk").is(":checked")){
			$("#expireDate").val("9999-12-31");
		}else{
			$("#expireDate").val("");
		}
	}else{//非复选框选择
		$("#expireDateChk").prop("checked", false);//不选择复选框
	}
}

//新增磁盘行
function addDiskRow(){
    var len = $("#diskTableId tr").length;
    if(len>=15){
    	$("#diskTableId").tips({side:3, msg:'只能选配15块磁盘', bg:'#AE81FF', time:2});
    	return;
    }
    
    var tdStr="<td align=\"left\" style=\"width: 120px;\"><select class=\"chosen-select form-control\" name=\"diskType\" id=\"diskType"+(len+1)+"\" data-placeholder=\"请选择磁盘类型\" style=\"vertical-align:top;width: 120px;\">"+$("#diskType").html()+"</select></td>"
	    +"<td align=\"left\" style=\"width: 120px;padding:10px;\"><input type=\"text\" name=\"diskSize\" id=\"diskSize\" value=\"20\" style=\"width: 120px;\" maxlength=\"5\" onblur=\"diskSizeFunc(this, 'diskType', 'iopsId"+(len+1)+"')\"/></td>"
	    +"<td align=\"left\" style=\"width: 200x;\">GB&nbsp;&nbsp;&nbsp;<span id=\"iopsId"+(len+1)+"\">1120</span>&nbsp;IOPS&nbsp;&nbsp;&nbsp;<input name=\"diskEncrypt\" type=\"checkbox\" value=\"\"/>加密&nbsp;&nbsp;&nbsp;<a href=\"javascript:void()\" onclick=\"delRow('diskTrId"+(len+1)+"')\">删除</a></td>";
    $("#diskTableId").append("<tr id=\"diskTrId"+(len+1)+"\">"+tdStr+"</tr>");
}

//新增软件安装行
function addSoftRow(){
    var len = $("#softTableId tr").length;
    if(len>=15){
    	$("#softTableId").tips({side:3, msg:'只能选择15个安装软件', bg:'#AE81FF', time:2});
    	return;
    }
    
    var tdStr="<td align=\"left\" style=\"width: 120px;\"><select class=\"chosen-select form-control\" name=\"softName\" data-placeholder=\"请选择软件名称\" style=\"vertical-align:top;width: 120px;\">"+$("#softName").html()+"</select></td>"
    	+"<td align=\"left\" style=\"width: 120px;padding:10px;\"><select class=\"chosen-select form-control\" name=\"softVer\" data-placeholder=\"请选择软件版本\" style=\"vertical-align:top;width: 120px;\">"+$("#softVer").html()+"</select></td>"
	    +"<td align=\"left\" style=\"width: 120x;\"><input type=\"hidden\" name=\"softParam\" id=\"softParam"+(len+1)+"\" value=\"\"/><a href=\"javascript:void()\" onclick=\"setSoftParam("+(len+1)+")\">设置参数</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:void()\" onclick=\"delRow('softTrId"+(len+1)+"')\">删除</a></td>";
	$("#softTableId").append("<tr id=\"softTrId"+(len+1)+"\">"+tdStr+"</tr>");
}

//删除行  
function delRow(rowId){
    $("#"+rowId).remove();  
}

//套餐数据校验
function checkPckgData(){
	if($("#pckgId").val()==""){
		if($("#tctableId").length>0){
			$("#tctableId").tips({side:3, msg:'请选择套餐', bg:'#AE81FF', time:2});
			$("#tctableId").focus();
		}else{
			alert("请选择套餐");
		}
		
		return false;
	}
	
	if($("#tcvirName").val()==""){
		$("#tcvirName").tips({side:3, msg:'虚拟机名称不能为空', bg:'#AE81FF', time:2});
		$("#tcvirName").focus();
		return false;
	}
	
	return true;
}

//数据校验
function checkData(){
	if($("#virName").val()==""){
		$("#virName").tips({side:3, msg:'虚拟机名称不能为空', bg:'#AE81FF', time:2});
		$("#virName").focus();
		return false;
	}
	
	if($("#imgUserName").val()==""){
		$("#imgUserName").tips({side:3, msg:'用户名不能为空', bg:'#AE81FF', time:2});
		$("#imgUserName").focus();
		return false;
	}
	
	if($("#imgUserPass").val()==""){
		$("#imgUserPass").tips({side:3, msg:'密码不能为空', bg:'#AE81FF', time:2});
		$("#imgUserPass").focus();
		return false;
	}
	
	if($("#osType").val()=="redhat" && $("#imgPath").val()==""){
		$("#imgPath").tips({side:3, msg:'路径不能为空', bg:'#AE81FF', time:2});
		$("#imgPath").focus();
		return false;
	}
	
	if($("#imgExpireDate").val()==""){
		$("#imgExpireDate").tips({side:3, msg:'镜像到期时间不能为空', bg:'#AE81FF', time:2});
		$("#imgExpireDate").focus();
		return false;
	}
	
	if($("#expireDate").val()==""){
		$("#expireDate").tips({side:3, msg:'到期时间不能为空', bg:'#AE81FF', time:2});
		$("#expireDate").focus();
		return false;
	}
	
	//存储
	var diskTypeArr=new Array()
	$("select[name='diskType']").each(function() {
		diskTypeArr.push($(this).val());
	});
	var diskSizeArr=new Array()
	$("input[name='diskSize']").each(function() {
		diskSizeArr.push($(this).val());
	});
	var diskEncryptArr=new Array()
	$("input:checkbox[name='diskEncrypt']").each(function() {
		diskEncryptArr.push($(this).is(":checked")==true?"1":"0");
	});
	$("#diskTypeStr").val(diskTypeArr.join());
	$("#diskSizeStr").val(diskSizeArr.join());
	$("#diskEncryptStr").val(diskEncryptArr.join());
	
	//软件安装
	var softNameArr=new Array()
	$("select[name='softName']").each(function() {
		softNameArr.push($(this).val());
	});
	var softVerArr=new Array()
	$("select[name='softVer']").each(function() {
		softVerArr.push($(this).val());
	});
	var softParamArr=new Array()
	$("input[name='softParam']").each(function() {
		softParamArr.push($(this).val());
	});
	$("#softNameStr").val(softNameArr.join());
	$("#softVerStr").val(softVerArr.join());
	$("#softParamStr").val(softParamArr.join());
	return true;
}

//加入清单
function addList(){
	if($("#tcsq").is(".active")){//套餐数据校验
		if(checkPckgData()){
			$("#tcmainForm").submit();
		}
	}else if(checkData()){//数据校验
		$("#mainForm").submit();
	}
}

//保存为套餐预查询
function savePckgPre(){
	if(checkData()){//数据校验
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="保存为套餐";
		diag.URL = '<%=basePath%>/savePckgPre.do';
		diag.Width = 500;
		diag.Height = 80;
		diag.CancelEvent=function(){diag.close();};//关闭事件
		diag.OKEvent=function(){//OK事件
			$("#pckgFlag").val("1");//套餐标志：0-否；1-是
			$("#pckgName").val(diag.innerFrame.contentWindow.document.getElementById('pckgName').value);
			savePckg();//保存为套餐
			$("#pckgFlag").val("0");//套餐标志：0-否；1-是
			$("#pckgName").val("");//套餐名称
			diag.close();
		};
		diag.show();
	}
}

//保存为套餐
function savePckg(){
	var jsonObj={};
	jsonObj.areaCode=$("#areaCode").val();//地域代码                       
	jsonObj.platType=$("#platType").val();//平台类型                       
	jsonObj.deployType=$("#deployType").val();//部署类型                     
	jsonObj.envCode=$("#envCode").val();//环境代码                        
	jsonObj.resType=$("#resType").val();//资源类型                        
	jsonObj.virName=$("#virName").val();//虚拟机名称                      
	jsonObj.cpu=$("#cpu").val();//CPU                                 
	jsonObj.memory=$("#memory").val();//内存                             
	jsonObj.diskTypeStr=$("#diskTypeStr").val();//磁盘类型，多个用英文逗号分隔   
	jsonObj.diskSizeStr=$("#diskSizeStr").val();//磁盘大小，多个用英文逗号分隔   
	jsonObj.diskEncryptStr=$("#diskEncryptStr").val();//磁盘加密，多个用英文逗号分隔
	jsonObj.softNameStr=$("#softNameStr").val();//软件名称，多个用英文逗号分隔   
	jsonObj.softVerStr=$("#softVerStr").val();//软件版本，多个用英文逗号分隔    
	jsonObj.softParamStr=$("#softParamStr").val();//软件参数，多个用英文逗号分隔  
	jsonObj.projectCode=$("#projectCode").val();//项目代码                    
	jsonObj.osType=$("#osType").val();//操作系统类型                     
	jsonObj.osBitNum=$("#osBitNum").val();//操作系统位数                   
	jsonObj.imgCode=$("#imgCode").val();//镜像代码                        
	jsonObj.imgUserName=$("#imgUserName").val();//镜像用户名                  
	jsonObj.imgUserPass=$("#imgUserPass").val();//镜像用户密码                
	jsonObj.imgPath=$("#imgPath").val();//镜像路径                        
	jsonObj.imgExpireDate=$("#imgExpireDate").val();//镜像到期时间              
	jsonObj.expireDate=$("#expireDate").val();//到期时间                     
	jsonObj.virNum=$("#virNum").val();//虚拟机数量                       
	jsonObj.pckgName=$("#pckgName").val();//套餐名称                       
	jsonObj.pckgFlag=$("#pckgFlag").val();//套餐标志：0-否；1-是
	
    $.ajax({
	    type: 'post',  
	    url: 'savePckg.do',
	    data: jsonObj,
	    dataType: 'json',  
	    success: function(data){
	    	$("#savePckgBtnId").tips({side:1, msg:data.retMsg, bg:'#AE81FF', time:2});
	    },
	    error: function(data) {
	    	$("#savePckgBtnId").tips({side:3, msg:data.retMsg, bg:'#AE81FF', time:2});
	    }
	});
}

//磁盘大小失焦触发
function diskSizeFunc(obj, diskTypeId, iopsId){
	var diskSize=$(obj).val();
	if($(obj).val()==""){
		return;
	}
	
	if(!diskSize.match(/^\d*$/) || diskSize<20){
		$(obj).val("20");
		diskSize=20;
	}
	
	if(diskSize>32768){
		$(obj).val("32768");
		diskSize=32768;
	}
	
	if($("#"+diskTypeId).val()=="1"){//高效云盘1120+6
		$("#"+iopsId).html(1120+(diskSize-20)*6);
	}else if($("#"+diskTypeId).val()=="2"){//SSD云盘1800+30
		$("#"+iopsId).html(1800+(diskSize-20)*30);
	}
}

//点击tab页
var tcsq=false;
function tabFunc(tabId){
	if(tabId=="zdysq"){//自定义申请
		$("#savePckgBtnId").show();
		return;
	}else{//套餐申请
		$("#savePckgBtnId").hide();
		if(tcsq || $("#tcsq").is(".active")){
			return;
		}
		
		tcsq=true;
		$("#tcsq").load("pckgAppPre.do");
	}
}

//点击遮罩层
function maskLayerClick(){
	$('#shoppingCartId').toggleClass('open');//开关
	var isOpen=$('#shoppingCartId').hasClass("open");//是否开启
  	$('#shoppingCartTable').css('top', isOpen?"0px":"130px");
  	$('#maskLayerId').css('display', isOpen?"block":"none");
  	$('#shoppingCartId').css('min-height', (isOpen?$(window).height():0)+"px");
  	$('#shoppingCartId').css('max-height', (isOpen?$(window).height():0)+"px");
	$("#shoppingCartId").load("getOrderList.do");
}

//必须加<!DOCTYPE html>
//$(document).height();//整个网页的高度
//$(window).height();//浏览器可视窗口的高度
//$(window).scrollTop();//浏览器可视窗口顶端距离网页顶端的高度（垂直偏移）
$(window).scroll(function() {
	if ($('#btnId').offset().top + $('#btnId').height() <= $(window).height() + $(window).scrollTop()) {
    	$('#btnId').css('position', 'fixed');
    	$('#btnId').css('bottom', '0');
    	$('#btnId').css('backgroundColor', '#ffffff');
	}
})
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="#zdysq" onclick="tabFunc('zdysq')" data-toggle="tab">自定义申请</a></li>
	<li><a href="#tcsq" onclick="tabFunc('tcsq')" data-toggle="tab">套餐申请</a></li>
</ul>
<div class="tab-content">
<div id="zdysq" class="tab-pane fade in active">
	<form id="mainForm" name="mainForm" action="addList.do" enctype="multipart/form-data" method="post">
	<input type="hidden" name="areaCode" id="areaCode" value="1"/>
	<input type="hidden" name="platType" id="platType" value="vmware"/>
	<input type="hidden" name="deployType" id="deployType" value="1"/>
	<input type="hidden" name="envCode" id="envCode" value="1"/>
	<input type="hidden" name="resType" id="resType" value="1"/>
	<input type="hidden" name="cpu" id="cpu" value="1"/>
	<input type="hidden" name="memory" id="memory" value="1"/>
	<input type="hidden" name="diskTypeStr" id="diskTypeStr" value=""/><!-- 磁盘类型字符串 -->
	<input type="hidden" name="diskSizeStr" id="diskSizeStr" value=""/><!-- 磁盘大小字符串 -->
	<input type="hidden" name="diskEncryptStr" id="diskEncryptStr" value=""/><!-- 磁盘加密字符串 -->
	<input type="hidden" name="softNameStr" id="softNameStr" value=""/><!-- 软件名称字符串 -->
	<input type="hidden" name="softVerStr" id="softVerStr" value=""/><!-- 软件版本字符串 -->
	<input type="hidden" name="softParamStr" id="softParamStr" value=""/><!-- 软件参数字符串 -->
	<input type="hidden" name="pckgName" id="pckgName" value=""/><!-- 套餐名称 -->
	<input type="hidden" name="pckgFlag" id="pckgFlag" value="0"/><!-- 套餐标志：0-否；1-是 -->
	<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
		<tr class="tablecls">
			<td align="left" style="width: 10px;padding:10px;background-color:#cccccc;">地域</td>
			<td align="right" style="width: 120px;padding:10px;">地域：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="areaCodeId" class="ullitab list-inline">
					<c:if test="${not empty areaCodeList}">
					<c:forEach items="${areaCodeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'areaCode', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;">平台类型</td>
			<td align="right" style="width: 120px;padding:10px;">平台类型：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="platTypeId" class="ullitab list-inline">
					<c:if test="${not empty platTypeList}">
					<c:forEach items="${platTypeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'platType', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;">部署类型</td>
			<td align="right" style="width: 120px;padding:10px;">部署类型：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="deployTypeId" class="ullitab list-inline">
					<c:if test="${not empty deployTypeList}">
					<c:forEach items="${deployTypeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'deployType', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;" rowspan="2">项目</td>
			<td align="right" style="width: 120px;padding:10px;">环境：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="envCodeId" class="ullitab list-inline">
					<c:if test="${not empty envCodeList}">
					<c:forEach items="${envCodeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'envCode', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">项目：</td>
			<td align="left" style="width: 120px;padding:10px;" colspan="6">
				<select class="chosen-select form-control" name="projectCode" id="projectCode" data-placeholder="请选择项目" style="vertical-align:top;width: 120px;">
				<option value="">请选择</option>
				<c:forEach items="${projectCodeList}" var="var">
					<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;" rowspan="5">基本配置</td>
			<td align="right" style="width: 120px;padding:10px;">资源类型：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="resTypeId" class="ullitab list-inline">
					<c:if test="${not empty resTypeList}">
					<c:forEach items="${resTypeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'resType', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">虚拟机名称：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<input type="text" name="virName" id="virName"  value=""/>
			</td>
		</tr>
		<!-- <tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">虚拟机IP：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<input type="text" name="virIp" id="virIp"  value=""/>
			</td>
		</tr> -->
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">推荐配置：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="recommendTypeId" class="ullitab list-inline">
					<c:if test="${not empty recommendTypeList}">
					<c:forEach items="${recommendTypeList}" var="var" varStatus="st">
					<li onclick="setRecommendType(this, 'recommendType', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">CPU：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="cpuId" class="ullitab list-inline">
					<c:if test="${not empty cpuList}">
					<c:forEach items="${cpuList}" var="var" varStatus="st">
					<li id="cpuId${var.dictCode}" onclick="setFieldValue(this, 'cpu', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">内存：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="memoryId" class="ullitab list-inline">
					<c:if test="${not empty memoryList}">
					<c:forEach items="${memoryList}" var="var" varStatus="st">
					<li id="memoryId${var.dictCode}" onclick="setFieldValue(this, 'memory', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;" rowspan="3">镜像</td>
			<td align="right" style="width: 120px;padding:10px;">操作系统：</td>
			<td align="left" style="width: 120px;padding:10px;">
				<select class="chosen-select form-control" name="osType" id="osType" data-placeholder="请选择操作系统" style="vertical-align:top;width: 100%;">
				<option value="">请选择</option>
				<c:forEach items="${osTypeList}" var="var">
					<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="right" style="width: 120px;padding:10px;">位数：</td>
			<td align="left" style="width: 120px;padding:10px;">
				<select class="chosen-select form-control" name="osBitNum" id="osBitNum" data-placeholder="请选择位数" style="vertical-align:top;width: 100%;">
				<option value="">请选择</option>
				<c:forEach items="${osBitNumList}" var="var">
					<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="right" style="width: 120px;padding:10px;">镜像信息：</td>
			<td align="left" style="padding:10px;" colspan="2">
				<select class="chosen-select form-control" name="imgCode" id="imgCode" data-placeholder="请选择镜像" style="vertical-align:top;width: 120px;">
				<option value="">请选择</option>
				<c:forEach items="${imgCodeList}" var="var">
					<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">用户名：</td>
			<td align="left" style="width: 120px;padding:10px;">
				<input type="text" name="imgUserName" id="imgUserName"  value=""/>
			</td>
			<td align="right" style="width: 120px;padding:10px;">密码：</td>
			<td align="left" style="width: 120px;padding:10px;">
				<input type="text" name="imgUserPass" id="imgUserPass"  value=""/>
			</td>
			<td align="right" style="width: 120px;padding:10px;">路径：</td>
			<td align="left" style="padding:10px;" colspan="2">
				<input type="text" name="imgPath" id="imgPath"  value=""/>&nbsp;&nbsp;Linux系统必填
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">到期时间：</td>
			<td style="padding:10px;" colspan="6">
				<input type="text" name="imgExpireDate" id="imgExpireDate" value="" class="span10 date-picker" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:120px;" placeholder="到期时间"/>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;">存储</td>
			<td align="right" style="width: 120px;padding:10px;">存储：</td>
			<td style="padding:10px;" colspan="6">
				<table id="diskTableId">
					<tr id="diskTrId">
						<td align="left" style="width: 120px;">
							<select class="chosen-select form-control" name="diskType" id="diskType" data-placeholder="请选择磁盘类型" style="vertical-align:top;width: 120px;">
							<option value="">请选择</option>
							<c:forEach items="${diskTypeList}" var="var">
								<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
							</c:forEach>
						  	</select>
						</td>
						<td align="left" style="width: 120px;padding:10px;">
							<input type="text" name="diskSize" id="diskSize" value="20" style="width: 120px;" maxlength="5" onblur="diskSizeFunc(this, 'diskType', 'iopsId')"/>
						</td>
						<td align="left" style="width: 200px;">
						  	GB&nbsp;&nbsp;&nbsp;<span id="iopsId">1120</span>&nbsp;IOPS&nbsp;&nbsp;&nbsp;<input name="diskEncrypt" type="checkbox" value=""/>加密
						</td>
					</tr>
				</table>
				<a href="javascript:void()" onclick="addDiskRow()"><span class="glyphicon glyphicon-plus"></span></a>增加磁盘，您可选配15块
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;">软件安装</td>
			<td align="right" style="width: 120px;padding:10px;">安装软件：</td>
			<td style="padding:10px;" colspan="6">
				<table id="softTableId">
					<tr id="softTrId">
						<td align="left" style="width: 120px;">
							<select class="chosen-select form-control" name="softName" id="softName" data-placeholder="请选择软件名称" style="vertical-align:top;width: 120px;">
							<option value="">请选择</option>
							<c:forEach items="${softNameList}" var="var">
								<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
							</c:forEach>
						  	</select>
						</td>
						<td align="left" style="width: 120px;padding:10px;">
							<select class="chosen-select form-control" name="softVer" id="softVer" data-placeholder="请选择软件版本" style="vertical-align:top;width: 120px;">
							<option value="">请选择</option>
							<c:forEach items="${softVerList}" var="var">
								<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
							</c:forEach>
						  	</select>
						</td>
						<td align="left" style="width: 120px;">
							<input type="hidden" name="softParam" id="softParam" value=""/>
						  	<a href="javascript:void()" onclick="setSoftParam(0)">设置参数</a>
						</td>
					</tr>
				</table>
				<a href="javascript:void()" onclick="addSoftRow()"><span class="glyphicon glyphicon-plus"></span></a>增加安装软件，您可选择15个
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;" rowspan="2">数量</td>
			<td align="right" style="width: 120px;padding:10px;">到期时间：</td>
			<td style="padding:10px;" colspan="6">
				<input type="text" name="expireDate" id="expireDate" value="" class="span10 date-picker" onclick="checkExpireDate(false)" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:120px;" placeholder="到期时间"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="expireDateChk" value="" onclick="checkExpireDate(true)"/>永久
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">数量：</td>
			<td style="width: 120px;padding:10px;" colspan="6">
				<div class="input-group spinner" data-trigger="spinner" id="spinner" style="width: 120px;"> 
				    <input type="text" id="virNum" name="virNum" class="form-control" value="1" data-max="1000" data-min="1" data-step="1"> 
				    <div class="input-group-addon"> 
				        <a href="javascript:void();" class="spin-up" data-spin="up"><i class="icon-sort-up"></i></a> 
				        <a href="javascript:void();" class="spin-down" data-spin="down"><i class="icon-sort-down"></i></a> 
				    </div> 
				</div>
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;</td>
		<tr class="tablecls">
			<td align="left" style="width: 20px;padding:10px;background-color:#cccccc;" rowspan="2">当前配置</td>
			<td align="right" style="width: 120px;padding:10px;">当前配置：</td>
			<td style="padding:10px;" colspan="6"></td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding:10px;">计费方式：</td>
			<td align="left" style="width: 120px;padding:10px;">包年包月</td>
			<td align="right" style="width: 120px;padding:10px;">地域：</td>
			<td align="left" style="width: 120px;padding:10px;"><span id="areaCodeLbl"></span></td>
			<td align="right" style="width: 120px;padding:10px;">实例规格：</td>
			<td align="left" style="width: 120px;padding:10px;" colspan="2"><span id="areaCodeLbl"></span></td>
		</tr>
	</table>
	</form>
</div>
<div id="tcsq" class="tab-pane fade"></div>
</div>
<!-- glyphicon glyphicon-time -->
<table id="btnId" style="width:100%;border-top:1px solid #f5f5f5;">
	<tr>
		<td align="left" style="padding:10px;">
			<div class="divbtn">
			    <span class="btncls" style="background-color:#f5620a;"><a id="addList" href="javascript:void()" onclick="addList()">加入清单</a></span>  
			    <span style="width:30px;float:right;">&nbsp;</span>
			    <span id="savePckgBtnId" class="btncls"><a href="javascript:void()" onclick="savePckgPre()">保存为套餐</a></span>
			</div>
		</td>
	</tr>
</table>
<table style="width:100%;border-top:1px solid #f5f5f5;"><tr><td style="padding:10px;"><div class="divbtn"></div></td></tr></table>
<table id="shoppingCartTable" style="position: fixed; right: 0; top: 130px; z-index: 999999999;">
	<tr>
		<td onclick="maskLayerClick()" align="center"><div style="cursor:pointer; float: left;width: 50px;padding:10px;background-color:#cccccc;"><span class="glyphicon glyphicon-shopping-cart"></span><div style="width: 10px;">购买清单</div><span id="shoppingCartNum" style="color: #f5620a">5000</span></div></td>
		<td id="shoppingCartId" class="shoppingCart" style="overflow-y: auto;"></td>
	</tr>
</table>
<div id="maskLayerId" onclick="maskLayerClick()" style="width: 100%; height: 100%; border-top: white1pxgroove;padding-bottom: 5px;padding-top: 0px;background-color: buttonface;text-align: center;background-color: #7f7f7f;filter: alpha(opacity=1);left: 0px;position: absolute;top: 1px;z-index: 3;display: none;cursor: default"></div><!-- 遮罩层 -->
<script type="text/javascript">
$(top.hangge());
$('.date-picker').datepicker({autoclose: true,todayHighlight: true});//datepicker
</script>
</body>
</html>