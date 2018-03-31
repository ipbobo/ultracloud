<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../system/index/top.jsp"%>
<link rel="stylesheet" href="css/style.css"/>
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<script type="text/javascript" src="plugins/echarts/echarts.min.js"></script><!-- 百度echarts -->
<script type="text/javascript">
function init(){
	 jQuery.ajax({  
			url : "resanalyze/queryBar.do",  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				loadBarChart(data.cpu.chartTitle, "bar1", data.cpu.titleArr, data.cpu.xDataArr, data.cpu.yDataArr);
				loadBarChart(data.memory.chartTitle, "bar2", data.memory.titleArr, data.memory.xDataArr, data.memory.yDataArr);
				loadBarChart(data.disk.chartTitle, "bar3", data.disk.titleArr, data.disk.xDataArr, data.disk.yDataArr);
			}
		});  
	
}
function loadBarChart(chartTitle, chartId, titleArr, xdataArr, ydataArr){
	
	var option = {
			title: {text: chartTitle},
		    tooltip: {trigger: 'axis', 
			    	axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }	
		    },//item或axis
		    grid:{
		      	left:'17%',//组件距离容器左边的距离
		      	right:'4%',
		      	bottom:'15%',
		    },
		    legend: {data: titleArr, left:'right'},
		    calculable: true,
		    xAxis: [
		        {
		        	show: true,
		            type: 'category',
		            data : xdataArr
		        }
		    ],
		    yAxis:  [
		        {
		            type: 'value'
		        }
		    ],
		    series: barSerrie(ydataArr)
		};
		var myChart = echarts.init(document.getElementById(chartId));
		myChart.setOption(option);//加载图表
}

function barSerrie(data){
   var serrie = [];
   for(var i = 0; i < data.length; i++){
       var item = {
           name:data[i].name,
           type: 'bar',
           data: data[i].value
       }
       serrie.push(item );
   };
    return serrie;
}
</script>
</head>
<body onload="init();">
<div class="main-container">
	<div class="page-content">
		<div style="height:5px;"></div>
		<div style="padding: 0px;margin: 0px" class="alert alert-block">
			<div class="alert alert-info">资源使用情况</div>		
				<table style="width:80%;">
				<tr style="height: 200px;">
					<td id="bar1" align="center" style="width: 300px;"></td>
					<td id="bar2" align="center" style="width: 300px;" ></td>
					<td id="bar3" align="center" style="width: 300px;" ></td>
				</tr>
				</table>
				<div class="alert alert-info">资源分析</div>				
				<table>
				<c:choose>
					<c:when test="${not empty platformAnalyzeList}">
						<c:forEach items="${platformAnalyzeList}" var="var" varStatus="vs">
								<tr style="padding-top: 20px;">
									<td style="width:120px;text-align: right;padding-top: 13px;">&nbsp;&nbsp;<b>${var.platformName}:</b></td>
									<td>
										<table border="1" style="border: 2px; border-color: black;">
											<thead>
												<tr>
													<th class="center" style="width: 200px;">CPU总量(核)/使用百分比</th>
													<th class="center" style="width: 200px;">日均申请量(核)</th>
													<th class="center" style="width: 200px;">支持分配天数</th>
												</tr>
											</thead>
												<tr>
													<td class="center">${var.cputotal}/${var.cpuUsedPercent} </td>
													<td class="center">${var.cpuAppPerDays}</td>
													<td class="center">${var.cpuSupportAppDays}</td>
												</tr>
											<thead>
												<tr>
													<th class="center">内存总量(G)/使用百分比</th>
													<th class="center">日均申请量(G)</th>
													<th class="center">支持分配天数</th>
												</tr>
											</thead>
												<tr>
													<td class="center">${var.memorytotal}/${var.memoryUsedPercent} </td>
													<td class="center">${var.memoryAppPerDays}</td>
													<td class="center">${var.memorySupportAppDays}</td>
												</tr>
											<thead>
												<tr>
													<th class="center">磁盘总量(G)/使用百分比</th>
													<th class="center">日均申请量(G)</th>
													<th class="center">支持分配天数</th>
												</tr>
											</thead>
												<tr>
													<td class="center">${var.disktotal}/${var.diskUsedPercent} </td>
													<td class="center">${var.diskAppPerDays}</td>
													<td class="center">${var.diskSupportAppDays}</td>
												</tr>
										</table>
									</td>
								</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</table>
		</div>
	</div>
</div>
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态


</script>
</body>
</html>