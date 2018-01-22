<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<form action="cloudhost/list.do" method="post" name="Form" id="Form">
								<input type="hidden" name="TYPE" value="${pd.TYPE}" />
								<table style="margin-top:5px;">
									<tr>
										<td style="padding-left:6px;"><a class="btn btn-primary btn-sm" onclick="apply()">申请</a></td>
										<td style="padding-left:6px;"><a class="btn btn-success btn-sm" onclick="start()">开机</a></td>
										<td style="padding-left:6px;"><a class="btn btn-danger  btn-sm" onclick="stop()">关机</a></td>
										<td style="padding-left:6px;"><a class="btn btn-danger  btn-sm" onclick="restart()">重启</a></td>
										<td style="padding-left:6px;"><a class="btn btn-warning btn-sm" onclick="suspend()">挂起</a></td>
										<td style="padding-left:6px;"><a class="btn btn-success btn-sm" onclick="resume()">恢复</a></td>
										<td style="padding-left:6px;">
											<div class="btn-group">
												<a class="btn btn-primary btn-sm">更多操作</a>
												<a class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
													<span class="caret"></span>
													<!-- <span class="sr-only">Toggle Dropdown</span> -->
												</a>
												<ul class="dropdown-menu">
													<li><a onclick="expandCompute()">计算扩容</a></li>
													<li><a onclick="expandStorage()">磁盘扩容</a></li>
													<li><a onclick="clone()">克隆</a></li>
													<li><a onclick="snapshot()">快照</a></li>
													<li><a onclick="destory()">删除</a></li>
												</ul>
											</div>
										</td>
										<td style="padding-left:6px;">
											<div class="nav-search">
												<span class="input-icon">
													<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
										</td>
									</tr>
								</table>
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
												<td class='center'><a>SSH</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
	</div>

	<%@ include file="../../system/index/foot.jsp"%>
	
	<script src="static/ace/js/bootbox.js"></script>
	<script src="static/ace/js/ace/ace.js"></script>
	<script src="static/ace/js/chosen.jquery.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
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
		
		<%-- //删除
		function del(ztid, STATUS, type, Id, SANME_ID){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(type == "1" && STATUS == '2' && $("#"+ztid).html() == '<span class="label label-important arrowed-in">未读</span>'){
					top.readFhsms();//读取站内信时减少未读总数  <!-- readFhsms()函数在 WebRoot\static\js\myjs\head.js中 -->
				}
				if(result) {
					top.jzts();
					var url = "<%=basePath%>fhsms/delete.do?FHSMS_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		} --%>
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var username = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  	
					  	if(username=='') username += document.getElementsByName('ids')[i].id;
					  	else username += ';' + document.getElementsByName('ids')[i].id;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>fhsms/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});//刷新当前页面
											top.getFhsmsCount();//更新未读站内信
									 });
								}
							});
						}else if(msg == '确定要给选中的用户发送站内信吗?'){
							sendFhsms(username);
						}
					}
				}
			});
		};
		
		function getSelected() {
			return $('#datagrid').find('input:checked').map(function() { return $(this).val(); }).get();
		}
		
		function apply() {
			siMenu('122', '121', '资源申请', 'resAppPre.do');
		}
		
		function start() {
			$.post('cloudhost/start', { ls: getSelected() }, function(result) {
				consol.log(result);
			});
		}
		
		function stop() {
			$.post('cloudhost/stop', { ls: getSelected() }, function(result) {
				consol.log(result);
			});
		}
		
		function restart() {
			$.post('cloudhost/restart', { ls: getSelected() }, function(result) {
				consol.log(result);
			});
		}
		
		function suspend() {
			$.post('cloudhost/suspend', { ls: getSelected() }, function(result) {
				consol.log(result);
			});
		}
		
		function resume() {
			$.post('cloudhost/resume', { ls: getSelected() }, function(result) {
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
				consol.log(result);
			});
		}
		
		function snapshot() {
			$.post('cloudhost/snapshot', { ls: getSelected() }, function(result) {
				consol.log(result);
			});
		}
		
		function destory() {
			$.post('cloudhost/destory', { ls: getSelected() }, function(result) {
				consol.log(result);
			});
		} 
	</script>

</body>
</html>