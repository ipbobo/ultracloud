<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<!-- 树形下拉框start -->
<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
<link rel="stylesheet" type="text/css" href="plugins/selectZtree/import_fh.css"/>
<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="plugins/selectZtree/ztree/ztree.css"></link>
<!-- 树形下拉框end -->
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						
						<!-- 检索  -->
						<form action="bidatacenter/listBillDay.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;width:100%;">
							<tr>
								<td style="vertical-align: top;"></td>
								<td style="vertical-align:top;padding-left:5px;width:150px;">
								<div class="nav-search" style="float: right;padding-top: 0px;margin-top: 0px;">
									
									<td style="width:70px;text-align: right;vertical-align:center;">部门：</td>
									<td style="vertical-align:center;padding-left:2px;width:150px;">
										<div style="padding-top: 3px;margin-top: 0px;">
											<input type="hidden" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}"/>
											<div class="selectTree" id="selectTree"></div>
										</div>
									</td>
									
									<td style="width:60px;text-align: right;vertical-align:center;">项目：</td>
									<td style="vertical-align:top;padding-left:2px;width:88px;">
										<div style="padding-top: 0px;margin-top: 0px;">
										 	<select class="chosen-select form-control" name="project_id" id="project_id" data-placeholder="请选择项目" style="vertical-align:top;width: 120px;">
												<option value=""></option>
												<option value="全部"></option>
												<c:forEach items="${projectList}" var="project">
													<option value="${project.id }" <c:if test="${pd.project_id==project.id}">selected</c:if>>${project.name }</option>
												</c:forEach>
										  	</select>
									  	</div>
									</td>
									
									<td style="width:80px;text-align: right;vertical-align:center;">申请人：</td>
									<td style="vertical-align:top;padding-left:2px;width:100px;">
										<div style="padding-top: 0px;margin-top: 0px;">
										 	<select class="chosen-select form-control" name="USERNAME" id="USERNAME" data-placeholder="请选择申请人" style="vertical-align:top;width: 120px;">
												<option value=""></option>
												<option value="全部"></option>
												<c:forEach items="${userList}" var="user">
													<option value="${user.USERNAME}" <c:if test="${pd.USERNAME==user.USERNAME}">selected</c:if>>${user.USERNAME}</option>
												</c:forEach>
										  	</select>
									  	</div>
									</td>
									
									<td style="width:60px;text-align: right;vertical-align:center;">状态：</td>
									<td style="vertical-align:top;padding-left:0px;width:88px;">
										<div style="padding-top: 0px;margin-top: 0px;">
									 	<select class="chosen-select form-control" name="status" id="status" data-placeholder="请选择状态" title="状态" style="vertical-align:top;width: 110px;" >
										<option value=""></option>
										<option value="0">运行中</option>
										<option value="1">挂起</option>
										<option value="2">关机</option>
										</select>
									  	</div>
									</td>
									
									<td style="vertical-align:top;padding-left:10px;width:150px;">
											<span class="input-icon">
												<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
												<i class="ace-icon fa fa-search nav-search-icon"></i>
											</span>
									</td>
									<td style="vertical-align:top;padding-left:5px;width: 32px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								
									<td style="vertical-align: top;padding-left:5px;width: 32px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td>
								</div>
								
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
							<thead>
								<tr>
									<th class="center" style="width: 5%;">序号</th>
								    <th class="center" style="width: 10%;">虚拟机名称</th>
								    <th class="center" style="width: 10%;">状态</th>
								    <th class="center" style="width: 15%;">虚拟机配置</th>
								    <th class="center" style="width: 10%;">平台</th>
									<th class="center" style="width: 10%;">部门</th>
									<th class="center" style="width: 10%;">项目</th>
									<th class="center" style="width: 10%;">申请人</th>
									<th class="center" style="width: 10%;">申请时间</th>
									<th class="center" style="width: 10%;">到期时间</th>
								</tr>
							</thead>
							<tbody>
								
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>${vs.index+1}</td>
											<td class="center">${var.vm_name}</td>
											<td class="center">
												<c:choose>
													<c:when test="${var.status == 0}">运行中</c:when>
													<c:when test="${var.status == 1}">挂起</c:when>
													<c:when test="${var.status == 2}">关机</c:when>
													<c:otherwise>未知</c:otherwise>
												</c:choose>
											</td>
											<td class="center">${var.cpu}C/${var.memory}G/${var.datadisk}G/</td>
											<td class="center">${var.type}</td>
											<td class="center">${var.DEPARTMENT_NAME }</td>
											<td class="center">${var.project_name }</td>
											<td class="center">${var.user}</td>
											<td class="center">${var.gmt_create}</td>
											<td class="center">${var.duedate}</td>
										</tr>
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="10" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</form>
	
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	</body>

<script type="text/javascript">
$(top.hangge());

//检索
function searchs(){
	top.jzts();
	$("#Form").submit();
}

//下拉树
var defaultNodes = {"treeNodes":${zTreeNodes}};
function initComplete(){
	//绑定change事件
	$("#selectTree").bind("change",function(){
		if(!$(this).attr("relValue")){
	      //  top.Dialog.alert("没有选择节点");
	    }else{
			//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
			$("#DEPARTMENT_ID").val($(this).attr("relValue"));
	    }
	});
	//赋给data属性
	$("#selectTree").data("data",defaultNodes);  
	$("#selectTree").render();
	$("#selectTree2_input").val("${null==depname?'请选择':depname}");
}

$(function() {
	//日期框
	$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
	
	//下拉框
	if(!ace.vars['touch']) {
		$('.chosen-select').chosen({allow_single_deselect:true}); 
		$(window)
		.off('resize.chosen')
		.on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': $this.parent().width()});
			});
		}).trigger('resize.chosen');
		$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
			if(event_name != 'sidebar_collapsed') return;
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': $this.parent().width()});
			});
		});
		$('#chosen-multiple-style .btn').on('click', function(e){
			var target = $(this).find('input[type=radio]');
			var which = parseInt(target.val());
			if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
			 else $('#form-field-select-4').removeClass('tag-input-style');
		});
	}

	//复选框全选控制
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

//导出excel
function toExcel(){
	var keywords = $("#nav-search-input").val();
	var lastLoginStart = $("#lastLoginStart").val();
	var lastLoginEnd = $("#lastLoginEnd").val();
	var ROLE_ID = $("#role_id").val();
	window.location.href='<%=basePath%>user/excel.do?keywords='+keywords+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
}
	
</script>
</html>
