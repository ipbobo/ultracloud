<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../system/index/top.jsp"%>
<script type="text/javascript">
//查询
function search(){
	top.jzts();
	$("#queryForm").submit();
}
</script>
</head>
<body class="no-skin">
<div class="widget-box">
	<div class="widget-header">
		<h4 class="smaller">条件查询</h4>
	</div>
	<form action="queryUserApplyWorkOrderPre.do" method="post"
		name="queryForm" id="queryForm">
		<table style="margin-top: 5px;">
			<tr>
				<td style="width: 120px; text-align: right;">&nbsp;工单类型:</td>
				<td style="width: 120px; text-align: left;"><select
					name="workorder_type" id="workorder_type" title="选择工单类型">
						
							<option value=""  <c:if test="${pd.workorder_type == null || pd.workorder_type == '' }">selected="selected"</c:if>>选择工单类型</option>
						
						<c:forEach items="${workorderTypeList}" var="var">
							<option value="${var.dictCode}" <c:if test="${pd.workorder_type !='' && var.dictCode == pd.workorder_type }">selected="selected"</c:if> >${var.dictValue}</option>
						</c:forEach>
				</select></td>
				<td style="width: 120px; text-align: right;">&nbsp;工单状态:</td>
				<td style="width: 120px; text-align: left;">
				<select name="workorder_status" id="workorder_status" title="选择工单状态">
						<option value="" <c:if test="${pd.workorder_status == null || pd.workorder_status == '' }">selected="selected"</c:if>>选择工单类型</option>
						<c:forEach items="${workorderStatusList}" var="var">
							<option value="${var.dictCode}" <c:if test="${pd.workorder_status !='' && var.dictCode == pd.workorder_status }">selected="selected"</c:if>>${var.dictValue}</option>
						</c:forEach>
				</select></td>
				<td style="width: 80px; text-align: right;">&nbsp;项目:</td>
				<td style="width: 120px; text-align: left;"><select
					name="project" id="project" title="选择项目">
						<option value="" <c:if test="${pd.project == null || pd.project == '' }">selected="selected"</c:if>>选择项目</option>
						<c:forEach items="${projectList}" var="var">
							<option value="${var.id}"  <c:if test="${pd.project !='' && var.id == pd.project }">selected="selected"</c:if>>${var.shortname}</option>
						</c:forEach>
				</select></td>
				<td style="width: 120px; text-align: right;" rowspan="2">
					<button class="btn btn-info" id="btn_query" type="submit">查询</button>
				</td>
				<td style="width: 80px; text-align: right;" rowspan="2">&nbsp;导出:</td>
				<td style="width: 120px; text-align: left;" rowspan="2">
					&nbsp;&nbsp;<a class="btn btn-light btn-xs"
					onclick="toExcel();" title="导出到EXCEL"><i
						id="nav-search-icon"
						class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a>
				</td>
			</tr>
			<tr>
				<td style="width: 120px; text-align: right;">&nbsp;工单号:</td>
				<td style="width: 120px; text-align: left;"><input
					type="text" name="workorder_appNo" value="${pd.workorder_appNo}" id="workorder_appNo" /></td>
				<td style="width: 120px; text-align: right;">&nbsp;时间选择:</td>
				<td style="width: 120px; text-align: left;"><select
					name="workorder_time" id="workorder_time" title="时间选择">
						<option value="" <c:if test="${pd.workorder_time == null || pd.workorder_time == '' }">selected="selected"</c:if>>全部</option>
						<option value="1" <c:if test="${pd.workorder_time !='' && '1' == pd.workorder_time }">selected="selected"</c:if>>近一周</option>
						<option value="2" <c:if test="${pd.workorder_time !='' && '2' == pd.workorder_time }">selected="selected"</c:if>>近一个月</option>
						<option value="3" <c:if test="${pd.workorder_time !='' && '3' == pd.workorder_time }">selected="selected"</c:if>>近一年</option>
				</select></td>




			</tr>
			<tr>

			</tr>
		</table>
	</form>
</div>
<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: 5px;">
	<thead>
		<tr>
			<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label></th>
			<th class="center" style="width: 50px;">序号</th>
			<th class="center">ID</th>
			<th class="center">操作者</th>
			<th class="center">操作类型</th>
			<th class="center">操作对象</th>
			<th class="center">操作状态</th>
			<th class="center">操作者IP</th>
			<th class="center">操作详细描述</th>
			<th class="center">创建时间</th>
			<th class="center">操作</th>
		</tr>
	</thead>
	<c:choose>
	<c:when test="${not empty list}">
	<c:forEach items="${list}" var="var" varStatus="vs">
		<tr>
			<td class='center'><label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace"/><span class="lbl"></span></label></td>
			<td class='center' style="width: 30px;">${vs.index+1}</td>
			<td class='center'>${var.id}</td>
			<td class='center'>${var.username}</td>
			<td class='center'>${var.type}</td>
			<td class='center'>${var.opt_object}</td>
			<td class='center'>${var.opt_status}</td>
			<td class='center'>${var.ip}</td>
			<td class='center'>${var.detail}</td>
			<td class='center'>${var.gmt_create}</td>
			<td class="center">
				<a class="btn btn-xs btn-success" onclick="del('${var.id}');"><i class="ace-icon fa fa-print  align-top bigger-125" title="删除"></i></a>
			</td>
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
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a><!-- 返回顶部 -->
<%@ include file="../system/index/foot.jsp"%>
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
$(top.hangge());//关闭加载状态
//复选框全选控制
$(function() {
	var active_class = 'active';
	$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
		var th_checked = this.checked;//checkbox inside "TH" table header
		$(this).closest('table').find('tbody > tr').each(function(){
			var row = this;
			if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
			else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
		});
	});
});
</script>
</body>
</html>