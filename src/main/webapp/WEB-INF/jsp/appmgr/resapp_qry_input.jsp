<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="/t-tags"%>
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
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
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

//发送Ajax请求
function ajaxHttpPost(url, jsonObj, tipId, numId){
    $.ajax({
	    type: 'post',  
	    url: url,
	    data: jsonObj,
	    dataType: 'json',  
	    success: function(data){
	    	$("#"+tipId).tips({side:1, msg:data.retMsg, bg:'#AE81FF', time:2});
	    	if($("#"+numId).length>0){
	    		$("#"+numId).html(($("#"+numId).html())*1+1);
	    	}
	    },
	    error: function(data) {
	    	$("#"+tipId).tips({side:3, msg:data.retMsg, bg:'#AE81FF', time:2});
	    }
	});
}

//点击推荐配置
function setRecommendType(obj, fieldName, fieldValue){
	setFieldCls(obj, fieldName);
	var cpuCode=fieldValue.substring(0, 1);
	var memoryCode=fieldValue.substring(1);
	setFieldValue(document.getElementById("cpuId"+cpuCode), 'cpu', cpuCode);
	setFieldValue(document.getElementById("memoryId"+memoryCode), 'memory', memoryCode);
	specFunc(cpuCode, memoryCode);//实例规格改变时触发
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
	
	expireDateFunc();//到期时间改变时触发
}

//新增磁盘行
function addDiskRow(){
    var len = $("#diskTableId tr").length;
    if(len>=15){
    	$("#diskTableId").tips({side:3, msg:'只能选配15块磁盘', bg:'#AE81FF', time:2});
    	return;
    }
    
    var tdStr="<td align=\"left\" style=\"width: 120px;padding-right:10px;padding-top:10px;\"><select class=\"chosen-select form-control\" name=\"diskType\" id=\"diskType"+(len+1)+"\" data-placeholder=\"请选择磁盘类型\" style=\"vertical-align:top;width: 120px;\" onchange=\"diskTypeFunc()\">"+$("#diskType").html()+"</select></td>"
	    +"<td align=\"left\" style=\"padding-top:10px;\"><input type=\"text\" name=\"diskSize\" id=\"diskSize\" value=\"20\" style=\"width: 120px;\" maxlength=\"5\" onblur=\"diskSizeFunc(this, 'diskType', 'iopsId"+(len+1)+"')\" onchange=\"diskTypeFunc()\"/></td>"
	    +"<td align=\"left\" style=\"padding-top:10px;\">GB</td>"
	    +"<td align=\"right\" style=\"padding-top:10px;\"><span id=\"iopsId"+(len+1)+"\">1120</span>&nbsp;IOPS&nbsp;<input name=\"diskEncrypt\" type=\"checkbox\" value=\"\"/>加密&nbsp;<a href=\"javascript:void()\" onclick=\"delRow('diskTrId"+(len+1)+"')\"><span class=\"glyphicon glyphicon-remove\"></span></a></td>";
    $("#diskTableId").append("<tr id=\"diskTrId"+(len+1)+"\">"+tdStr+"</tr>");
}

//新增软件安装行
function addSoftRow(){
    var len = $("#softTableId tr").length;
    if(len>=15){
    	$("#softTableId").tips({side:3, msg:'只能选择15个安装软件', bg:'#AE81FF', time:2});
    	return;
    }
    
    var tdStr="<td align=\"left\" style=\"width: 120px;padding-right:10px;padding-top:10px;\"><select class=\"chosen-select form-control\" name=\"softCode\" data-placeholder=\"请选择软件名称\" style=\"vertical-align:top;width: 120px;\">"+$("#softCode").html()+"</select></td>"
	    +"<td align=\"right\" style=\"padding-top:10px;\"><input type=\"hidden\" name=\"softParam\" id=\"softParam"+(len+1)+"\" value=\"\"/><a href=\"javascript:void()\" onclick=\"setSoftParam("+(len+1)+")\">设置参数</a>&nbsp;<a href=\"javascript:void()\" onclick=\"delRow('softTrId"+(len+1)+"')\"><span class=\"glyphicon glyphicon-remove\"></span></a></td>";
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
function checkData(btnId){
	if($("#envCode").val()==""){
		$("#"+btnId).tips({side:1, msg:'环境不能为空', bg:'#AE81FF', time:2});
		$("#"+btnId).focus();
		return false;
	}
	
	if($("#projectCode").val()==""){
		$("#projectCode").tips({side:3, msg:'请选择项目', bg:'#AE81FF', time:2});
		$("#projectCode").focus();
		return false;
	}
	
	if($("#virName").val()==""){
		$("#virName").tips({side:3, msg:'虚拟机名称不能为空', bg:'#AE81FF', time:2});
		$("#virName").focus();
		return false;
	}
	
	if($("#osType").val()==""){
		$("#osType").tips({side:3, msg:'请选择操作系统', bg:'#AE81FF', time:2});
		$("#osType").focus();
		return false;
	}
	
	if($("#osBitNum").val()==""){
		$("#osBitNum").tips({side:3, msg:'请选择位数', bg:'#AE81FF', time:2});
		$("#osBitNum").focus();
		return false;
	}
	
	if($("#imgCode").val()==""){
		$("#imgCode").tips({side:3, msg:'请选择镜像', bg:'#AE81FF', time:2});
		$("#imgCode").focus();
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
	
	if($("#softCode").val()==""){
		$("#softCode").tips({side:3, msg:'请选择软件安装', bg:'#AE81FF', time:2});
		$("#softCode").focus();
		return false;
	}
	
	if($("#expireDate").val()==""){
		$("#expireDate").tips({side:3, msg:'到期时间不能为空', bg:'#AE81FF', time:2});
		$("#expireDate").focus();
		return false;
	}
	
	//存储
	var diskTypeArr=new Array();
	$("select[name='diskType']").each(function() {
		diskTypeArr.push($(this).val());
	});
	var diskSizeArr=new Array();
	$("input[name='diskSize']").each(function() {
		diskSizeArr.push($(this).val());
	});
	var diskEncryptArr=new Array();
	$("input:checkbox[name='diskEncrypt']").each(function() {
		diskEncryptArr.push($(this).is(":checked")==true?"1":"0");
	});
	$("#diskTypeStr").val(diskTypeArr.join());
	$("#diskSizeStr").val(diskSizeArr.join());
	$("#diskEncryptStr").val(diskEncryptArr.join());
	
	//软件安装
	var softCodeArr=new Array();
	$("select[name='softCode']").each(function() {
		softCodeArr.push($(this).val());
	});
	var softParamArr=new Array();
	$("input[name='softParam']").each(function() {
		softParamArr.push($(this).val());
	});
	$("#softCodeStr").val(softCodeArr.join());
	$("#softParamStr").val(softParamArr.join());
	return true;
}

//加入清单
function addList(){
	var jsonObj={};//JSON请求数据
	if($("#tcsq").is(".active")){//套餐数据校验
		if(checkPckgData()){
			jsonObj.tcareaCode=$("#tcareaCode").val();//地域代码                       
			jsonObj.tcplatType=$("#tcplatType").val();//平台类型                       
			jsonObj.tcdeployType=$("#tcdeployType").val();//部署类型                     
			jsonObj.tcvirName=$("#tcvirName").val();//虚拟机名称
			jsonObj.pckgId=$("#pckgId").val();//套餐ID     
			ajaxHttpPost("addPckgList.do", jsonObj, "addListBtnId", "shoppingCartNum");//发送Ajax请求
		}
	}else if(checkData("addListBtnId")){//数据校验
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
		jsonObj.softCodeStr=$("#softCodeStr").val();//软件代码，多个用英文逗号分隔   
		jsonObj.softParamStr=$("#softParamStr").val();//软件参数，多个用英文逗号分隔  
		jsonObj.projectCode=$("#projectCode").val();//项目代码                    
		jsonObj.osType=$("#osType").val();//操作系统类型                     
		jsonObj.osBitNum=$("#osBitNum").val();//操作系统位数                   
		jsonObj.imgCode=$("#imgCode").val();//镜像代码                        
		jsonObj.imgUserName=$("#imgUserName").val();//镜像用户名                  
		jsonObj.imgUserPass=$("#imgUserPass").val();//镜像用户密码                
		jsonObj.imgPath=$("#imgPath").val();//镜像路径                        
		jsonObj.expireDate=$("#expireDate").val();//到期时间                     
		jsonObj.virNum=$("#virNum").val();//虚拟机数量                       
		jsonObj.pckgName=$("#pckgName").val();//套餐名称                       
		jsonObj.status=$("#status").val();//状态：0-待提交；1-已提交；T-套餐
		ajaxHttpPost("addList.do", jsonObj, "addListBtnId", "shoppingCartNum");//发送Ajax请求
	}
}

//保存为套餐预查询
function savePckgPre(){
	if(checkData("savePckgBtnId")){//数据校验
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="保存为套餐";
		diag.URL = '<%=basePath%>/savePckgPre.do';
		diag.Width = 500;
		diag.Height = 80;
		diag.CancelEvent=function(){diag.close();};//关闭事件
		diag.OKEvent=function(){//OK事件
			$("#status").val("T");//状态：0-待提交；1-已提交；T-套餐
			$("#pckgName").val(diag.innerFrame.contentWindow.document.getElementById('pckgName').value);
			savePckg();//保存为套餐
			$("#status").val("0");//状态：0-待提交；1-已提交；T-套餐
			$("#pckgName").val("");//套餐名称
			diag.close();
		};
		diag.show();
	}
}

//保存为套餐
function savePckg(){
	var jsonObj={};//JSON请求数据
	//jsonObj.areaCode=$("#areaCode").val();//地域代码
	//jsonObj.platType=$("#platType").val();//平台类型
	//jsonObj.deployType=$("#deployType").val();//部署类型
	jsonObj.envCode=$("#envCode").val();//环境代码                        
	jsonObj.resType=$("#resType").val();//资源类型                        
	jsonObj.virName=$("#virName").val();//虚拟机名称                      
	jsonObj.cpu=$("#cpu").val();//CPU                                 
	jsonObj.memory=$("#memory").val();//内存                             
	jsonObj.diskTypeStr=$("#diskTypeStr").val();//磁盘类型，多个用英文逗号分隔   
	jsonObj.diskSizeStr=$("#diskSizeStr").val();//磁盘大小，多个用英文逗号分隔   
	jsonObj.diskEncryptStr=$("#diskEncryptStr").val();//磁盘加密，多个用英文逗号分隔
	jsonObj.softCodeStr=$("#softCodeStr").val();//软件代码，多个用英文逗号分隔   
	jsonObj.softParamStr=$("#softParamStr").val();//软件参数，多个用英文逗号分隔  
	jsonObj.projectCode=$("#projectCode").val();//项目代码                    
	jsonObj.osType=$("#osType").val();//操作系统类型                     
	jsonObj.osBitNum=$("#osBitNum").val();//操作系统位数                   
	jsonObj.imgCode=$("#imgCode").val();//镜像代码                        
	jsonObj.imgUserName=$("#imgUserName").val();//镜像用户名                  
	jsonObj.imgUserPass=$("#imgUserPass").val();//镜像用户密码                
	jsonObj.imgPath=$("#imgPath").val();//镜像路径                        
	jsonObj.expireDate=$("#expireDate").val();//到期时间                     
	jsonObj.virNum=$("#virNum").val();//虚拟机数量                       
	jsonObj.pckgName=$("#pckgName").val();//套餐名称                       
	jsonObj.status=$("#status").val();//状态：0-待提交；1-已提交；T-套餐
	ajaxHttpPost("savePckg.do", jsonObj, "savePckgBtnId");//发送Ajax请求
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
function tabFunc(tabId){
	if(tabId=="zdysq"){//自定义申请
		$("#savePckgBtnId").show();
		return;
	}else{//套餐申请
		$("#savePckgBtnId").hide();
		$("#tcsq").load("pckgAppPre.do");
	}
}

//点击遮罩层
function maskLayerClick(id){
	if(id=="shoppingCart"){//购物车
		if(!$('#shoppingCartId').hasClass("open") && $("#shoppingCartNum").html()=='0'){
			return;
		}
		
		if($('#buyHisId').hasClass("open")){
			maskLayerClick("buyHis");//点击遮罩层
		}
		
		$('#shoppingCartId').toggleClass('open');//开关
		var isOpen=$('#shoppingCartId').hasClass("open");//是否开启
	  	$('#maskLayerId').css('display', isOpen?"block":"none");
	  	$('#shoppingCartTable').css('top', isOpen?"0px":"130px");
	  	$('#shoppingCartId').css('min-height', (isOpen?$(window).height():0)+"px");
	  	$('#shoppingCartId').css('max-height', (isOpen?$(window).height():0)+"px");
	  	$('#batchBuy').css('display', isOpen?"block":"none");
	  	$('#getShoppingCartList').css('height', (isOpen?$(window).height()-50:0)+"px");
	  	if(isOpen){
			$("#getShoppingCartList").load("getShoppingCartList.do");
	  	}
	}else if(id=="buyHis"){//已购历史
		if(!$('#buyHisId').hasClass("open") && $("#buyHisNum").html()=='0'){
			return;
		}
		
		if($('#shoppingCartId').hasClass("open")){
			maskLayerClick("shoppingCart");//点击遮罩层
		}
		
		$('#buyHisId').toggleClass('open');//开关
		var isOpen=$('#buyHisId').hasClass("open");//是否开启
		$('#maskLayerId').css('display', isOpen?"block":"none");
	  	$('#buyHisTable').css('top', isOpen?"0px":"280px");
	  	$('#buyHisId').css('min-height', (isOpen?$(window).height():0)+"px");
	  	$('#buyHisId').css('max-height', (isOpen?$(window).height():0)+"px");
	  	$('#getBuyHisList').css('height', (isOpen?$(window).height():0)+"px");
	  	$("#getBuyHisList").load("getBuyHisList.do");
	}else{//遮罩层
		maskLayerClick($('#shoppingCartId').hasClass("open")?"shoppingCart":"buyHis");//点击遮罩层
	}
}

//重新加载已购历史列表
function reloadBuyHis(beginDate, endDate, projCode){
	$("#getBuyHisList").load("getBuyHisList.do?beginDate="+beginDate+"&endDate="+endDate+"&projCode="+projCode);
}

//资源类型改变时触发
function resTypeFunc(val){
	$("#resTypeLabel").html(val);
}

//实例规格改变时触发
function specFunc(cpuVal, memoryVal){
	cpuVal=(cpuVal==""?$("#cpu").val():cpuVal);//CPU                                 
	memoryVal=(memoryVal==""?$("#memory").val():memoryVal);//内存
	$("#specLabel").html(cpuVal+"&nbsp;核&nbsp;"+memoryVal+"&nbsp;GB");
	getTotalAmt();//计算金额
}

//镜像改变时触发
function imgFunc(){
	var osTypeName="";
	if($("#osType").val()!=''){
		osTypeName=$("#osType").find("option:selected").text();
	}
	
	var osBitNumName="";
	if($("#osBitNum").val()!='' && $("#osBitNum").val()!=''){
		osBitNumName=$("#osBitNum").find("option:selected").text();
	}
	
	$("#imgLabel").html(osTypeName+"&nbsp;"+osBitNumName);
}

//数据盘改变时触发
function diskTypeFunc(){
	var diskTypeArr=new Array();
	$("select[name='diskType']").each(function() {
		var diskTypeName="";
		if($(this).val()!=''){
			diskTypeName=$(this).find("option:selected").text();
		}
		
		diskTypeArr.push(diskTypeName);
	});
	var diskSizeArr=new Array();
	$("input[name='diskSize']").each(function() {
		diskSizeArr.push($(this).val());
	});
	
	var diskTypeLabel="";
	for (var i=0;i<diskTypeArr.length;i++){
		diskTypeLabel+=diskTypeArr[i]+"&nbsp;(&nbsp;"+diskSizeArr[i]+"&nbsp;GB)<br>";
	}
	
	$("#diskTypeLabel").html(diskTypeLabel);
}

//数量改变时触发
function virNumFunc(operType){
	var virNum=$("#virNum").val();
	if(isNaN(virNum)){
		virNum=virNum.match(new RegExp("^\\d+"));
		if(virNum=='') virNum=1;
	}
	
	virNum=virNum*1;
	if(operType=='+'){
		virNum++;
	}else if(operType=='-'){
		virNum--;
	}
	
	if(virNum<1) virNum=1;
	if(virNum>1000) virNum=1000;
	$("#virNumLabel").html(virNum+"&nbsp;台");
}

//到期时间改变时触发
function expireDateFunc(){
	$("#expireDateLabel").html($("#expireDate").val());
}

//计算金额
function getTotalAmt(){
	var amt="123456789.00";
	$("#totalAmt").html(amtFmt(amt));
}

//购物车计算金额
function getAllTotalAmt(){
	var amt="123456789.00";
	$("#allTotalAmt").html(amtFmt(amt));
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
	<form id="mainForm" name="mainForm" action="" enctype="multipart/form-data" method="post">
	<input type="hidden" name="areaCode" id="areaCode" value="1"/>
	<input type="hidden" name="platType" id="platType" value="vmware"/>
	<input type="hidden" name="deployType" id="deployType" value="1"/>
	<input type="hidden" name="envCode" id="envCode" value="${defaultEnvCode}"/>
	<input type="hidden" name="resType" id="resType" value="1"/>
	<input type="hidden" name="cpu" id="cpu" value="1"/>
	<input type="hidden" name="memory" id="memory" value="1"/>
	<input type="hidden" name="diskTypeStr" id="diskTypeStr" value=""/><!-- 磁盘类型字符串 -->
	<input type="hidden" name="diskSizeStr" id="diskSizeStr" value=""/><!-- 磁盘大小字符串 -->
	<input type="hidden" name="diskEncryptStr" id="diskEncryptStr" value=""/><!-- 磁盘加密字符串 -->
	<input type="hidden" name="softCodeStr" id="softCodeStr" value=""/><!-- 软件代码字符串 -->
	<input type="hidden" name="softParamStr" id="softParamStr" value=""/><!-- 软件参数字符串 -->
	<input type="hidden" name="status" id="status" value="0"/><!-- 状态：0-待提交；1-已提交；T-套餐 -->
	<input type="hidden" name="pckgName" id="pckgName" value=""/><!-- 套餐名称 -->
	<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
		<tr class="tablecls">
			<td align="left" style="width: 90px;padding-left:10px;background-color:#cccccc;" valign="middle"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;地域</td>
			<td align="right" style="width: 120px;padding:10px;">&nbsp;</td>
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
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;平台类型</td>
			<td align="right" style="width: 120px;padding:10px;"></td>
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
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;部署类型</td>
			<td align="right" style="width: 120px;padding:10px;"></td>
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
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle" rowspan="2"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;项目</td>
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
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">项目：</td>
			<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;">
				<select class="chosen-select form-control" name="projectCode" id="projectCode" data-placeholder="请选择项目" style="vertical-align:top;width: 100%;">
				<option value="">请选择</option>
				<c:forEach items="${projectList}" var="var">
					<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="left" style="padding-right:10px;padding-bottom:10px;" colspan="5">&nbsp;</td>
		</tr>
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle" rowspan="5"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;基本配置</td>
			<td align="right" style="width: 120px;padding:10px;">资源类型：</td>
			<td align="left" style="padding:10px;" colspan="6">
				<ul id="resTypeId" class="ullitab list-inline">
					<c:if test="${not empty resTypeList}">
					<c:forEach items="${resTypeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'resType', '${var.dictCode}');resTypeFunc('${var.dictValue}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">虚拟机名称：</td>
			<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;">
				<input type="text" name="virName" id="virName" style="width: 100%;" value=""/>
			</td>
			<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="5">&nbsp;</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">推荐配置：</td>
			<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="6">
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
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">CPU：</td>
			<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="6">
				<ul id="cpuId" class="ullitab list-inline">
					<c:if test="${not empty cpuList}">
					<c:forEach items="${cpuList}" var="var" varStatus="st">
					<li id="cpuId${var.dictCode}" onclick="setFieldValue(this, 'cpu', '${var.dictCode}');specFunc('${var.dictCode}', '');" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">内存：</td>
			<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="6">
				<ul id="memoryId" class="ullitab list-inline">
					<c:if test="${not empty memoryList}">
					<c:forEach items="${memoryList}" var="var" varStatus="st">
					<li id="memoryId${var.dictCode}" onclick="setFieldValue(this, 'memory', '${var.dictCode}');specFunc('', '${var.dictCode}');" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle" rowspan="2"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;镜像</td>
			<td align="right" style="width: 120px;padding:10px;">操作系统：</td>
			<td align="left" style="width: 120px;padding-left:10px;padding-top:10px;padding-bottom:10px;">
				<select class="chosen-select form-control" name="osType" id="osType" data-placeholder="请选择操作系统" style="vertical-align:top;width: 100%;" onchange="imgFunc()">
				<option value="">请选择</option>
				<c:forEach items="${osTypeList}" var="var">
					<option value="${var.dictCode}">${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="right" style="width: 120px;padding:10px;">位数：</td>
			<td align="left" style="width: 120px;padding-left:10px;padding-top:10px;padding-bottom:10px;">
				<select class="chosen-select form-control" name="osBitNum" id="osBitNum" data-placeholder="请选择位数" style="vertical-align:top;width: 100%;" onchange="imgFunc()">
				<option value="">请选择</option>
				<c:forEach items="${osBitNumList}" var="var">
					<option value="${var.dictCode}">${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="right" style="width: 120px;padding:10px;">镜像信息：</td>
			<td align="left" style="width: 180px;padding-left:10px;padding-top:10px;padding-bottom:10px;">
				<select class="chosen-select form-control" name="imgCode" id="imgCode" data-placeholder="请选择镜像" style="vertical-align:top;width: 100%;">
				<option value="">请选择</option>
				<c:forEach items="${imgCodeList}" var="var">
					<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="left" style="padding-left:10px;padding-bottom:10px;">&nbsp;</td>
		</tr>
		<tr class="tablecls">
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">用户名：</td>
			<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;">
				<input type="text" name="imgUserName" id="imgUserName" style="width: 100%;" value=""/>
			</td>
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">密码：</td>
			<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;">
				<input type="password" name="imgUserPass" id="imgUserPass" style="width: 100%;" value=""/>
			</td>
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">路径：</td>
			<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;">
				<input type="text" name="imgPath" id="imgPath" style="width: 100%;" value="/opt"/>
			</td>
			<td align="left" style="padding-left:10px;padding-bottom:10px;">
				Linux系统必填
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;存储</td>
			<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;"></td>
			<td style="padding:10px;" colspan="6">
				<table id="diskTableId">
					<tr id="diskTrId">
						<td align="left" style="width: 120px;padding-right:10px;">
							<select class="chosen-select form-control" name="diskType" id="diskType" data-placeholder="请选择磁盘类型" style="vertical-align:top;width: 120px;" onchange="diskTypeFunc()">
							<option value="">请选择</option>
							<c:forEach items="${diskTypeList}" var="var">
								<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
							</c:forEach>
						  	</select>
						</td>
						<td align="left" style="width: 120px;">
							<input type="text" name="diskSize" id="diskSize" value="20" style="width: 120px;" maxlength="5" onblur="diskSizeFunc(this, 'diskType', 'iopsId')" onchange="diskTypeFunc()"/>
						</td>
						<td align="left" style="width: 20px;">GB</td>
						<td align="right" style="padding-right:13px;">
						  	<span id="iopsId">1120</span>&nbsp;IOPS&nbsp;<input name="diskEncrypt" type="checkbox" value=""/>加密&nbsp;
						</td>
						<td align="left" valign="bottom" style="width: 200px;padding-bottom:8px;" rowspan="15">
							&nbsp;<a href="javascript:void()" onclick="addDiskRow()"><span class="glyphicon glyphicon-plus"></span></a>增加磁盘，您可选配15块
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;软件安装</td>
			<td align="right" style="width: 120px;padding:10px;"></td>
			<td style="padding:10px;" colspan="6">
				<table id="softTableId">
					<tr id="softTrId">
						<td align="left" style="width: 120px;padding-right:10px;">
							<select class="chosen-select form-control" name="softCode" id="softCode" data-placeholder="请选择软件名称" style="vertical-align:top;width: 120px;">
							<option value="">请选择</option>
							<c:forEach items="${softCodeList}" var="var">
								<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
							</c:forEach>
						  	</select>
						</td>
						<td align="right" style="padding-right:13px;">
							<input type="hidden" name="softParam" id="softParam" value=""/><a href="javascript:void()" onclick="setSoftParam(0)">设置参数</a>&nbsp;
						</td>
						<td align="left" valign="bottom" style="width: 200px;padding-bottom:9px;" rowspan="15">
							&nbsp;<a href="javascript:void()" onclick="addSoftRow()"><span class="glyphicon glyphicon-plus"></span></a>增加安装软件，您可选择15个
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;数量</td>
			<td align="right" style="width: 120px;padding:10px;">&nbsp;</td>
			<td style="width: 120px;padding:10px;" colspan="6">
				<div class="input-group spinner" data-trigger="spinner" id="spinner" style="width: 120px;"> 
				    <input type="text" id="virNum" name="virNum" class="form-control" value="1" data-max="1000" data-min="1" data-step="1" onchange="virNumFunc()"> 
				    <div class="input-group-addon"> 
				        <a href="javascript:void();" onclick="virNumFunc('+')" class="spin-up" data-spin="up"><i class="icon-sort-up"></i></a> 
				        <a href="javascript:void();" onclick="virNumFunc('-')" class="spin-down" data-spin="down"><i class="icon-sort-down"></i></a> 
				    </div> 
				</div>
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;到期时间</td>
			<td align="right" style="width: 120px;padding:10px;">&nbsp;</td>
			<td style="padding:10px;" colspan="6">
				<input type="text" name="expireDate" id="expireDate" value="" class="span10 date-picker" onclick="checkExpireDate(false)" onchange="expireDateFunc()" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:120px;" placeholder="到期时间"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="expireDateChk" value="" onclick="checkExpireDate(true)"/>永久
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" valign="middle" rowspan="2"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;当前配置</td>
			<td align="right" valign="top" style="width: 120px;padding:10px;">资源类型：</td>
			<td id="resTypeLabel" align="left" valign="top" style="width: 180px;padding:10px;">云主机</td>
			<td align="right" valign="top" style="width: 120px;padding:10px;">实例规格：</td>
			<td id="specLabel" align="left" valign="top" style="width: 180px;padding:10px;">1&nbsp;核&nbsp;1&nbsp;GB</td>
			<td align="right" valign="top" style="width: 120px;padding:10px;">镜像：</td>
			<td id="imgLabel" align="left" valign="top" style="width: 180px;padding:10px;" colspan="2"></td>
		</tr>
		<tr class="tablecls">
			<td align="right" valign="top" style="width: 120px;padding:10px;">数据盘：</td>
			<td id="diskTypeLabel" align="left" valign="top" style="width: 180px;padding:10px;">高效云盘&nbsp;(&nbsp;20&nbsp;GB)</td>
			<td align="right" valign="top" style="width: 120px;padding:10px;">购买量：</td>
			<td id="virNumLabel" align="left" valign="top" style="width: 180px;padding:10px;">1&nbsp;台</td>
			<td align="right" valign="top" style="width: 120px;padding:10px;">到期时间：</td>
			<td id="expireDateLabel" align="left" valign="top" style="width: 180px;padding:10px;" colspan="2"></td>
		</tr>
	</table>
	</form>
</div>
<div id="tcsq" class="tab-pane fade"></div>
</div>
<!-- 按钮 -->
<table id="btnId" style="width:100%;border-top:1px solid #f5f5f5;">
	<tr>
		<td align="left" style="padding:10px;">
			<div class="divbtn">
				配置费用：<span id="totalAmt" style="font-size:26px;color: #f5620a;">￥0.00</span>
			    <span id="addListBtnId" class="btncls" style="background-color:#f5620a;"><a id="addList" href="javascript:void()" onclick="addList()">加入清单</a></span>  
			    <span style="width:30px;float:right;">&nbsp;</span>
			    <span id="savePckgBtnId" class="btncls"><a href="javascript:void()" onclick="savePckgPre()">保存为套餐</a></span>
			</div>
		</td>
	</tr>
</table>
<table style="width:100%;border-top:1px solid #f5f5f5;"><tr><td style="padding:10px;"><div class="divbtn"></div></td></tr></table>
<!-- 购物车 -->
<table id="shoppingCartTable" style="position: fixed; right: 0; top: 130px; z-index: 999999999;">
	<tr>
		<td onclick="maskLayerClick('shoppingCart')" align="center"><div style="cursor:pointer; float: left;width: 50px;padding:10px;background-color:#cccccc;"><span class="glyphicon glyphicon-shopping-cart"></span><div style="width: 10px;">购买清单</div><span id="shoppingCartNum" style="color: #f5620a;">0</span></div></td>
		<td id="shoppingCartId" class="shoppingCart">
			<div id="getShoppingCartList" style="height:0px;overflow-y: auto;"></div>
			<div id="batchBuy" class="divbtn" style="display:none;width:100%;height:50px;padding:10px;border-top:1px solid #f5f5f5;">
				共计：<span id="allTotalAmt" style="font-size:26px;color: #f5620a;">￥0.00</span>
				<span class="btncls" style="width:100px;background-color:#f5620a;"><a href="javascript:void()" onclick="batchBuy()">批量购买</a></span>
				<span style="width:20px;float:right;">&nbsp;</span>
			    <span class="btncls" style="width:100px;"><a href="javascript:void()" onclick="clearShoppingCart()">清空购物车</a></span>
			</div>
		</td>
	</tr>
</table>
<!-- 购买历史 -->
<table id="buyHisTable" style="position: fixed; right: 0; top: 280px; z-index: 999999999;">
	<tr>
		<td onclick="maskLayerClick('buyHis')" align="center"><div style="cursor:pointer; float: left;width: 50px;padding:10px;background-color:#cccccc;"><span class="glyphicon glyphicon-time"></span><div style="width: 10px;">已购历史</div><span id="buyHisNum" style="color: #f5620a">0</span></div></td>
		<td id="buyHisId" class="shoppingCart">
			<div id="getBuyHisList" style="height:0px;overflow-y: auto;"></div>
		</td>
	</tr>
</table>
<div id="maskLayerId" onclick="maskLayerClick()" style="width: 100%; height: 100%; border-top: white1pxgroove;padding-bottom: 5px;padding-top: 0px;background-color: buttonface;text-align: center;background-color: #7f7f7f;filter: alpha(opacity=1);left: 0px;position: absolute;top: 1px;z-index: 3;display: none;cursor: default"></div><!-- 遮罩层 -->
<script type="text/javascript">
$(top.hangge());
$('.date-picker').datepicker({autoclose: true,todayHighlight: true});//datepicker
$("#shoppingCartNum").html("${shoppingCartNum}");
$("#buyHisNum").html("${buyHisNum}");
</script>
</body>
</html>