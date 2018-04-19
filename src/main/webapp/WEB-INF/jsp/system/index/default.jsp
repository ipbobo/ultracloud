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
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<script type="text/javascript" src="plugins/echarts/echarts.min.js"></script><!-- 百度echarts -->

	<!--新的界面优化代码 start @zjb 2018-2-7 -->
	<link rel="stylesheet" href="css/newSkin.css">
	<script src="js/tuoyunChart.js"></script>
	<!--新的界面优化代码 end @zjb 2018-2-7 -->
<script type="text/javascript">
//复选框选择
function checkFunc(){
	//if($("#chkId").is(":checked")){
		timeRefresh=window.setInterval(getDashboard, 300000)
	//}else{
	//	clearInterval(timeRefresh);
	//}
}

//查询加载仪表盘(暂时没有使用)
function getDashboard(){
	var cpuTimeType=$("#cpuTimeType").val();//CPU时间类型
	var memTimeType=$("#memTimeType").val();//内存时间类型
	var storeTimeType=$("#storeTimeType").val();//磁盘时间类型
	var resType=$("#resType").val();//资源类型
	var chkFlag=$("#chkId").is(":checked")?"1":"0";//复选框是否选中：0-否；1-是
	window.location.href="<%=basePath%>login_default.do?cpuTimeType="+cpuTimeType+"&memTimeType="+memTimeType+"&storeTimeType="+storeTimeType+"&resType="+resType;
}

//资源使用量趋势
function getResRate(resType){
	$.ajax({
	    type: 'post',
	    data: JSON.stringify({
	    	"resType": resType,//资源类型：cpu-CPU、mem-内存、store-磁盘
	    	"cpuTimeType": $("#cpuTimeType").val(),//CPU时间类型
	    	"memTimeType": $("#memTimeType").val(),//内存时间类型
	    	"storeTimeType": $("#storeTimeType").val(),//磁盘时间类型
	    	"hostIdStr": $("#hostIdStr").val()//主机ID列表
	    }),
	    url: "<%=basePath%>getResRate.do",
	    dataType: 'json',  
	    success: function(data){
		    if(data.retCode=="0"){//成功
		    	getLineChart(resType+'Chart', ['资源使用量趋势'], data.resRate.xaxis.split(","), data.resRate.yaxis.split(","));//获取图表
		    }
	    },
	    error: function(data) {}
	});
}

//资源使用列表
function getResUseList(){
	$.each($("#simple-table tr"), function (i, item) {
    	if(i!=0)item.remove();
	});

	$.ajax({
	    type: 'post',
	    data: JSON.stringify({
	    	"resType": $("#resType").val(),//资源类型：cpu-CPU、mem-内存、store-磁盘
	    	"hostIdStr": $("#hostIdStr").val()//主机ID列表
	    }),
	    url: "<%=basePath%>getResUseList.do",
	    dataType: 'json',  
	    success: function(data){
		    if(data.retCode=="0"){//成功
		    	var resUseList=data.resUseList;
		    	if(!resUseList){
		    		$("#simple-table").append("<tr class=\"main_info\"><td colspan=\"6\" class=\"center\">没有相关数据</td></tr>");
		    		return;
		    	}
		    	
				$.each(resUseList, function (i, item) {
					var trStr="<tr>"
						+"<td style=\"padding: 0px;width: 30px;\" class='center'>"+(i+1)+"</td>"
						+"<td style=\"padding: 0px;\" class='center'>"+item.hostName+"</td>"
						+"<td style=\"padding: 0px;\" class='center'>"+item.hostIp+"</td>"
						+"<td style=\"padding: 0px;\" class='center'>"+item.useRate+"</td>"
						+"<td style=\"padding: 0px;\" class='center'>"+item.useNum+"</td>"
						+"<td style=\"padding: 0px;\" class='center'>"+item.allotNum+"</td>"
						+"</tr>";
				    $("#simple-table").append(trStr);
			    });
		    }
	    },
	    error: function(data) {}
	});
}

function closeHeadToggle(){
    $('.head-toggle').hide();
}
</script>
</head>
<body onload="checkFunc()" class="login_default">
<input id="hostIdStr" name="hostIdStr" type="hidden" value="${hostIdStr}">
<div id="tip" style="display:none">
    <table>
        <tr>
            <td class="top-left">&nbsp;</td>
            <td class="top-middle">&nbsp;</td>
            <td class="top-right">&nbsp;</td>
        </tr>
        <tr>
            <td class="center-left">&nbsp;</td>
            <td id="tip-msg" class="center-middle"></td>
            <td class="center-right">&nbsp;</td>
        </tr>
        <tr>
            <td class="bottom-left">&nbsp;</td>
            <td class="bottom-middle">&nbsp;</td>
            <td class="bottom-right">&nbsp;</td>
        </tr>
    </table>
</div>
<div class="head-toggle" style="margin-top: 20px">
	<ul>
		<li>虚机总量  ${virNum}</li>
		<li>宿主机总量  ${hostNum}</li>
		<li>物理机总量  ${physNum}</li>
		<li>用户总数  ${userNum}</li>
		<li>项目总数  ${projNum}</li>
		<li>工单总数  ${workOrderNum}</li>
		<li><button type="button" class="close" data-dismiss="alert" onclick="closeHeadToggle()"><i class="fa fa-close"></i></button></li>
	</ul>
</div>
<div class="main-container">
<div class="page-content">
<!--新的界面优化代码 start @zjb 2018-2-7 -->
<div class="row">
	<div class="col-xs-6 col-md-6 col-sm-12" style="margin-top: 20px;">
		<div class="mem-panel">
			<div class="inner-box">
				<div class="head-box">
					虚拟资源
				</div>
				<div class="body-box">
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="col-xs-4">
								<div id="virCpuChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="virMemChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="virStoreChart" style="width: 100%"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-6 col-md-6 col-sm-12" style="margin-top: 20px;">
		<div class="mem-panel">
			<div class="inner-box">
				<div class="head-box">
					宿主机
				</div>
				<div class="body-box">
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="col-xs-4">
								<div id="physCpuChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="physMemChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="physStoreChart" style="width: 100%"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-6 col-md-6 col-sm-12" style="margin-top: 20px">
		<div class="mem-panel">
			<div class="inner-box">
				<div class="head-box">
					<span>负载情况</span>
                          <ul>
                              <li class="blues">低负载</li>
                              <li class="yellows">中负载</li>
                              <li class="pinks">高负载</li>
                              <li class="grays">停机</li>
                          </ul>
				</div>
				<div class="body-box">
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="col-xs-4">
								<div id="virLoadChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="hostLoadChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="physLoadChart" style="width: 100%"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-6 col-md-6 col-sm-12" style="margin-top: 20px">
		<div class="mem-panel">
			<div class="inner-box">
				<div class="head-box">
                          <span>运行情况</span>
                          <ul>
                              <li class="blues">运行</li>
                              <li class="yellows">挂起</li>
                              <li class="pinks">关机</li>
                          </ul>
				</div>
				<div class="body-box">
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="col-xs-4">
								<div id="virRunChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="hostRunChart" style="width: 100%"></div>
							</div>
							<div class="col-xs-4">
								<div id="physRunChart" style="width: 100%"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-6 col-md-12 col-sm-12" style="margin-top: 20px">
		<div class="mem-panel">
			<div class="inner-box">
				<div class="head-box">
					资源使用量趋势
				</div>
				<div class="body-box">
					<div id="left-container" class="container-fluid">
						<div class="row-fluid">
							<div class="col-xs-4">
								<div class="select-box">
									<select style="width: 100px;" class="chosen-select form-control" name="cpuTimeType" id="cpuTimeType" data-placeholder="请选择时间类型" style="vertical-align:top;width: 100%;" onchange="getResRate('cpu')">
									<c:forEach items="${timeTypeList}" var="var">
										<option value="${var.dictCode}" <c:if test="${cpuTimeType==var.dictCode || (cpuTimeType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
									</c:forEach>
									</select>
								</div>
								<div id="cpuChart" style="width: 100%;padding: 30px 0 20px 0"></div>
								<div align="center" style="width:100%;text-align: center;padding-bottom: 10px">CPU</div>
							</div>
							<div class="col-xs-4">
								<div class="select-box">
									<select style="width: 100px;" class="chosen-select form-control" name="memTimeType" id="memTimeType" data-placeholder="请选择时间类型" style="vertical-align:top;width: 100%;" onchange="getResRate('mem')">
									<c:forEach items="${timeTypeList}" var="var">
										<option value="${var.dictCode}" <c:if test="${memTimeType==var.dictCode || (memTimeType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
									</c:forEach>
									</select>
								</div>
								<div id="memChart" style="width: 100%;padding: 30px 0 20px 0"></div>
								<div align="center" style="width:100%;text-align: center;padding-bottom: 10px">内存</div>
							</div>
							<div class="col-xs-4">
								<div class="select-box">
									<select style="width: 100px;" class="chosen-select form-control" name="storeTimeType" id="storeTimeType" data-placeholder="请选择时间类型" style="vertical-align:top;width: 100%;" onchange="getResRate('store')">
									<c:forEach items="${timeTypeList}" var="var">
										<option value="${var.dictCode}" <c:if test="${storeTimeType==var.dictCode || (storeTimeType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
									</c:forEach>
									</select>
								</div>
								<div id="storeChart" style="width: 100%;padding: 30px 0 20px 0"></div>
								<div align="center" style="width:100%;text-align: center;padding-bottom: 10px">磁盘</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-6 col-md-12 col-sm-12" style="margin-top: 20px">
		<div class="mem-panel">
			<div class="inner-box">
				<div class="head-box">
					<span>TOP5资源使用排行</span>
					<select style="width: 100px;" class="chosen-select form-control" name="resType" id="resType" data-placeholder="请选择资源类型" style="vertical-align:top;width: 100%;" onchange="getResUseList()">
					<c:forEach items="${resTypeList}" var="var">
						<option value="${var.dictCode}" <c:if test="${resType==var.dictCode || (resType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
					</c:forEach>
					</select>
				</div>
				<div class="body-box">
					<div id="right-container" class="container-fluid" style="height: auto;padding: 20px">
						<table id="simple-table" class="simple-table">
							<thead>
							<tr>
								<th style="padding: 0px;width:30px" class="center">序号</th>
								<th style="padding: 0px;" class="center">虚机名</th>
								<th style="padding: 0px;" class="center">IP</th>
								<th style="padding: 0px;" class="center">使用率</th>
								<th style="padding: 0px;" class="center">使用量</th>
								<th style="padding: 0px;" class="center">分配量</th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${not empty resUseList}">
									<c:forEach items="${resUseList}" var="var" varStatus="vs">
										<tr>
										<td style="padding: 0px;width: 30px;" class='center'>${vs.index+1+page.currentResult}</td>
										<td style="padding: 0px;" class='center'>${var.hostName}</td>
										<td style="padding: 0px;" class='center'>${var.hostIp}</td>
										<td style="padding: 0px;" class='center'>${var.useRate}</td>
										<td style="padding: 0px;" class='center'>${var.useNum}</td>
										<td style="padding: 0px;" class='center'>${var.allotNum}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info"><td colspan="6" class="center">没有相关数据</td></tr>
								</c:otherwise>
							</c:choose>
							</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--新的界面优化代码 end @zjb 2018-2-7 -->
</div>
</div>
<%@ include file="foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
/* 新的界面优化代码 start @zjb 2018-2-7 */
getTuoyunChart('virCpuChart','CPU(${vir.cpuUseNum}/${vir.cpuTotalNum}核)','${(vir.cpuTotalNum==0?0:vir.cpuUseNum/vir.cpuTotalNum)*100}','meterStyle1');
getTuoyunChart('virMemChart','内存(${vir.memUseNum}/${vir.memTotalNum}GB)','${(vir.memTotalNum==0?0:vir.memUseNum/vir.memTotalNum)*100}','meterStyle1');
getTuoyunChart('virStoreChart','磁盘(${vir.storeUseNum}/${vir.storeTotalNum}GB)','${(vir.storeTotalNum==0?0:vir.storeUseNum/vir.storeTotalNum)*100}','meterStyle1');
getTuoyunChart('physCpuChart','CPU(${phys.cpuUseNum}/${phys.cpuTotalNum}核)','${(phys.cpuTotalNum==0?0:phys.cpuUseNum/phys.cpuTotalNum)*100}','meterStyle1');
getTuoyunChart('physMemChart','内存(${phys.memUseNum}/${phys.memTotalNum}GB)','${(phys.memTotalNum==0?0:phys.memUseNum/phys.memTotalNum)*100}','meterStyle1');
getTuoyunChart('physStoreChart','磁盘(${phys.storeUseNum}/${phys.storeTotalNum}GB)','${(phys.storeTotalNum==0?0:phys.storeUseNum/phys.storeTotalNum)*100}','meterStyle1');
getTuoyunPercentChart('virLoadChart','虚拟机','${virLoad.loadLittleNum}','${virLoad.loadMiddleNum}','${virLoad.loadHeightNum}','${virLoad.loadStopNum}','低','中','高','停');
getTuoyunPercentChart('hostLoadChart','宿主机','${hostLoad.loadLittleNum}','${hostLoad.loadMiddleNum}','${hostLoad.loadHeightNum}','${hostLoad.loadStopNum}','低','中','高','停');
getTuoyunPercentChart('physLoadChart','物理机','${physLoad.loadLittleNum}','${physLoad.loadMiddleNum}','${physLoad.loadHeightNum}','${physLoad.loadStopNum}','低','中','高','停');
getTuoyunPercentChart('virRunChart','虚拟机','${virRun.runRunnigNum}','${virRun.runHangupNum}','${virRun.runCloseNum}','0','运','挂','关','');
getTuoyunPercentChart('hostRunChart','宿主机','${hostRun.runRunnigNum}','${hostRun.runHangupNum}','${hostRun.runCloseNum}','0','运','挂','关','');
getTuoyunPercentChart('physRunChart','物理机','${physRun.runRunnigNum}','${physRun.runHangupNum}','${physRun.runCloseNum}','0','运','挂','关','');
/* 新的界面优化代码 end @zjb 2018-2-7 */

//getGaugeChart('virCpuChart', ['', 'CPU(${vir.cpuUseNum}/${vir.cpuTotalNum}核)'], ['50%', '40%'], [0, 65], '${(vir.cpuUseNum/vir.cpuTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
//getGaugeChart('virMemChart', ['', '内存(${vir.memUseNum}/${vir.memTotalNum}GB)'], ['50%', '40%'], [0, 65], '${(vir.memUseNum/vir.memTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
//getGaugeChart('virStoreChart', ['', '磁盘(${vir.storeUseNum}/${vir.storeTotalNum}GB)'], ['50%', '40%'], [0, 65], '${(vir.storeUseNum/vir.storeTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
//getGaugeChart('physCpuChart', ['', 'CPU(${phys.cpuUseNum}/${phys.cpuTotalNum}核)'], ['50%', '40%'], [0, 65], '${(phys.cpuUseNum/phys.cpuTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
//getGaugeChart('physMemChart', ['', '内存(${phys.memUseNum}/${phys.memTotalNum}GB)'], ['50%', '40%'], [0, 65], '${(phys.memUseNum/phys.memTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
//getGaugeChart('physStoreChart', ['', '磁盘(${phys.storeUseNum}/${phys.storeTotalNum}GB)'], ['50%', '40%'], [0, 65], '${(phys.storeUseNum/phys.storeTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
//getAnnularChart('virLoadChart', ['${virLoad.loadTotalNum}台','虚拟机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${virLoad.loadLittleNum}台(轻)','${virLoad.loadMiddleNum}台(中)','${virLoad.loadHeightNum}台(高)','${virLoad.loadStopNum}台(停)'], ['${virLoad.loadLittleNum}', '${virLoad.loadMiddleNum}', '${virLoad.loadHeightNum}', '${virLoad.loadStopNum}'], ['#00ff00', '#ffff00', '#ff0000','#A9A9A9'], "%");//获取图表
//getAnnularChart('hostLoadChart', ['${hostLoad.loadTotalNum}台','宿主机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${hostLoad.loadLittleNum}台(轻)','${hostLoad.loadMiddleNum}台(中)','${hostLoad.loadHeightNum}台(高)','${hostLoad.loadStopNum}台(停)'], ['${hostLoad.loadLittleNum}', '${hostLoad.loadMiddleNum}', '${hostLoad.loadHeightNum}', '${hostLoad.loadStopNum}'], ['#00ff00', '#ffff00', '#ff0000','#A9A9A9'], "%");//获取图表
//getAnnularChart('physLoadChart', ['${physLoad.loadTotalNum}台','物理机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${physLoad.loadLittleNum}台(轻)','${physLoad.loadMiddleNum}台(中)','${physLoad.loadHeightNum}台(高)','${physLoad.loadStopNum}台(停)'], ['${physLoad.loadLittleNum}', '${physLoad.loadMiddleNum}', '${physLoad.loadHeightNum}', '${physLoad.loadStopNum}'], ['#00ff00', '#ffff00', '#ff0000','#A9A9A9'], "%");//获取图表
//getAnnularChart('virRunChart', ['${virRun.runTotalNum}台','虚拟机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${virRun.runRunnigNum}台(运)','${virRun.runHangupNum}台(挂)','${virRun.runCloseNum}台(关)'], ['${virRun.runRunnigNum}','${virRun.runHangupNum}','${virRun.runCloseNum}'], ['#00ff00', '#ffff00', '#ff0000'], "%");//获取图表
//getAnnularChart('hostRunChart', ['${hostRun.runTotalNum}台','宿主机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${hostRun.runRunnigNum}台(运)','${hostRun.runHangupNum}台(挂)','${hostRun.runCloseNum}台(关)'], ['${hostRun.runRunnigNum}','${hostRun.runHangupNum}','${hostRun.runCloseNum}'], ['#00ff00', '#ffff00', '#ff0000'], "%");//获取图表
//getAnnularChart('physRunChart', ['${physRun.runTotalNum}台','物理机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${physRun.runRunnigNum}台(运)','${physRun.runHangupNum}台(挂)','${physRun.runCloseNum}台(关)'], ['${physRun.runRunnigNum}','${physRun.runHangupNum}','${physRun.runCloseNum}'], ['#00ff00', '#ffff00', '#ff0000'], "%");//获取图表
$('#cpuChart,#memChart,#storeChart').css("height",$('#cpuChart').width()/2);
var leftH=$('#left-container').height();
var rightH=$('#right-container').height();
if(leftH>=rightH){
	$('#right-container').css("height",$('#left-container').height());
} else {
	$('#left-container').css("height",$('#right-container').height()+40);
}
//getLineChart('cpuChart', ['资源使用量趋势'], '${cpuResRate.xaxis}'.split(","), '${cpuResRate.yaxis}'.split(","));//获取图表
//getLineChart('memChart', ['资源使用量趋势'], '${memResRate.xaxis}'.split(","), '${memResRate.yaxis}'.split(","));//获取图表
//getLineChart('storeChart', ['资源使用量趋势'], '${storeResRate.xaxis}'.split(","), '${storeResRate.yaxis}'.split(","));//获取图表
getResRate("cpu");//资源使用量趋势
getResRate("mem");//资源使用量趋势
getResRate("store");//资源使用量趋势
</script>
</body>
</html>