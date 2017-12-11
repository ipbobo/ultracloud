<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>
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
						<form action="process/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入名称" class="nav-search-input" id="nav-search-input" autocomplete="off" name="name" value="${pd.name }" placeholder="这里输入名称"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th style="width:7%"  class="center ">版本号</th>
									<th style="width:10%" class="center hidden-480">流程定义ID</th>
									<th style="width:10%" class="center hidden-480">名称</th>
									<th style="width:8%"  class="center hidden-480">部署ID</th>
									<th style="width:10%" class="center">KEY</th>
									<th style="width:13%" class="center hidden-480">流程描述XML文件</th>
									<th style="width:13%" class="center hidden-480">流程图</th>
									<th style="width:10%" class="center hidden-480">部署时间</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class="center">${var.version}</td>
											<td class='center'>${var.id}</td>
											<td class="center">${var.name}</td> 
											<td class='center'>${var.deploymentId}</td>
											<td class="center">${var.key}</td> 
											<td class="center">${var.resourceName}</td>
											<td class="center"><a href="javascritp:void(0);" onclick="currentType('image','${var.id}');return false" >${var.diagramResourceName}</a></td>
											<td class='center'>${var.deploymentTime}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- layer弹窗插件-->
	<script src="plugins/layer/3.0/layer.js"></script>
	<!--弹窗组件end-->
	<script type="text/javascript">
	$(top.hangge());//关闭加载状态
	//检索
	function tosearch(){
		top.jzts();
		$("#Form").submit();
	}
	
	//工作流程图
	function currentType(type,pdId){
		var width=document.documentElement.clientWidth * 0.85+"px";
		var height=document.documentElement.clientHeight * 0.85+"px";
		layer.open({
		    type: 2,
		    title: "流程【"+type+"】",
		    shadeClose: true,
		    maxmin: true,
		    area: [width, height],
		    content: "<%=basePath%>process/resource/read?resourceType="+type+"&processDefinitionId="+pdId
		});
	}
	</script>

</body>
</html>