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
var option = {
    title : {text: 'CPU', subtext: '使用情况', x:'center'},  
    tooltip : {
        trigger: 'item',  
        formatter: "{a} <br/>{b} : {c} %"  
    },  
    legend: {  
        orient : 'vertical',  
        x : 'left',  
        data:['正常','警告','危险','未知']  
    }, 
    color:['green', '#f5b031','red','blueviolet'],
    toolbox: {  
        show : false,  
        feature : {  
            mark : {show: true},  
            dataView : {show: true, readOnly: false},  
            magicType : {  
                show: true,   
                type: ['pie', 'funnel'],  
                option: {  
                    funnel: {  
                        x: '25%',  
                        width: '50%',  
                        funnelAlign: 'left',  
                        max: 1548  
                    }  
                }  
            },  
            restore : {show: true},  
            saveAsImage : {show: true}  
        }  
    },  
    calculable : true,  
    series : [
        {
            name:'CPU',  
            type:'pie',  
            radius : '55%',//饼图的半径大小  
            center: ['50%', '60%'],//饼图的位置  
            data: [
          {value:65, name:'正常'},  
          {value:20, name:'警告'},  
          {value:5, name:'危险'},  
          {value:10, name:'未知'}, 
         ] 
        }  
    ]  
};

var cpuChart = echarts.init(document.getElementById('cpuChart'));//基于准备好的dom，初始化echarts实例
var memChart = echarts.init(document.getElementById('memChart'));//基于准备好的dom，初始化echarts实例
cpuChart.setOption(option);//使用刚指定的配置项和数据显示图表
memChart.setOption(option);//使用刚指定的配置项和数据显示图表
</script>
</head>
<body>
<form id="mainForm" name="mainForm" action="bizview/callist.do" enctype="multipart/form-data" method="post">
	<table style="margin-top: 0px;margin-left: 0px;margin-bottom: 10px;">
		<tr class="tablecls">
			<td align="left" style="width: 120px;padding-right: 10px;">
				<select class="chosen-select form-control" name="bizviewType" id="bizviewType" data-placeholder="请选择业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="bizviewTypeFunc('bizviewType')">
				<c:forEach items="${bizviewTypeList}" var="var">
					<option value="${var.dictCode}" <c:if test="${bizviewType==var.dictCode || (bizviewType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="left" style="width: 120px;">
				<select class="chosen-select form-control" name="subBizviewType" id="subBizviewType" data-placeholder="请选择子业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="subBizviewTypeFunc('bizviewType', 'subBizviewType')">
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
					<td align="right" style="width: 120px;padding-top: 10px;">
						<div class="col-xs-12">
							<div id="cpuChart" style="width: 400px;height:400px;" class="col-xs-4 col-sm-4" ></div>
							<div id="memChart" style="width: 400px;height:400px;" class="col-xs-4 col-sm-4" ></div>
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