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
<link rel="stylesheet" href="css/newSkin.css">
<script type="text/javascript">
</script>
</head>
<body class="new-page-list">
<form id="mainForm" name="mainForm" action="bizview/cloudhostlist.do" enctype="multipart/form-data" method="post">
<input type="hidden" name="operType" value="${pd.operType}">
<input type="hidden" name="bizviewType" value="${pd.bizviewType}">
<input type="hidden" name="subBizviewType" value="${pd.subBizviewType}">
<div class="widget-box widget-header" style="margin:0px"><h4 class="smaller">云主机列表&nbsp;&nbsp;&nbsp;<font color="#ff0000" size="2">*此列表不包括从资源池同步过来,但未绑定的虚拟机</font></h4></div>
<table id="simple-table" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th class="center" style="width: 50px;">序号</th>
			<th class="center">云主机名称</th>
			<th class="center">IP</th>
			<th class="center">项目</th>
			<th class="center">环境</th>
			<c:if test="${pd.operType=='cal'}">
				<th class="center">CPU</th>
				<th class="center">内存</th>
			</c:if>
			<c:if test="${pd.operType=='store'}">
				<th class="center">数据盘</th>
				<th class="center">快照</th>
			</c:if>
			<th class="center" style="width: 140px;">创建时间</th>
		</tr>
	</thead>
	<c:choose>
	<c:when test="${not empty cloudHostList}">
	<c:forEach items="${cloudHostList}" var="var" varStatus="vs">
		<tr>
			<td class='center' style="width: 30px;">${vs.index+1+page.currentResult}</td>
			<td class='center'>${var.name}</td>
			<td class='center'>${var.ip}</td>
			<td class='center'>${var.projectCodeName}</td>
			<td class='center'>${var.envCodeName}</td>
			<c:if test="${pd.operType=='cal'}">
				<td class='center'>${var.cpu}&nbsp;核</td>
				<td class='center'>${var.memory}&nbsp;GB</td>
			</c:if>
			<c:if test="${pd.operType=='store'}">
				<td class='center'>${var.mount_disk_size}&nbsp;核</td>
				<td class='center'>${var.snapshoot}&nbsp;GB</td>
			</c:if>
			<td class='center'>${var.gmt_create}</td>
		</tr>
	</c:forEach>
	</c:when>
	<c:otherwise>
		<tr class="main_info">
			<td colspan="100" class="center">没有相关数据</td>
		</tr>
	</c:otherwise>
	</c:choose>
</table>
<table style="width: 100%;"><tr><td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;margin-bottom: 0px;">${page.pageStr}</div></td></tr></table>
</form>
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
</script>
</body>
</html>