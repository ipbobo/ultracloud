<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="top.jsp"%>
<script type="text/javascript" src="static/ace/js/ace/ace.js"></script><!-- ace scripts -->
<script type="text/javascript" src="static/ace/js/chosen.jquery.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<script type="text/javascript" src="plugins/echarts/echarts.min.js"></script><!-- 百度echarts -->
<script type="text/javascript">
</script>
</head>
<body>
<div class="main-container">
	<div class="page-content">
		<div style="height:5px;"></div>
		<div style="padding: 0px;" class="alert alert-block alert-success">
			<table style="width:100%;height:35px;padding:0px;">
				<tr>
					<td rowspan="2"><img src="static/login/logo.png" alt="Logo" width="80px" height="35px"/>拓云管理平台</td>
					<td align="center" width="80px">虚机总量</td>
					<td align="center" width="95px">宿主机总量</td>
					<td align="center" width="95px">物理机总量</td>
					<td align="center" width="80px">用户总数</td>
					<td align="center" width="80px">项目总数</td>
					<td align="center" width="80px">工单总数</td>
					<td align="center" width="20px" rowspan="2"><button type="button" class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button></td>
				</tr>
				<tr>
					<td align="center">${virNum}</td>
					<td align="center">${hostNum}</td>
					<td align="center">${physNum}</td>
					<td align="center">${userNum}</td>
					<td align="center">${projNum}</td>
					<td align="center">${workOrderNum}</td>
				</tr>
			</table>
		</div>
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
				<td align="left" valign="top" style="padding:10px;border-right:1px solid #cccccc;border-bottom:1px solid #cccccc;">
					<table style="width: 100%;">
						<tr>
							<td style="border-bottom:1px solid #cccccc;" colspan="2"><b>容量视图</b></td>
						</tr>
						<tr>
							<td align="left" style="width: 100%;padding-top: 10px;">
								<div class="col-xs-12">
									<div id="cpuChart" style="width: 300px;height:200px;" class="col-xs-4 col-sm-4"></div>
									<div id="memChart" style="width: 300px;height:200px;" class="col-xs-4 col-sm-4"></div>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"><i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a><!-- 返回顶部 -->
<%@ include file="foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
getBarChart('cpuChart', ['CPU容量'], ['总量','申请中','已分配','剩余'], ['1', '2', '11', '33'], ['#00E5EE', '#FF0000', '#CCCCCC','#00FF00'], "核");//获取图表
getBarChart('memChart', ['内存容量'], ['总量','申请中','已分配','剩余'], ['2', '1', '22', '221'], ['#00E5EE', '#FF0000', '#CCCCCC','#00FF00'], "GB");//获取图表
</script>
</body>
</html>