<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../../system/index/top.jsp"%>
	<link rel="stylesheet" href="css/newSkin.css">
</head>
<body class="no-skin control-list">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<form action="cloudhost/list.do" method="post" name="Form" id="Form">
								<input type="hidden" name="TYPE" value="${pd.TYPE}" />
								<c:if test="${QX == 1}">
								<table style="margin-top:5px;">
									<tr>
										<c:if test="${BQX == 1}">
											<td style="padding-left:6px;"><a class="btn btn-primary btn-sm" onclick="apply()">申请</a></td>
										</c:if>
										<td style="padding-left:6px;"><a class="btn btn-success btn-sm" onclick="start()">开机</a></td>
										<td style="padding-left:6px;"><a class="btn btn-success2 btn-sm" onclick="resume()">恢复</a></td>
										<td style="padding-left:6px;"><a class="btn btn-warning btn-sm" onclick="suspend()">挂起</a></td>
										<td style="padding-left:6px;"><a class="btn btn-danger2  btn-sm" onclick="reboot()">重启</a></td>
										<td style="padding-left:6px;"><a class="btn btn-danger  btn-sm" onclick="stop()">关机</a></td>
										<td style="padding-left:6px;">
											<div class="btn-group" style="height: 33px">
												<button type="button" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
													更多操作<span class="caret"></span>
												</button>
												<%--<a class="btn btn-primary btn-sm">更多操作</a>
												<a class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
													<span class="caret"></span>
													<!-- <span class="sr-only">Toggle Dropdown</span> -->
												</a>--%>
												<ul class="dropdown-menu">
													<c:if test="${BQX == 1}">
														<li><a onclick="expandCompute()">计算扩容</a></li>
														<li><a onclick="expandStorage()">磁盘扩容</a></li>
													</c:if>
													<li><a onclick="clone()">克隆</a></li>
													<li><a onclick="snapshot()">快照</a></li>
													<li><a onclick="destory()">删除</a></li>
												</ul>
											</div>
										</td>
										<td style="padding-left:6px;">
											<div class="nav-search">
												<span class="input-icon">
													<input type="text" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
										</td>
										<td style="vertical-align: top; padding-left: 2px; width: 32px;">
											<a class="btn btn-light btn-xs" onclick="searchs();" title="检索">
												<i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>
											</a>
										</td>
									</tr>
								</table>
								</c:if>
								<table id="datagrid" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
									<thead>
										<tr>
											<th class="center" style="width:35px;">
												<label class="pos-rel">
													<input type="checkbox" class="ace" id="zcheckbox" />
													<span class="lbl"></span>
												</label>
											</th>
											<th class="center">名称</th>
											<th class="center">IP</th>
											<th class="center">CPU</th>
											<th class="center">内存</th>
											<th class="center">磁盘</th>
											<th class="center">OS</th>
											<th class="center">状态</th>
											<th class="center">所属平台</th>
											<th class="center">到期时间</th>
											<th class="center">远程控制台</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${varList}" var="var" varStatus="vs">
											<tr>
												<td class='center'>
													<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
												</td>
												<td class='center'><a>${var.name}</a></td>
												<td class='center'>${var.ip}</td>
												<td class='center'>${var.cpu}</td>
												<td class='center'>
													<c:if test="${not empty var.memory}">
														${var.memory}G
													</c:if>
												</td>
												<td class='center'>
													<c:if test="${not empty var.datadisk}">
														${var.datadisk}G
													</c:if>
												</td>
												<td class='center'>${var.os}</td>
												<td class='center'>
													<c:choose>
													    <c:when test="${var.status == 0}">
															运行中
													    </c:when>
													    <c:when test="${var.status == 1}">
															挂起
													    </c:when>
													    <c:when test="${var.status == 2}">
															关机
													    </c:when>
													    <c:otherwise>
															未知
													    </c:otherwise>
													</c:choose>
												</td>
												<td class='center'>${var.platform}</td>
												<td class='center'>${var.duedate}</td>
												<td class='center'><a target="_blank" href="cloudhost/console?id=${var.id}">SSH</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0;margin-top: 0;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

		<div class="modal fade" id="platform_modal" tabindex="-1" role="dialog" aria-labelledby="platform_modalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="middleware_modalLabel">
							克隆虚拟机
						</h4>
					</div>
					<div class="modal-body">
						<input type="hidden"   name="h_appNo" id="h_appNo">
						<input type="hidden"   name="h_cpu" id="h_cpu">
						<input type="hidden"   name="h_memory" id="h_memory">
						<input type="hidden"   name="h_diskSize" id="h_diskSize">
						<table  style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;宿主机</td>
								<td>
									<select name="host" style="width:60%;margin-left: 50px;">
										<option value="#" selected="selected">请选择宿主机</option>
										<c:forEach items="${hostList}" var="var">
											<option value="${var.id}">${var.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;名称:</td>
								<td>
									<input class="textbox" type="text" name="vname">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;CPU:</td>
								<td>
									<input class="textbox" type="number" name="vcpus">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;内存:</td>
								<td>
									<input class="textbox" type="number" name="vmemory">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;磁盘:</td>
								<td>
									<input class="textbox" type="number" name="vdisk">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;镜像:</td>
								<td>
									<select name="vimage" style="width:60%;margin-left: 50px;">
										<option value="#" selected="selected">请选择镜像</option>
										<option value="/root/CentOS-6-x86_64-GenericCloud-1710.qcow2">
											CentOS-6-x86_64-GenericCloud-1710.qcow2
										</option>
									</select>
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button id="platform_modal_btn_cancel" type="button" class="btn btn-default"  data-dismiss="modal">取消</button>
						<button id="platform_modal_btn_ok" type="button" class="btn btn-primary" onclick="createVm()">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../../system/index/foot.jsp"%>
	
	<script src="static/ace/js/bootbox.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/ace/js/chosen.jquery.js"></script>
	<script src="static/js/jquery.tips.js"></script>
	<script src="static/js/myjs/head.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		
		$(function() {
			//复选框全选控制
			var active_class = 'active';
			$('#datagrid > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		function getSelected() {
            var selection = $('#datagrid').find('input:checked').map(function() { return $(this).val(); }).get();
            if (selection.length <= 0) {
                bootbox.alert("请选择虚拟机");
                throw "Error: no vm selected";
			}

            return selection;
		}

		function apply() {
			siMenu('122', '121', '资源申请', 'resAppPre.do');
		}
		
		function start() {
			$.post('cloudhost/start', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
		
		function stop() {
			$.post('cloudhost/stop', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
		
		function reboot() {
			$.post('cloudhost/reboot', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
		
		function suspend() {
			$.post('cloudhost/suspend', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
		
		function resume() {
			$.post('cloudhost/resume', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
		
		function expandCompute() {
			var selection = getSelected();
			if (selection.length > 0) {
				siMenu('123', '121', '计算扩容', 'reqOperServicePre.do?serviceType=6&vmId=' + selection[0]);
			}
		}
		
		function expandStorage() {
			var selection = getSelected();
			if (selection.length > 0) {
				siMenu('123', '121', '磁盘扩容', 'reqOperServicePre.do?serviceType=6&vmId=' + selection[0]);
			}
		}
		
		function clone() {
			$.post('cloudhost/clone', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
		
		function snapshot() {
			$.post('cloudhost/snapshot', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
		
		function destory() {
			$.post('cloudhost/destory', { ls: getSelected() }, function(result) {
				nextPage(${page.currentPage});
				consol.log(result);
			});
		}
	</script>

</body>
</html>