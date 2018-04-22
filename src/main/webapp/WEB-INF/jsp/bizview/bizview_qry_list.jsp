<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../system/index/top.jsp"%>
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript" src="static/ace/js/ace/ace.js"></script><!-- ace scripts -->
<script type="text/javascript" src="static/ace/js/chosen.jquery.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<link rel="stylesheet" href="css/newSkin.css">
<script type="text/javascript">
//业务视图总览类型改变时触发
function bizviewTypeFunc(bizviewTypeId){
	var bizviewType=$("#"+bizviewTypeId).val();//业务视图总览类型
	loadData(bizviewType, "");//数据加载
}

//业务视图总览类型改变时触发
function subBizviewTypeFunc(bizviewTypeId, subBizviewTypeId){
	var bizviewType=$("#"+bizviewTypeId).val();//业务视图总览类型
	var subBizviewType=$("#"+subBizviewTypeId).val();//子业务视图总览类型
	loadData(bizviewType, subBizviewType);//数据加载
}

//业务视图总览类型改变时触发回调函数
/* function bizviewTypeCallback(data, bool){
	//var jsonObj={"bizType": $("#bizviewType").val()};//JSON请求数据：业务视图总览类型
	//sendHttpPost("getDataList.do", jsonObj, bizviewTypeCallback);//发送Http请求
	if(bool && data.retCode=="0"){
		$("#subBizviewType").empty();//清空子业务视图总览类型
		$("#subBizviewType").append("<option value=''>"+"全部"+$("#bizviewType").find("option:selected").text()+"</option>");
		$.each(data.dataList, function(indx, item) {
			$("#subBizviewType").append("<option value='"+item.dictCode+"'>"+item.dictValue+"</option>");
		});
		
		loadData();//数据加载
	}
} */

//计算数据加载
function loadJstabData(bizviewType, subBizviewType){
	$("#jstab").load("bizview/callist.do?bizviewType="+(bizviewType?bizviewType:"")+"&subBizviewType="+(subBizviewType?subBizviewType:""));
}

//存储数据加载
function loadCctabData(bizviewType, subBizviewType){
	$("#cctab").load("bizview/storelist.do?bizviewType="+(bizviewType?bizviewType:"")+"&subBizviewType="+(subBizviewType?subBizviewType:""));
}

//数据加载
function loadData(bizviewType, subBizviewType){
	if($("#jstab").is(".active")){//计算
		loadJstabData(bizviewType, subBizviewType);//计算数据加载
	}else{//存储
		loadCctabData(bizviewType, subBizviewType);//存储数据加载
	}
}

//点击tab页
var isCctabOpen=false;//存储Tab是否打开
function tabFunc(tabId){
	if(tabId=="cctab" && !isCctabOpen){//存储
		var bizviewType=$("#ccbizviewType").val();//业务视图总览类型
		var subBizviewType=$("#ccsubBizviewType").val();//子业务视图总览类型
		loadCctabData(bizviewType, subBizviewType);//存储数据加载
		isCctabOpen=true;
	}
}
</script>
</head>
<body onload="loadData()" class="new-page-list">
<ul class="nav nav-tabs">
	<li class="active"><a href="#jstab" onclick="tabFunc('jstab')" data-toggle="tab" style="border-left: 0px;">计算</a></li>
	<li><a href="#cctab" onclick="tabFunc('cctab')" data-toggle="tab">存储</a></li>
</ul>
<div class="tab-content" style="border: 0px;">
<div id="jstab" class="tab-pane fade in active"></div>
<div id="cctab" class="tab-pane fade"></div>
</div>
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
</script>
</body>
</html>