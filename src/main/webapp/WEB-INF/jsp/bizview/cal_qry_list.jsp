﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript" src="plugins/echarts/echarts.min.js"></script><!-- 百度echarts -->
<link rel="stylesheet" href="css/newSkin.css">
<script type="text/javascript">
getBarChart('cpuChart', ['CPU容量'], ['总量','申请中','已分配','剩余'], ['${cmpRes.cpuTotalNum}', '${cmpRes.cpuAppNum}', '${cmpRes.cpuUseNum}', '${cmpRes.cpuRestNum}'], ['#f9b552', '#009fe9', '#5f52a1','#df2360'], "核");//获取图表
getBarChart('memChart', ['内存容量'], ['总量','申请中','已分配','剩余'], ['${cmpRes.memTotalNum}', '${cmpRes.memAppNum}', '${cmpRes.memUseNum}', '${cmpRes.memRestNum}'], ['#f9b552', '#009fe9', '#5f52a1','#df2360'], "GB");//获取图表
</script>
</head>
<body class="new-page-list">
<form id="mainForm" name="mainForm" action="" enctype="multipart/form-data" method="post">
	<table style="margin-top: 0px;margin-left: 0px;margin-bottom: 10px;">
		<tr class="tablecls">
			<td align="left" style="width: 120px;padding-right: 10px;">
				<select class="chosen-select form-control new-ctl-style-select color-gray" name="bizviewType" id="bizviewType" data-placeholder="请选择业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="bizviewTypeFunc('bizviewType')">
				<c:forEach items="${bizviewTypeList}" var="var">
					<option value="${var.dictCode}" <c:if test="${bizviewType==var.dictCode || (bizviewType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="left" style="width: 120px;">
				<select class="chosen-select form-control new-ctl-style-select color-gray" name="subBizviewType" id="subBizviewType" data-placeholder="请选择子业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="subBizviewTypeFunc('bizviewType', 'subBizviewType')">
				<option value="">全部${cmpRes.bizviewTypeName}</option>
				<c:forEach items="${subBizviewTypeList}" var="var">
					<option value="${var.dictCode}"<c:if test="${subBizviewType==var.dictCode}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
		</tr>
	</table>
</form>
<table style="width: 100%;margin-top: 0px;margin-left: 0px;margin-bottom: 10px;">
	<tbody><tr>
		<td align="left" valign="top" style="width: 300px;padding:10px;border:1px solid #eeeeee; background: #ffffff">
			<table style="width: 100%;" class="in-table-list">
				<tbody><tr>
					<th style="border-bottom:1px solid #eeeeee;" colspan="2"><b>资源信息</b></th>
				</tr>
				<tr>
					<td align="right" style="width: 120px;padding-top: 10px;">${cmpRes.bizviewTypeName}：</td>
					<td align="left" style="width: 180px;padding-top: 10px;">${cmpRes.subBizviewTypeName}</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">CPU总量：</td>
					<td align="left" style="width: 180px;">${cmpRes.cpuTotalNum}&nbsp;核</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">已分配：</td>
					<td align="left" style="width: 180px;">${cmpRes.cpuUseNum}&nbsp;核</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">申请中：</td>
					<td align="left" style="width: 180px;">${cmpRes.cpuAppNum}&nbsp;核</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">CPU剩余：</td>
					<td align="left" style="width: 180px;">${cmpRes.cpuRestNum}&nbsp;核</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">内存总量：</td>
					<td align="left" style="width: 180px;">${cmpRes.memTotalNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">已分配：</td>
					<td align="left" style="width: 180px;">${cmpRes.memUseNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">申请中：</td>
					<td align="left" style="width: 180px;">${cmpRes.memAppNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">内存剩余：</td>
					<td align="left" style="width: 180px;">${cmpRes.memRestNum}&nbsp;GB</td>
				</tr>
			</table>
		</td>
		<td width="20px"></td>
		<td align="left" valign="top" style="padding:10px;border:1px solid #eeeeee; background: #ffffff">
			<table style="width: 100%;" class="in-table-list">
				<tbody><tr>
					<th style="border-bottom:1px solid #eeeeee;" colspan="2"><b>容量视图</b></th>
				</tr>
				<tr>
					<td align="left" style="width: 100%;padding-top: 10px;">
						<div class="col-xs-12">
							<div id="cpuChart" style="width: 400px;height:250px;" class="col-xs-4 col-sm-4"></div>
							<div id="memChart" style="width: 400px;height:250px;" class="col-xs-4 col-sm-4"></div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<iframe src="bizview/cloudhostlist.do?operType=cal&bizviewType=${bizviewType}&subBizviewType=${subBizviewType}" name="mainFrame" frameborder="0" marginheight="0" marginwidth="0" height="480px" width="100%"></iframe>
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
</script>
</body>
</html>