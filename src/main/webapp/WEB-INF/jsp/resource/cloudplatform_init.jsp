<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<link href="static/login/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/ace/css/font-awesome.css" />
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />  
	<link href="css/bwizard.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/ace/css/ace-fonts.css" />
	<!-- <link rel="stylesheet" href="static/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" /> -->
	<!-- <link rel="stylesheet" href="static/ace/css/ace-skins.css" class="ace-main-stylesheet" id="ace-skins-stylesheet" /> -->
	<!-- <script src="static/ace/js/ace-extra.js"></script> -->
	<style>
	body{padding-top: 10px;}
	.next {
		padding-left: 10px;
		float: left;
	}
	</style>
</head>
<body>
	<div class="container">
		<div id="wizard" style="text-align:left">
			<ol>
				<li>数据中心</li>
				<li>集群</li>
				<li>宿主机</li>
				<li>存储</li>
				<li>网络</li>
			</ol>
			<div>
					<form action="" method="post" name="datacenterForm" id="datacenterForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">名称</th>
									<th class="center">所属云平台</th>
									<th class="center">创建时间</th>
									<th class="center">修改时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty datacenterList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${datacenterList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.cpf_name}</td>
											<td class='center' style="width: 170px;">${var.gmt_create}</td>
											<td class='center' style="width: 170px;">${var.gmt_modified}</td>
											<td class="center">
												<!-- Todo -->
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-sm btn-success" onclick="startusing();">启用</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
			</div>
			<div>
				<form action="" method="post" name="clusterForm" id="clusterForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">名称</th>
									<th class="center">所属数据中心</th>
									<th class="center">创建时间</th>
									<th class="center">修改时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty clusterList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${clusterList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.dc_name}</td>
											<td class='center' style="width: 170px;">${var.gmt_create}</td>
											<td class='center' style="width: 170px;">${var.gmt_modified}</td>
											<td class="center">
												<!-- Todo -->
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-sm btn-success" onclick="startusing();">启用</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
				</form>
			</div>
			<div>
				<form action="" method="post" name="hostmachineForm" id="hostmachineForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">宿主机名称</th>
									<th class="center">IP地址</th>
									<th class="center">所属集群</th>
									<th class="center">CPU</th>
									<th class="center">内存</th>
									<th class="center">磁盘</th>
									<th class="center">虚拟机数量</th>
									<th class="center">创建时间</th>
									<th class="center">修改时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty hmList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${hmList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.ip}</td>
											<td class='center'>${var.cluster_name}</td>
											<td class='center'>${var.cpu}</td>
											<td class='center'>${var.memory}</td>
											<td class='center'>${var.localdisk}</td>
											<td class='center'>${var.devicenum}</td>
											<td class='center' style="width: 170px;">${var.gmt_create}</td>
											<td class='center' style="width: 170px;">${var.gmt_modified}</td>
											<td class="center">
												<!-- Todo -->
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-sm btn-success" onclick="startusing();">启用</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
				</form>
			</div>
			<div>
				<form action="" method="post" name="storageForm" id="storageForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">名称</th>
									<th class="center">存储类型</th>
									<th class="center">空间总量</th>
									<th class="center">空闲空间</th>
									<th class="center">剩余空间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty storageList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${storageList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.type}</td>
											<td class='center'>${var.allspace}</td>
											<td class='center'>${var.freespace}</td>
											<td class='center'>${var.freespace}/${var.allspace}</td>
											<td class="center">
												<!-- Todo -->
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-sm btn-success" onclick="startusing();">启用</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
						</form>
			</div>
			<div>
				<form action="" method="post" name="dcnForm" id="dcnForm">
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">名称</th>
									<th class="center">所属数据中心</th>
									<th class="center">ip池</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty dcnList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${dcnList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.name}</td>
											<td class='center'>${var.dc_name}</td>
											<td class='center'>${var.ippool}</td>
											<td class="center">
												<!-- Todo -->
											</td>
										</tr>
									
									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-sm btn-success" onclick="startusing();">启用</a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
					</form>
			</div>
		</div>
	</div>

	<script src="plugins/codeEditor/jquery.min.js"></script>
	<script src="static/login/js/bootstrap.min.js"></script>
	<script src="js/bwizard.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(top.hangge());	
	   	$("#wizard").bwizard();
	   	
	   	$(function() {
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
	   	
		//启动
		function startusing() {
			 
		}
	</script>
</body>
</html>
