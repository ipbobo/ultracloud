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
			url : "restrend/queryLine.do",  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				loadLineChart(data.cpu.chartTitle, "line1", data.cpu.titleArr, data.cpu.xDataArr, data.cpu.yDataArr);
				loadLineChart(data.disk.chartTitle, "line2", data.disk.titleArr, data.disk.xDataArr, data.disk.yDataArr);
				loadLineChart(data.vm.chartTitle, "line3", data.vm.titleArr, data.vm.xDataArr, data.vm.yDataArr);
				loadLineChart(data.memory.chartTitle, "line4", data.memory.titleArr, data.memory.xDataArr, data.memory.yDataArr);
			}
		});  
	
}
function loadLineChart(chartTitle, chartId, titleArr, xdataArr, ydataArr){
	
	var option = {
			title: {text: chartTitle},
		    tooltip: {trigger: 'axis'},//item或axis
		    legend: {data: titleArr},
		    calculable: true,
		    xAxis: [
		        {
		        	show: true,
		            type: 'category',
		            boundaryGap : false,
		            axisLabel : {
		                formatter: '{value}',
		                interval: 0//全部显示，仅type为category有效
		            },
		            data : xdataArr
		        }
		    ],
		    yAxis:  [
		        {
		            type: 'value'
		        }
		    ],
		    series: lineSerrie(ydataArr)
		};
		var myChart = echarts.init(document.getElementById(chartId));
		myChart.setOption(option);//加载图表
}

function lineSerrie(data){
   var serrie = [];
   for(var i = 0; i < data.length; i++){
       var item = {
           name:data[i].name,
           type: 'line',
           data: data[i].value
       }
       serrie.push(item);
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
			<table style="width:80%;">
				<tr style="height: 200px;">
					<td id="line1" align="center" style="width: 400px;"></td>
					<td id="line2" align="center" style="width: 400px;" ></td>
				</tr>
				<tr style="height: 200px;">
					<td id="line3" align="center" style="width: 400px;" ></td>
					<td id="line4" align="center" style="width: 400px;" ></td>
				</tr>
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