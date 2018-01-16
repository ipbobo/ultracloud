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
<script type="text/javascript">
//复选框选择
function checkFunc(){
	if($("#chkId").is(":checked")){
		timeRefresh=window.setInterval(getDashboard, 300000)
	}else{
		clearInterval(timeRefresh);
	}
}

//获取仪表盘
function getDashboard(){
	var cpuTimeType=$("#cpuTimeType").val();//CPU时间类型
	var memTimeType=$("#memTimeType").val();//内存时间类型
	var storeTimeType=$("#storeTimeType").val();//磁盘时间类型
	var resType=$("#resType").val();//资源类型
	var chkFlag=$("#chkId").is(":checked")?"1":"0";//复选框是否选中：0-否；1-是
	window.location.href="<%=basePath%>login_default.do?cpuTimeType="+cpuTimeType+"&memTimeType="+memTimeType+"&storeTimeType="+storeTimeType+"&resType="+resType;
}
</script>
</head>
<body onload="checkFunc()">
<div class="main-container">
	<div class="page-content">
		<div style="height:5px;"></div>
		<div style="padding: 0px;margin: 0px" class="alert alert-block">
			<table style="width:100%;height:35px;padding:0px;">
				<tr>
					<td style="width: 170px;background-color: #dff0d8;" rowspan="2"><img src="static/login/logo.png" alt="Logo" width="80px" height="35px"/>拓云管理平台</td>
					<td>&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center" width="80px">虚机总量</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center" width="95px">宿主机总量</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center" width="95px">物理机总量</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center" width="80px">用户总数</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center" width="80px">项目总数</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center" width="80px">工单总数</td>
					<td align="center" width="50px" rowspan="2"><input id="chkId" type="checkbox" onclick="checkFunc()" <c:if test="${chkFlag=='1'}">checked</c:if>/>刷新</td>
					<td align="center" width="20px" rowspan="2"><button type="button" class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center">${virNum}</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center">${hostNum}</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center">${physNum}</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center">${userNum}</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center">${projNum}</td>
					<td width="20px">&nbsp;</td>
					<td style="background-color: #dff0d8;" align="center">${workOrderNum}</td>
				</tr>
			</table>
		</div>
		<table style="width: 100%;">
			<tr>
				<td align="left" valign="top" style="padding-top: 10px;" colspan="3"><div align="center" style="width: 80px;border:1px solid #000000;"><h5>虚拟资源</h5></div></td>
				<td align="left" valign="top" style="padding-top: 10px;" colspan="3"><div align="center" style="width: 80px;border:1px solid #000000;"><h5>物理资源</h5></div></td>
			</tr>
			<tr height="150px">
				<td id="virCpuChart" align="center" width="16%"></td>
				<td id="virMemChart" align="center" width="16%"></td>
				<td id="virStoreChart" align="center" width="16%"></td>
				<td id="physCpuChart" align="center" width="16%"></td>
				<td id="physMemChart" align="center" width="16%"></td>
				<td id="physStoreChart" align="center" width="16%"></td>
			</tr>
			<tr>
				<td align="left" valign="top"><div align="center" style="width: 80px;border:1px solid #000000;"><h5>负载情况</h5></div></td>
				<td align="left" colspan="2"><img src="images/load.jpg"/></td>
				<td align="left" valign="top"><div align="center" style="width: 80px;border:1px solid #000000;"><h5>运行情况</h5></div></td>
				<td align="left" colspan="2"><img src="images/run.jpg"/></td>
			</tr>
			<tr height="180px">
				<td id="virLoadChart" align="center" width="16%"></td>
				<td id="hostLoadChart" align="center" width="16%"></td>
				<td id="physLoadChart" align="center" width="16%"></td>
				<td id="virRunChart" align="center" width="16%"></td>
				<td id="hostRunChart" align="center" width="16%"></td>
				<td id="physRunChart" align="center" width="16%"></td>
			</tr>
			<tr>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">虚拟机</div></td>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">宿主机</div></td>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">物理机</div></td>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">虚拟机</div></td>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">宿主机</div></td>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">物理机</div></td>
			</tr>
			<tr>
				<td align="left" valign="top" colspan="3"><div align="center" style="width: 120px;border:1px solid #000000;"><h5>资源使用量趋势</h5></div></td>
				<td align="left" valign="top" colspan="3"><div align="center" style="width: 140px;border:1px solid #000000;"><h5>TOP5资源使用排行</h5></div></td>
			</tr>
			<tr>
				<td align="right" style="padding-top: 10px;">
					<select style="width: 100px;" class="chosen-select form-control" name="cpuTimeType" id="cpuTimeType" data-placeholder="请选择时间类型" style="vertical-align:top;width: 100%;" onchange="getDashboard()">
					<c:forEach items="${timeTypeList}" var="var">
						<option value="${var.dictCode}" <c:if test="${cpuTimeType==var.dictCode || (cpuTimeType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
					</c:forEach>
				  	</select>
				</td>
				<td align="right" style="padding-top: 10px;">
					<select style="width: 100px;" class="chosen-select form-control" name="memTimeType" id="memTimeType" data-placeholder="请选择时间类型" style="vertical-align:top;width: 100%;" onchange="getDashboard()">
					<c:forEach items="${timeTypeList}" var="var">
						<option value="${var.dictCode}" <c:if test="${memTimeType==var.dictCode || (memTimeType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
					</c:forEach>
				  	</select>
				</td>
				<td align="right" style="padding-top: 10px;">
					<select style="width: 100px;" class="chosen-select form-control" name="storeTimeType" id="storeTimeType" data-placeholder="请选择时间类型" style="vertical-align:top;width: 100%;" onchange="getDashboard()">
					<c:forEach items="${timeTypeList}" var="var">
						<option value="${var.dictCode}" <c:if test="${storeTimeType==var.dictCode || (storeTimeType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
					</c:forEach>
				  	</select>
				</td>
				<td align="right" style="padding-top: 10px;" colspan="3">
					<select style="width: 100px;" class="chosen-select form-control" name="resType" id="resType" data-placeholder="请选择资源类型" style="vertical-align:top;width: 100%;" onchange="getDashboard()">
					<c:forEach items="${resTypeList}" var="var">
						<option value="${var.dictCode}" <c:if test="${resType==var.dictCode || (resType=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
					</c:forEach>
				  	</select>
				</td>
			</tr>
			<tr height="180px">
				<td id="cpuChart" align="center"></td>
				<td id="memChart" align="center"></td>
				<td id="storeChart" align="center"></td>
				<td align="center" style="padding-top: 5px;" colspan="3">
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center" style="width: 50px;">序号</th>
								<th class="center">虚机名</th>
								<th class="center">IP</th>
								<th class="center">使用率</th>
								<th class="center">使用量</th>
								<th class="center">分配量</th>
							</tr>
						</thead>
						<c:choose>
						<c:when test="${not empty resUseList}">
						<c:forEach items="${resUseList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1+page.currentResult}</td>
								<td class='center'>${var.name}</td>
								<td class='center'>${var.ip}</td>
								<td class='center'>${var.useRate}</td>
								<td class='center'>${var.useNum}</td>
								<td class='center'>${var.allotNum}</td>
							</tr>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="6" class="center">没有相关数据</td>
							</tr>
						</c:otherwise>
						</c:choose>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">CPU</div></td>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">内存</div></td>
				<td align="center" style="padding: 10px;"><div align="center" style="width: 50px;border:1px solid #000000;">磁盘</div></td>
				<td align="center" style="padding: 10px;" colspan="3"></td>
			</tr>
		</table>
	</div>
</div>
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"><i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a><!-- 返回顶部 -->
<%@ include file="foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
getGaugeChart('virCpuChart', ['', 'CPU(${vir.cpuUseNum}/${vir.cpuTotalNum}核)'], '${(vir.cpuUseNum/vir.cpuTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('virMemChart', ['', '内存(${vir.memUseNum}/${vir.memTotalNum}GB)'], '${(vir.memUseNum/vir.memTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('virStoreChart', ['', '磁盘(${vir.storeUseNum}/${vir.storeTotalNum}GB)'], '${(vir.storeUseNum/vir.storeTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('physCpuChart', ['', 'CPU(${phys.cpuUseNum}/${phys.cpuTotalNum}核)'], '${(phys.cpuUseNum/phys.cpuTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('physMemChart', ['', '内存(${phys.memUseNum}/${phys.memTotalNum}GB)'], '${(phys.memUseNum/phys.memTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getGaugeChart('physStoreChart', ['', '磁盘(${phys.storeUseNum}/${phys.storeTotalNum}GB)'], '${(phys.storeUseNum/phys.storeTotalNum)*100}', [[0.1, '#00ff00'],[0.2, '#66ff00'],[0.3, '#99ff00'],[0.4, '#ccff00'],[0.5, '#ffff00'],[0.6, '#ffcc00'],[0.7, '#ff9900'],[0.8, '#ff6600'],[0.9, '#ff3300'],[1, '#ff0000']], "%");//获取图表
getAnnularChart('virLoadChart', ['${virLoad.loadTotalNum}台','虚拟机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${virLoad.loadLittleNum}台','${virLoad.loadMiddleNum}台','${virLoad.loadHeightNum}台','${virLoad.loadStopNum}台'], ['${virLoad.loadLittleNum}', '${virLoad.loadMiddleNum}', '${virLoad.loadHeightNum}', '${virLoad.loadStopNum}'], ['#00ff00', '#ffff00', '#ff0000','#A9A9A9'], "%");//获取图表
getAnnularChart('hostLoadChart', ['${hostLoad.loadTotalNum}台','宿主机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${hostLoad.loadLittleNum}台','${hostLoad.loadMiddleNum}台','${hostLoad.loadHeightNum}台','${hostLoad.loadStopNum}台'], ['${hostLoad.loadLittleNum}', '${hostLoad.loadMiddleNum}', '${hostLoad.loadHeightNum}', '${hostLoad.loadStopNum}'], ['#00ff00', '#ffff00', '#ff0000','#A9A9A9'], "%");//获取图表
getAnnularChart('physLoadChart', ['${physLoad.loadTotalNum}台','物理机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${physLoad.loadLittleNum}台','${physLoad.loadMiddleNum}台','${physLoad.loadHeightNum}台','${physLoad.loadStopNum}台'], ['${physLoad.loadLittleNum}', '${physLoad.loadMiddleNum}', '${physLoad.loadHeightNum}', '${physLoad.loadStopNum}'], ['#00ff00', '#ffff00', '#ff0000','#A9A9A9'], "%");//获取图表
getAnnularChart('virRunChart', ['${virRun.runTotalNum}台','虚拟机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${virRun.runRunnigNum}台','${virRun.runHangupNum}台','${virRun.runCloseNum}台'], ['${virRun.runRunnigNum}','${virRun.runHangupNum}','${virRun.runCloseNum}'], ['#00ff00', '#ffff00', '#ff0000'], "%");//获取图表
getAnnularChart('hostRunChart', ['${hostRun.runTotalNum}台','宿主机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${hostRun.runRunnigNum}台','${hostRun.runHangupNum}台','${hostRun.runCloseNum}台'], ['${hostRun.runRunnigNum}','${hostRun.runHangupNum}','${hostRun.runCloseNum}'], ['#00ff00', '#ffff00', '#ff0000'], "%");//获取图表
getAnnularChart('physRunChart', ['${physRun.runTotalNum}台','物理机'], ['vertical', 'left', 'top'], ['30%', '50%'], ['50%', '75%'], ['${physRun.runRunnigNum}台','${physRun.runHangupNum}台','${physRun.runCloseNum}台'], ['${physRun.runRunnigNum}','${physRun.runHangupNum}','${physRun.runCloseNum}'], ['#00ff00', '#ffff00', '#ff0000'], "%");//获取图表
getLineChart('cpuChart', ['资源使用量趋势'], '${cpuResRate.xaxis}'.split(","), '${cpuResRate.yaxis}'.split(","));//获取图表
getLineChart('memChart', ['资源使用量趋势'], '${memResRate.xaxis}'.split(","), '${memResRate.yaxis}'.split(","));//获取图表
getLineChart('storeChart', ['资源使用量趋势'], '${storeResRate.xaxis}'.split(","), '${storeResRate.yaxis}'.split(","));//获取图表
//getAnnularChart('virRunChart2', ['','虚拟机'], ['horizontal', 'center', 'bottom'], ['30%', '50%'], ['50%', '50%'], ['运行','挂起','关机'], ['${virRun.runRunnigNum}', '${virRun.runHangupNum}', '${virRun.runCloseNum}'], ['#00ff00', '#ffff00', '#ff0000'], "%");//获取图表
//getAnnularChart('virRunChart3', ['','虚拟机'], ['horizontal', 'center', 'bottom'], ['30%', '50%'], ['50%', '50%'], ['轻负载','中负载','高负载','停机'], ['${virLoad.loadLittleNum}', '${virLoad.loadMiddleNum}', '${virLoad.loadHeightNum}', '${virLoad.loadStopNum}'], ['#00ff00', '#ffff00', '#ff0000','#A9A9A9'], "%");//获取图表
</script>
</body>
</html>