<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script src="js/tuoyunChart.js"></script>
<script type="text/javascript">
getTuoyunChart('cpuGaugeChart','CPU使用率',${(cmpRes.cpuTotalNum==0?0:cmpRes.cpuUseNum/cmpRes.cpuTotalNum)*100},'meterStyle1');
getTuoyunChart('memGaugeChart','内存使用率',${(cmpRes.memTotalNum==0?0:cmpRes.memUseNum/cmpRes.memTotalNum)*100},'meterStyle1');
/*getGaugeChart('cpuGaugeChart', ['', 'CPU使用率'], ['50%', '40%'], [0, 70], '${(cmpRes.cpuTotalNum==0?0:cmpRes.cpuUseNum/cmpRes.cpuTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('memGaugeChart', ['', '内存使用率'], ['50%', '40%'], [0, 70], '${(cmpRes.memTotalNum==0?0:cmpRes.memUseNum/cmpRes.memTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表*/
getBarChart('cpuChart', ['CPU容量'], ['总量','使用量'], ['${cmpRes.cpuTotalNum}', '${cmpRes.cpuUseNum}'], ['#f9b552','#009fe9'], "核");//获取图表
getBarChart('memChart', ['内存容量'], ['总量','使用量'], ['${cmpRes.memTotalNum}', '${cmpRes.memUseNum}'], ['#f9b552','#009fe9'], "GB");//获取图表
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
				<option value="">全部集群</option>
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
					<td align="right" style="width: 120px;padding-top: 10px;">数据中心：</td>
					<td align="left" style="width: 180px;padding-top: 10px;">${cmpRes.bizviewTypeName}</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">物理CPU总量：</td>
					<td align="left" style="width: 180px;">${cmpRes.cpuTotalNum}&nbsp;核</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">物理CPU使用量：</td>
					<td align="left" style="width: 180px;">${cmpRes.cpuUseNum}&nbsp;核</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">存储使用量：</td>
					<td align="left" style="width: 180px;">${cmpRes.storeUseNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">物理内存总量：</td>
					<td align="left" style="width: 180px;">${cmpRes.memTotalNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">物理内存使用量：</td>
					<td align="left" style="width: 180px;">${cmpRes.memUseNum}&nbsp;GB</td>
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
					<td align="center" style="width: 30%;padding-top: 10px;">
						<div class="col-xs-12">
							<div id="cpuGaugeChart" style="width: 70%;"></div>
							<div id="memGaugeChart" style="width: 70%;"></div>
						</div>
					</td>
					<td align="center" style="width: 70%;padding-top: 10px;">
						<div class="col-xs-12">
							<div id="cpuChart" style="width: 50%;height:250px;" class="col-xs-4 col-sm-4"></div>
							<div id="memChart" style="width: 50%;height:250px;" class="col-xs-4 col-sm-4"></div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<iframe src="resview/hostlist.do?operType=cal&bizviewType=${bizviewType}&subBizviewType=${subBizviewType}" name="mainFrame" frameborder="0" marginheight="0" marginwidth="0" height="480px" width="100%"></iframe>
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
</script>
</body>
</html>