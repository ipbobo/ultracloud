<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="/t-tags"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/showdialog.jsp"/>
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
	var fieldValues=fieldValue.split(",");
	var cpuCode=fieldValues[0];
	var memoryCode=fieldValues[1];
	setFieldValue(document.getElementById("cpuId"+cpuCode), 'cpu', cpuCode);
	setFieldValue(document.getElementById("memoryId"+memoryCode), 'memory', memoryCode);
	specFunc(cpuCode, memoryCode);//实例规格改变时触发
}

//设置软件参数
function setSoftParam(indx){
	var obj=$("#softCode"+(indx==0?"":indx));
	if(obj.val()==""){
		obj.tips({side:3, msg:'请选择软件安装', bg:'#AE81FF', time:2});
		obj.focus();
		return false;
	}
	
	var diag = new top.Dialog();
	diag.Drag=true;
	diag.Title ="设置参数";
	diag.URL = '<%=basePath%>/getParamList.do?softCode='+obj.val();
	diag.Width = 350;
	diag.Height = 400;
	diag.CancelEvent=function(){diag.close();};//关闭事件
	diag.OKEvent=function(){//OK事件
		var softParamStr=diag.innerFrame.contentWindow.getSoftParam();//获取软件参数
		$("#softParam"+(indx==0?"":indx)).val(softParamStr);
		diag.close();
	};
	diag.show();
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
	
	$("#expireDateLabel").html($("#expireDate").val());
	getTotalAmt();//计算金额
}

//新增磁盘行
function addDiskRow(){
    var len = $("#diskTableId tr").length;
    var diskNum=$("#diskNum").val();
    if(len>=diskNum*1){
    	$("#diskTableId").tips({side:3, msg:'只能选配'+diskNum+'块磁盘', bg:'#AE81FF', time:2});
    	return;
    }
    
    var tdStr="<td align=\"left\" style=\"width: 120px;padding-right:10px;padding-top:10px;\"><select class=\"chosen-select form-control\" name=\"diskType\" id=\"diskType"+(len+1)+"\" data-placeholder=\"请选择磁盘类型\" style=\"vertical-align:top;width: 120px;\" onchange=\"diskTypeFunc()\">"+$("#diskType").html()+"</select></td>"
	    +"<td align=\"left\" style=\"padding-top:10px;\"><input type=\"text\" name=\"diskSize\" id=\"diskSize\" value=\"20\" style=\"width: 120px;\" maxlength=\"5\" onblur=\"diskSizeFunc(this, 'diskType', 'iopsId"+(len+1)+"')\"/></td>"
	    +"<td align=\"left\" style=\"padding-top:10px;\">GB</td>"
	    +"<td align=\"right\" style=\"padding-top:10px;\"><span style=\"display:none\"><span id=\"iopsId"+(len+1)+"\">1120</span>&nbsp;IOPS&nbsp;<input name=\"diskEncrypt\" type=\"checkbox\" value=\"\"/>加密</span>&nbsp;<a href=\"javascript:void()\" onclick=\"delRow('diskTrId"+(len+1)+"')\"><span class=\"glyphicon glyphicon-remove\"></span></a></td>";
    $("#diskTableId").append("<tr id=\"diskTrId"+(len+1)+"\">"+tdStr+"</tr>");
}

//新增软件安装行
function addSoftRow(){
    var len = $("#softTableId tr").length;
    var softNum=$("#softNum").val();
    if(len>=softNum*1){
    	$("#softTableId").tips({side:3, msg:'只能选择'+softNum+'个安装软件', bg:'#AE81FF', time:2});
    	return;
    }
    
    var tdStr="<td align=\"left\" style=\"width: 120px;padding-right:10px;padding-top:10px;\"><select class=\"chosen-select form-control\" name=\"softCode\" id=\"softCode"+(len+1)+"\" data-placeholder=\"请选择软件名称\" style=\"vertical-align:top;width: 120px;\">"+$("#softCode").html()+"</select></td>"
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
			showAlert("请选择套餐");
		}
		
		return false;
	}
	
	/* if($("#tcvirName").val()==""){
		$("#tcvirName").tips({side:3, msg:'虚拟机名称不能为空', bg:'#AE81FF', time:2});
		$("#tcvirName").focus();
		return false;
	} */
	
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
	
	var virName=$("#virName").val();
	/*if(virName==""){
		$("#virName").tips({side:3, msg:'虚拟机名称不能为空', bg:'#AE81FF', time:2});
		$("#virName").focus();
		return false;
	}*/
	
	if(virName!="" && virName.match(/^['"]*$/)){
		$("#virName").tips({side:3, msg:'虚拟机名称不能包含“\'"”', bg:'#AE81FF', time:2});
		$("#virName").focus();
		return false;
	}
	
	var osType=$("#osType").val();
	if(osType==""){
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
	
	/*if($("#imgUserName").val()==""){
		$("#imgUserName").tips({side:3, msg:'用户名不能为空', bg:'#AE81FF', time:2});
		$("#imgUserName").focus();
		return false;
	}
	
	if($("#imgUserPass").val()==""){
		$("#imgUserPass").tips({side:3, msg:'密码不能为空', bg:'#AE81FF', time:2});
		$("#imgUserPass").focus();
		return false;
	}
	
	if((osType=="redhat" || osType=="centos") && $("#imgPath").val()==""){
		$("#imgPath").tips({side:3, msg:'路径不能为空', bg:'#AE81FF', time:2});
		$("#imgPath").focus();
		return false;
	}*/
	
	/* if(!mutiCheck('diskType', '请选择存储')){
		return false;
	} */
	
	/* if(!mutiCheck('softCode', '请选择软件安装')){
		return false;
	} */
	
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
	$("#softParamStr").val(softParamArr.join('|'));
	return true;
}

//多个校验
function mutiCheck(name, msg){
	var bool=true;
	$("select[name='"+name+"']").each(function() {
		if($(this).val()==""){
			$(this).tips({side:3, msg:msg, bg:'#AE81FF', time:2});
			$(this).focus();
			bool=false;
			return false;
		}
	});
	
	return bool;
}

//加入清单
function addList(){
	var jsonObj={};//JSON请求数据
	if($("#tcsq").is(".active")){//套餐数据校验
		if(checkPckgData()){
			jsonObj.tcareaCode=$("#tcareaCode").val();//地域代码
			jsonObj.tcenvCode=$("#tcenvCode").val();//环境代码                       
			jsonObj.tcplatType=$("#tcplatType").val();//平台类型                       
			jsonObj.tcdeployType=$("#tcdeployType").val();//部署类型                     
			jsonObj.tcvirName=$("#tcvirName").val();//虚拟机名称
			jsonObj.pckgId=$("#pckgId").val();//套餐ID     
			ajaxHttpPost("addPckgList.do", jsonObj, "addListBtnId", "shoppingCartNum");//发送Ajax请求
		}
	}else if(checkData("addListBtnId")){//数据校验
		jsonObj.areaCode=$("#areaCode").val();//地域代码                       
		jsonObj.envCode=$("#envCode").val();//环境代码                        
		jsonObj.platType=$("#platType").val();//平台类型                       
		jsonObj.deployType=$("#deployType").val();//部署类型                     
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
		jsonObj.uploadFileName=$("#uploadFileName").val();//文件名
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
	//jsonObj.envCode=$("#envCode").val();//环境代码                        
	//jsonObj.platType=$("#platType").val();//平台类型
	//jsonObj.deployType=$("#deployType").val();//部署类型
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
	jsonObj.uploadFileName=$("#uploadFileName").val();//文件名
	jsonObj.pckgName=$("#pckgName").val();//套餐名称                       
	jsonObj.status=$("#status").val();//状态：0-待提交；1-已提交；T-套餐
	ajaxHttpPost("savePckg.do", jsonObj, "savePckgBtnId");//发送Ajax请求
}

//磁盘大小失焦触发
function diskSizeFunc(obj, diskTypeId, iopsId){
	var diskSize=$(obj).val();
	if($(obj).val()==""){
		$(obj).val("20");
		return;
	}
	
	if(!diskSize.match(/^\d*$/) || diskSize<1){
		$(obj).val("20");
		diskSize=20;
	}
	
	var diskMaxNum=$("#diskMaxNum").val();
	if(diskMaxNum==''){
		diskMaxNum='32768';
	}
	
	if(diskSize>diskMaxNum*1){
		$(obj).val(diskMaxNum);
		diskSize=diskMaxNum*1;
	}
	
	if($("#"+diskTypeId).val()=="1"){//高效云盘1120+6
		$("#"+iopsId).html(1120+(diskSize-1)*6);
	}else if($("#"+diskTypeId).val()=="2"){//SSD云盘1800+30
		$("#"+iopsId).html(1800+(diskSize-1)*30);
	}
	
	diskTypeFunc();//数据盘改变时触发
}

//点击tab页
function tabFunc(tabId){
	if(tabId=="zdysq"){//自定义申请
		getTotalAmt();//计算金额
		$("#savePckgBtnId").show();
		return;
	}else{//套餐申请
		$("#savePckgBtnId").hide();
		$("#totalAmt").html("￥0.00");//总价
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
	var osType=$("#osType").val();
	if(osType!=''){
		osTypeName=$("#osType").find("option:selected").text();
	}
	
	var osBitNumName="";
	var osBitNum=$("#osBitNum").val();
	if(osBitNum!=''){
		osBitNumName=$("#osBitNum").find("option:selected").text();
	}
	
	getImgList('', osType, osBitNum);
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
	getTotalAmt();//计算金额
}

//计算金额
var cpuPrice=('${cmpPrice.cpuPrice}')*1;//cpu单价
var memPrice=('${cmpPrice.memPrice}')*1;//内存单价
var storePrice=('${cmpPrice.storePrice}')*1;//磁盘单价
function getTotalAmt(virNum){
	var dayNum=getDateDiff(getCurrDate(), $("#expireDate").val());
	var virNum=dayNum*(virNum?virNum:$("#virNum").val());//数量
	var cpuNum=$("#cpu").val();//cpu数量
	var memNum=$("#memory").val();//内存数量
	var storeNum=0;//磁盘数量
	$("input[name='diskSize']").each(function() {
		storeNum+=($(this).val())*1;//磁盘数量
	});
	
	$("#totalAmt").html(amtFmt((virNum*(cpuNum*cpuPrice+memNum*memPrice+storeNum*storePrice)).toFixed(2)+"", '￥'));//总价
}

//套餐计算金额
function getPckgTotalAmt(cpuNum, memNum, storeVal, virNum){
	var storeVals=storeVal.split(",");
	var storeNum=0;//磁盘数量
	$.each(storeVals, function (i, item) {
		storeNum+=item*1;//磁盘数量
	});
	
	$("#totalAmt").html(amtFmt((virNum*(cpuNum*cpuPrice+memNum*memPrice+storeNum*storePrice)).toFixed(2)+"", '￥'));//总价
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
	getTotalAmt(virNum);//计算金额
}

//上传附件：txt,word,excel,image
function uploadFileFunc() {
	var uploadFile = $('#uploadFile').val();
	if (uploadFile==null || uploadFile=='') {
		$("#uploadFile").tips({side:3, msg:'请选择文件', bg:'#AE81FF', time:2});
		return;
	}

	if (!/\.(txt|TXT|doc|docx|DOC|DOCX|xls|xlsx|XLS|XLSX|gif|jpg|jpeg|png|bmp|GIF|JPG|JPEG|PNG|BMP)$/.test(uploadFile)) {
		$("#uploadFile").tips({side:3, msg:'待上传的文件必须是txt,word,excel,image中的一种', bg:'#AE81FF', time:2});
		return false;
	}
	
	$.ajax({
		url : 'uploadFile.do',
		type : 'POST',
		data : new FormData($("#mainForm")[0]),
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			$("#uploadFileName").val(data);
			$("#uploadFile").tips({side:3, msg:"文件上传成功["+data+"]", bg:'#AE81FF', time:2});
		},
		error : function(data) {
			$("#uploadFile").tips({side:3, msg:"文件上传失败["+data+"]", bg:'#AE81FF', time:2});
		}
	});
}

//环境代码列表查询
function getEnvCodeList(areaCodeId){
	if(areaCodeId==''){
		areaCodeId=$("#areaCode").val();
	}
	
	$.ajax({
	    type: 'post',  
	    url: 'getEnvCodeList.do?areaCodeId='+areaCodeId,
	    dataType: 'json',
	    success: function(data){
	    	$("#envCodeId").empty();//清空环境代码列表
		    if(data.retCode=="0"){//删除成功
		    	$.each(data.dataList, function (i, item) {
				    $("#envCodeId").append("<li onclick=\"setFieldValue(this, 'envCode', '"+item.dictCode+"');getPlatTypeList('', '"+item.dictCode+"');checkNum('"+item.diskNum+"', '"+item.diskMaxNum+"', '"+item.softNum+"');\" class='"+(item.dictDefault=='1'?"active":"")+"'>"+item.dictValue+"</li>");
			    });
			    
			    $("#envCode").val(data.defaultEnvCode);//设置默认值
			    getPlatTypeList('', data.defaultEnvCode);
			    checkNum(data.defaultDiskNum, data.defaultDiskMaxNum, data.defaultSoftNum);//数量校验
		    }
	    },
	    error: function(data) {}
	});
}

//平台类型列表查询
function getPlatTypeList(areaCodeId, envCodeId){
	if(areaCodeId==''){
		areaCodeId=$("#areaCode").val();
	}
	
	if(envCodeId==''){
		envCodeId=$("#envCode").val();
	}
	
	$.ajax({
	    type: 'post',  
	    url: 'getPlatTypeList.do?areaCodeId='+areaCodeId+'&envCodeId='+envCodeId,
	    dataType: 'json',
	    success: function(data){
	    	$("#platTypeId").empty();//清空平台类型列表
		    if(data.retCode=="0"){//删除成功
		    	$.each(data.dataList, function (i, item) {
				    $("#platTypeId").append("<li onclick=\"setFieldValue(this, 'platType', '"+item.dictCode+"');getImgList('"+item.dictCode+"', '', '');\" class='"+(item.dictDefault=='1'?"active":"")+"'>"+item.dictValue+"</li>");
			    });
			    
			    $("#platType").val(data.defaultPlatType);//设置默认值
			    getImgList(data.defaultPlatType, '', '');
		    }
	    },
	    error: function(data) {}
	});
}

//模板列表查询
function getImgList(platTypeId, osType, osBitNum){
	if(platTypeId==''){
		platTypeId=$("#platType").val();
	}
	
	if(osType==''){
		osType=$("#osType").val();
	}
	
	if(osBitNum==''){
		osBitNum=$("#osBitNum").val();
	}
	
	$.ajax({
	    type: 'post',  
	    url: 'getImgList.do?platTypeId='+platTypeId+'&osType='+osType+'&osBitNum='+osBitNum,
	    dataType: 'json',
	    success: function(data){
	    	$("#imgCode").empty();//清空模板列表
		    if(data.retCode=="0"){//删除成功
		    	$("#imgCode").append("<option value=''>请选择</option>");
		    	$.each(data.dataList, function (i, item) {
				    $("#imgCode").append("<option value='"+item.dictCode+"'>"+item.dictValue+"</option>");
			    });
		    }
	    },
	    error: function(data) {}
	});
}

//数量校验
function checkNum(diskNum, diskMaxNum, softNum){
	$("#diskNum").val(diskNum);
	$("#diskMaxNum").val(diskMaxNum);
	$("#softNum").val(softNum);
	$("#diskNumId").html(diskNum);
	$("#softNumId").html(softNum);
	$.each($("#diskTableId tr"), function (i, item) {if(i!=0){delRow($(this).attr("id"));}else{diskSizeFunc(document.getElementById("diskSize"), 'diskType', 'iopsId');}});
	$.each($("#softTableId tr"), function (i, item) {if(i!=0){delRow($(this).attr("id"));}});
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
	<link rel="stylesheet" href="css/newSkin.css">
</head>
<body class="resapp-qry-input">
<div class="head-nav">主页 > 资源申请</div>
<ul class="nav nav-tabs">
	<li class="active"><a href="#zdysq" onclick="tabFunc('zdysq')" data-toggle="tab">自定义申请</a></li>
	<li><a href="#tcsq" onclick="tabFunc('tcsq')" data-toggle="tab">套餐申请</a></li>
</ul>
<div class="tab-content">
<div id="zdysq" class="tab-pane fade in active">
	<form id="mainForm" name="mainForm" action="" enctype="multipart/form-data" method="post">
	<input type="hidden" name="areaCode" id="areaCode" value="${defaultAreaCode}"/>
	<input type="hidden" name="envCode" id="envCode" value="${defaultEnvCode}"/>
	<input type="hidden" name="diskNum" id="diskNum" value="${defaultDiskNum}"/>
	<input type="hidden" name="diskMaxNum" id="diskMaxNum" value="${defaultDiskMaxNum}"/>
	<input type="hidden" name="softNum" id="softNum" value="${defaultSoftNum}"/>
	<input type="hidden" name="platType" id="platType" value="${defaultPlatType}"/>
	<input type="hidden" name="deployType" id="deployType" value="1"/>
	<input type="hidden" name="resType" id="resType" value="1"/>
	<input type="hidden" name="cpu" id="cpu" value="1"/>
	<input type="hidden" name="memory" id="memory" value="1"/>
	<input type="hidden" name="diskTypeStr" id="diskTypeStr" value=""/><!-- 磁盘类型字符串 -->
	<input type="hidden" name="diskSizeStr" id="diskSizeStr" value=""/><!-- 磁盘大小字符串 -->
	<input type="hidden" name="diskEncryptStr" id="diskEncryptStr" value=""/><!-- 磁盘加密字符串 -->
	<input type="hidden" name="softCodeStr" id="softCodeStr" value=""/><!-- 软件代码字符串 -->
	<input type="hidden" name="softParamStr" id="softParamStr" value=""/><!-- 软件参数字符串 -->
	<input type="hidden" name="uploadFileName" id="uploadFileName" value=""><!-- 文件名 -->
	<input type="hidden" name="status" id="status" value="0"/><!-- 状态：0-待提交；1-已提交；T-套餐 -->
	<input type="hidden" name="pckgName" id="pckgName" value=""/><!-- 套餐名称 -->
	<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
		<tr class="tablecls">
			<td align="left" style="width: 90px;padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;地域&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="right" style="width: 120px;padding:10px;">&nbsp;</td>
			<td align="left" style="padding:10px;height:56px;" colspan="6">
				<ul id="areaCodeId" class="ullitab list-inline">
					<c:if test="${not empty areaCodeList}">
					<c:forEach items="${areaCodeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'areaCode', '${var.dictCode}');getEnvCodeList('${var.dictCode}');getPlatTypeList('${var.dictCode}', '');" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle" rowspan="2"><span class="glyphicon glyphicon-cog"></span>&nbsp;项目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="right" style="width: 120px;padding:10px;">环境：</td>
			<td align="left" style="padding:10px;height:56px;" colspan="6">
				<ul id="envCodeId" class="ullitab list-inline">
					<c:if test="${not empty envCodeList}">
					<c:forEach items="${envCodeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'envCode', '${var.dictCode}');getPlatTypeList('', '${var.dictCode}');checkNum('${var.diskNum}', '${var.diskMaxNum}', '${var.softNum}');" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
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
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;平台类型</td>
			<td align="right" style="width: 120px;padding:10px;"></td>
			<td align="left" style="padding:10px;height:56px;" colspan="6">
				<ul id="platTypeId" class="ullitab list-inline">
					<c:if test="${not empty platTypeList}">
					<c:forEach items="${platTypeList}" var="var" varStatus="st">
					<li onclick="setFieldValue(this, 'platType', '${var.dictCode}');getImgList('${var.dictCode}', '', '');" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
					</c:forEach>
					</c:if>
				</ul>
			</td>
		</tr>
		<%-- <tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;部署类型</td>
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
		</tr> --%>
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle" rowspan="5"><span class="glyphicon glyphicon-cog"></span>&nbsp;基本配置</td>
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
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle" rowspan="2"><span class="glyphicon glyphicon-cog"></span>&nbsp;镜像&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
				Linux必填
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;存储&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
							<input type="text" name="diskSize" id="diskSize" value="20" style="width: 120px;" maxlength="5" onblur="diskSizeFunc(this, 'diskType', 'iopsId')"/>
						</td>
						<td align="left" style="width: 20px;">GB</td>
						<td align="right" style="padding-right:13px;">
						  	<span style="display:none"><span id="iopsId">1120</span>&nbsp;IOPS&nbsp;<input name="diskEncrypt" type="checkbox" value=""/>加密</span>&nbsp;
						</td>
						<td align="left" valign="bottom" style="width: 200px;padding-bottom:8px;" rowspan="15">
							&nbsp;<a href="javascript:void()" onclick="addDiskRow()"><span class="glyphicon glyphicon-plus"></span></a>增加磁盘，您可选配<span id="diskNumId">${defaultDiskNum}</span>块
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;软件安装</td>
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
							&nbsp;<a href="javascript:void()" onclick="addSoftRow()"><span class="glyphicon glyphicon-plus"></span></a>增加安装软件，您可选择<span id="softNumId">${defaultSoftNum}</span>个
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;数量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="right" style="width: 120px;padding:10px;"></td>
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
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;到期时间</td>
			<td align="right" style="width: 120px;padding:10px;"></td>
			<td style="padding:10px;" colspan="6">
				<input type="text" name="expireDate" id="expireDate" value="" class="span10 date-picker" onchange="checkExpireDate(false)" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:120px;" placeholder="到期时间"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="expireDateChk" value="" onclick="checkExpireDate(true)"/>永久
			</td>
		</tr>
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle"><span class="glyphicon glyphicon-cog"></span>&nbsp;上传附件</td>
			<td align="right" style="width: 120px;padding:10px;">&nbsp;</td>
			<td style="padding:10px;"><input style="background-color:#cccccc;" type="file" name="uploadFile" id="uploadFile" value="选择文件" accept=".txt,.doc,.docx,.xls,.xlsx,image/*"/></td>
			<td colspan="5"><input type="button" value="上传" onclick="uploadFileFunc()">&nbsp;格式要求：txt,word,excel,image</td>
		</tr>
		<tr><td colspan="8" height="10px"></td></tr>
		<tr class="tablecls">
			<td align="left" style="padding-left:10px;background-color:#cccccc;" class="first-td" valign="middle" rowspan="2"><span class="glyphicon glyphicon-cog"></span>&nbsp;当前配置</td>
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
				配置费用(元)：<span id="totalAmt" style="font-size:26px;color: #f5620a;">￥0.00</span>
			    <span id="addListBtnId" class="btncls active" style="background-color:#f5620a;"><a id="addList" href="javascript:void()" onclick="addList()">加入清单</a></span>
			    <span style="width:30px;float:right;">&nbsp;</span>
			    <span id="savePckgBtnId" class="btncls"><a href="javascript:void()" onclick="savePckgPre()">保存为套餐</a></span>
			</div>
		</td>
	</tr>
</table>
<table style="width:100%;border-top:1px solid #f5f5f5;"><tr><td style="padding:10px;"><div class="divbtn"></div></td></tr></table>
<!-- 购物车 -->
<table id="shoppingCartTable" style="position: fixed; right: 0; top: 130px; z-index: 99;">
	<tr>
		<td onclick="maskLayerClick('shoppingCart')" align="center"><div style="cursor:pointer; float: left;width: 50px;padding:10px;background-color:#cccccc;"><span class="glyphicon glyphicon-shopping-cart"></span><div style="width: 10px;">购买清单</div><span id="shoppingCartNum" style="color: #f5620a;">0</span></div></td>
		<td id="shoppingCartId" class="shoppingCart">
			<div id="getShoppingCartList" style="height:0px;overflow-y: auto;"></div>
			<div id="batchBuy" class="divbtn" style="display:none;width:100%;height:50px;padding:10px;border-top:1px solid #f5f5f5;">
				共计(元)：<span id="allTotalAmt" style="font-size:26px;color: #f5620a;">￥0.00</span>
				<span class="btncls active" style="width:80px;background-color:#f5620a;"><a href="javascript:void()" onclick="batchBuy()">批量购买</a></span>
				<span style="width:10px;float:right;">&nbsp;</span>
			    <span class="btncls" style="width:80px;"><a href="javascript:void()" onclick="clearShoppingCart()">清空购物车</a></span>
			</div>
		</td>
	</tr>
</table>
<!-- 购买历史 -->
<table id="buyHisTable" style="position: fixed; right: 0; top: 280px; z-index: 99;">
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
getTotalAmt();//计算金额，初始化加载
</script>
</body>
</html>