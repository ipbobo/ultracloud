<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script type="text/javascript">
//查询
function doSearch(){
	var beginDate=$("#beginDate").val();
	var endDate=$("#endDate").val();
	if(beginDate!="" && !isDate(beginDate)){
    	alert("开始日期不正确，请重新输入！");
        $("#beginDate").focus();
        return;
    }
    
    if(endDate!="" && !isDate(endDate)){
    	alert("结束日期不正确，请重新输入！");
        $("#endDate").focus();
        return;
    }
    
    //比较起始日期是否大于等于结束日期
	if(beginDate!="" && endDate!=""){
	    if(beginDate>endDate){
	    	alert("结束日期必须大于等于开始日期，请重新输入！");
	       	$("#endDate").focus();
	        return;
	    }
	}
	
	top.jzts();
	$("#mainForm").submit();
}
</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="#jstab" onclick="tabFunc('jstab')" data-toggle="tab">计算</a></li>
	<li><a href="#cctab" onclick="tabFunc('cctab')" data-toggle="tab">存储</a></li>
</ul>
<div class="tab-content">
<div id="jstab" class="tab-pane fade in active" style="position:fixed;height: 100%">
	<table style="margin-top: 0px;margin-left: 0px;margin-bottom: 10px;">
		<tr class="tablecls">
			<td align="left" style="width: 120px;padding-right: 10px;">
				<select class="chosen-select form-control" name="bizviewType" id="bizviewType" data-placeholder="请选择业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="bizviewTypeFunc()">
				<c:forEach items="${bizviewTypeList}" var="var">
					<option value="${var.dictCode}">${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
			<td align="left" style="width: 120px;">
				<select class="chosen-select form-control" name="subBizviewType" id="subBizviewType" data-placeholder="请选择子业务视图总览类型" style="vertical-align:top;width: 100%;" onchange="subBizviewTypeFunc()">
				<option value="">全部环境</option>
				<c:forEach items="${envCodeList}" var="var">
					<option value="${var.dictCode}">${var.dictValue}</option>
				</c:forEach>
			  	</select>
			</td>
		</tr>
	</table>
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
			<td style="border-bottom:1px solid #cccccc;">
				<table style="width: 100%;border-collapse:separate;border-spacing:0px 10px;">
				<tr>
					<td align="right" style="width: 120px;">资源类型：</td>
					<td align="left" style="width: 180px;">${var.resTypeName}</td>
					<td align="right" style="width: 120px;">实例规格：</td>
					<td align="left" style="width: 180px;">${var.cpu}&nbsp;核&nbsp;${var.memory}&nbsp;GB</td>
					<td align="right" style="width: 120px;">镜像：</td>
					<td align="left" style="width: 180px;">${var.osTypeName}&nbsp;${var.osBitNumName}</td>
				</tr>
				<tr>
					<td align="right" valign="top" style="width: 120px;">数据盘：</td>
					<td align="left" valign="top" style="width: 180px;">
						<t:list key="${var.diskTypeName}" val="${var.diskSize}" name="vars">
							${vars.dictCode}&nbsp;(&nbsp;${vars.dictValue}&nbsp;GB)<br>
						</t:list>
					</td>
					<td align="right" valign="top" style="width: 120px;">购买量：</td>
					<td align="left" valign="top" style="width: 180px;">${var.virNum}&nbsp;台</td>
					<td align="right" valign="top" style="width: 120px;">到期时间：</td>
					<td align="left" valign="top" style="width: 180px;">${var.expireDate}</td>
				</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<div class="widget-box widget-header" style="margin:0px"><h4 class="smaller">云主机列表&nbsp;&nbsp;&nbsp;<font color="#ff0000" size="2">*此列表不包括从资源池同步过来,但未绑定的虚拟机</font></h4></div>
<table id="simple-table" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th class="center" style="width: 50px;">序号</th>
			<th class="center">云主机名称</th>
			<th class="center">IP</th>
			<th class="center">项目</th>
			<th class="center">环境</th>
			<th class="center">CPU</th>
			<th class="center">内存</th>
		</tr>
	</thead>
	<c:choose>
	<c:when test="${not empty list}">
	<c:forEach items="${list}" var="var" varStatus="vs">
		<tr>
			<td class='center' style="width: 30px;">${vs.index+1+page.currentResult}</td>
			<td class='center'>${var.username}</td>
			<td class='center'>${var.operTypeName}</td>
			<td class='center'>${var.opt_object}</td>
			<td class='center'>${var.detail}</td>
			<td class='center'>${var.optStatusName}</td>
			<td class='center'>${var.ip}</td>
			<td class='center'>${var.gmt_create}</td>
			<td class="center"><a class="btn btn-xs btn-danger" onclick="del('${var.id}', '确定要删除该条记录吗?');"><i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i></a></td>
		</tr>
	</c:forEach>
	</c:when>
	<c:otherwise>
		<tr class="main_info">
			<td colspan="100" class="center">没有相关数据</td>
		</tr>
	</c:otherwise>
	</c:choose>
</table>
<div class="page-header position-relative">
	<table style="width: 100%;">
		<tr>
			<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
		</tr>
	</table>
</div>
<div id="cctab" class="tab-pane fade"></div>
</div>	
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a><!-- 返回顶部 -->
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
</script>
</body>
</html>