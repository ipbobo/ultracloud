<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="top.jsp"%>
<link rel="stylesheet" href="css/style.css"/>
<script type="text/javascript" src="static/ace/js/ace/ace.js"></script><!-- ace scripts -->
<script type="text/javascript" src="static/ace/js/chosen.jquery.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<script type="text/javascript" src="plugins/echarts/echarts.min.js"></script><!-- 百度echarts -->
<script type="text/javascript">
clearInterval(timeTicket);
timeTicket = setInterval(function (){
    option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
    myChart.setOption(option, true);
},2000)
</script>
</head>
<body>
<div class="main-container">
	<div class="page-content">
		<div style="height:5px;"></div>
		<div style="padding: 0px;margin: 0px" class="alert alert-block alert-success">
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
		<table style="width: 100%;" border=1>
			<tr>
				<td align="left" valign="top"><div align="center" style="width: 50px;border:1px solid #000000;"><h4>虚拟</h4></div></td>
				<td id="virCpuChart" align="center" width="200px" height="200px"></td>
				<td id="virMemChart" align="center" width="200px" height="200px"></td>
				<td id="virStoreChart" align="center" width="200px" height="200px"></td>
				<td align="left" valign="top"><div align="center" style="width: 50px;border:1px solid #000000;"><h4>物理</h4></div></td>
				<td style="padding:10px;" align="center" width="200px" height="200px">
					<div class="chart-progress">
						<div class="chart-progress-bar chart-progress-bar-info" role="progressbar" aria-valuenow="${physCmpRes.cpuUseNum}" aria-valuemin="0" aria-valuemax="${physCmpRes.cpuTotalNum}" style="width: ${(physCmpRes.cpuUseNum/physCmpRes.cpuTotalNum)*100}%;">${physCmpRes.cpuUseNum}/${physCmpRes.cpuTotalNum}&nbsp;核</div>
					</div>
					<h5>CPU</h5>
				</td>
				<td style="padding:10px;" align="center" width="200px" height="200px">
					<div class="chart-progress">
						<div class="chart-progress-bar chart-progress-bar-info" role="progressbar" aria-valuenow="${physCmpRes.memUseNum}" aria-valuemin="0" aria-valuemax="${physCmpRes.memTotalNum}" style="width: ${(physCmpRes.memUseNum/physCmpRes.memTotalNum)*100}%;">${physCmpRes.memUseNum}/${physCmpRes.memTotalNum}&nbsp;G</div>
					</div>
					<h5>内存</h5>
				</td>
				<td style="padding:10px;" align="center" width="200px" height="200px">
					<div class="chart-progress">
						<div class="chart-progress-bar chart-progress-bar-info" role="progressbar" aria-valuenow="${physCmpRes.storeUseNum}" aria-valuemin="0" aria-valuemax="${physCmpRes.storeTotalNum}" style="width: ${(physCmpRes.storeUseNum/physCmpRes.storeTotalNum)*100}%;">${physCmpRes.storeUseNum}/${physCmpRes.storeTotalNum}&nbsp;G</div>
					</div>
					<h5>磁盘</h5>
				</td>
			</tr>
			<tr>
				<td align="left" valign="top" colspan="4"><div align="left" style="width: 100px;border:1px solid #000000;"><h4>负载情况</h4></div></td>
				<td align="left" valign="top" colspan="4"><div align="left" style="width: 100px;border:1px solid #000000;"><h4>运行情况</h4></div></td>
			</tr>
			<tr>
				<td></td>
				<td id="fzqkVirChart" align="center" width="200px" height="200px">虚拟机</td>
				<td id="fzqkHostChart" align="center" width="200px" height="200px"></td>
				<td id="fzqkPhysChart" align="center" width="200px" height="200px"></td>
			</tr>
		</table>
	</div>
</div>
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"><i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a><!-- 返回顶部 -->
<%@ include file="foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
getGaugeChart('virCpuChart', ['', 'CPU(${virCmpRes.cpuUseNum}/${virCmpRes.cpuTotalNum})'], '${(virCmpRes.cpuUseNum/virCmpRes.cpuTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('virMemChart', ['', '内存(${virCmpRes.memUseNum}/${virCmpRes.memTotalNum})'], '${(virCmpRes.memUseNum/virCmpRes.memTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('virStoreChart', ['', '磁盘(${virCmpRes.storeUseNum}/${virCmpRes.storeTotalNum})'], '${(virCmpRes.storeUseNum/virCmpRes.storeTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getPieChart('fzqkVirChart', ['CPU容量'], ['总量','申请中','已分配','剩余'], ['${cmpRes.cpuTotalNum}', '${cmpRes.cpuAppNum}', '${cmpRes.cpuUseNum}', '${cmpRes.cpuRestNum}'], ['#00E5EE', '#FF0000', '#CCCCCC','#00FF00'], "核");//获取图表
getPieChart('fzqkHostChart', ['CPU容量'], ['总量','申请中','已分配','剩余'], ['${cmpRes.cpuTotalNum}', '${cmpRes.cpuAppNum}', '${cmpRes.cpuUseNum}', '${cmpRes.cpuRestNum}'], ['#00E5EE', '#FF0000', '#CCCCCC','#00FF00'], "核");//获取图表
getPieChart('fzqkPhysChart', ['CPU容量'], ['总量','申请中','已分配','剩余'], ['${cmpRes.cpuTotalNum}', '${cmpRes.cpuAppNum}', '${cmpRes.cpuUseNum}', '${cmpRes.cpuRestNum}'], ['#00E5EE', '#FF0000', '#CCCCCC','#00FF00'], "核");//获取图表
</script>
</body>
</html>