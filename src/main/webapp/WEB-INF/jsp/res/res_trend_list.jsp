<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../system/index/top.jsp"%>
<link rel="stylesheet" href="css/style.css"/>
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<script type="text/javascript" src="plugins/echarts/echarts.min.js"></script><!-- 百度echarts -->
</head>
<body onload="init();">
<div class="main-container">
	<div class="page-content">
		<div style="height:5px;"></div>
		<div style="padding: 0px;margin: 0px" class="alert alert-block">
			<table style="width:100%;height:300px;padding:0px;">
				<tr>
					<td id="line1" align="center" width="40%"></td>
					<td id="line2" align="center" width="40%"></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态


function init(){
	
}
function loadLineChart(chartId, titleArr, xdataArr, ydataArr){
	var option = {
		    tooltip: {trigger: 'axis'},//item或axis
		    legend: {data: titleArr},
		    calculable: true,
		    grid: {x: 25, y: 5, x2: 15, y2: 25},
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
    var serie = [];
    for(var i = 0; i < data.length; i++){
        var item = {
            name:data[i].name,
            type: 'line',
            data: data[i].value
        }
        serie.push(item );
    };
     return serrie;
}
</script>
</body>
</html>