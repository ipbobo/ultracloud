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
<script type="text/javascript">
getBarChart('storeChart', ['存储容量'], ['总量','申请中','已分配','剩余'], ['${cmpRes.storeTotalNum}', '${cmpRes.storeAppNum}', '${cmpRes.storeUseNum}', '${cmpRes.storeRestNum}'], ['#00E5EE', '#FF0000', '#CCCCCC','#00FF00'], "GB");//获取图表
</script>
</head>
<body>
<form id="mainForm" name="mainForm" action="bizview/storelist.do" enctype="multipart/form-data" method="post">
	<table style="margin-top: 0px;margin-left: 0px;margin-bottom: 10px;">
		<tr class="tablecls">
			<td align="left" style="width: 120px;padding-right: 10px;">
				<select class="chosen-select form-control" name="ccbizviewType" id="ccbizviewType" data-placeholder="请选择业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="bizviewTypeFunc('ccbizviewType')">
				<c:forEach items="${bizviewTypeList}" var="var">
					<option value="${var.dictCode}" <c:if test="${bizviewType==var.dictCode || (bizviewType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="left" style="width: 120px;">
				<select class="chosen-select form-control" name="ccsubBizviewType" id="ccsubBizviewType" data-placeholder="请选择子业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="subBizviewTypeFunc('ccbizviewType', 'ccsubBizviewType')">
				<option value="">全部${cmpRes.bizviewTypeName}</option>
				<c:forEach items="${subBizviewTypeList}" var="var">
					<option value="${var.dictCode}"<c:if test="${subBizviewType==var.dictCode}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
		</tr>
	</table>
</form>
<table style="width: 100%;border:1px solid #cccccc;margin-top: 0px;margin-left: 0px;margin-bottom: 10px;">
	<tr>
		<td align="left" valign="top" style="width: 300px;padding:10px;border-right:1px solid #cccccc;border-bottom:1px solid #cccccc;">
			<table style="width: 100%;">
				<tr>
					<td style="border-bottom:1px solid #cccccc;" colspan="2"><b>资源信息</b></td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;padding-top: 10px;">${cmpRes.bizviewTypeName}：</td>
					<td align="left" style="width: 180px;padding-top: 10px;">${cmpRes.subBizviewTypeName}</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">存储总量：</td>
					<td align="left" style="width: 180px;">${cmpRes.storeTotalNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">已分配：</td>
					<td align="left" style="width: 180px;">${cmpRes.storeUseNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">申请中：</td>
					<td align="left" style="width: 180px;">${cmpRes.storeAppNum}&nbsp;GB</td>
				</tr>
				<tr>
					<td align="right" style="width: 120px;">存储剩余：</td>
					<td align="left" style="width: 180px;">${cmpRes.storeRestNum}&nbsp;GB</td>
				</tr>
			</table>
		</td>
		<td align="left" valign="top" style="padding:10px;border-right:1px solid #cccccc;border-bottom:1px solid #cccccc;">
			<table style="width: 100%;">
				<tr>
					<td style="border-bottom:1px solid #cccccc;" colspan="2"><b>容量视图</b></td>
				</tr>
				<tr>
					<td align="center" style="width: 100%;padding-top: 10px;">
						<div class="col-xs-12">
							<div align="center" id="storeChart" style="width: 300px;height:200px;"></div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<iframe src="bizview/cloudhostlist.do?bizviewType=${bizviewType}&subBizviewType=${subBizviewType}" name="mainFrame" frameborder="0" marginheight="0" marginwidth="0" height="480px" width="100%"></iframe>
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
</script>
</body>
</html>