<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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

	<div class="page-header">
	<button class="btn btn-success" id="cancel" name="cancel" type="reset"
		onclick="top.Dialog.close();">返回</button>
	<small>
		<button class="btn disabled btn-success">工单号：${workorder.appNo}</button>
	</small>

	</div>
	<!-- /.page-header -->
	<div class="alert alert-info">流程图</div>
	<p></p>

	<div>
		<img id="procImg"
			src="<c:url value='findworkflowPic.do?procDefId=${procDefId}' />" />
		<c:if test="${workorder.appNo != '5'}">
			<!-- 给执行的节点加框 -->
			<div
				style="position: absolute; border:2px solid red;left:${workorderImag.x}px;top:${workorderImag.y + 153}px;width:${workorderImag.width }px;height:${workorderImag.height }px;"></div>
		</c:if>
	</div>

	<div class="alert alert-info">申请工单详情</div>
	<div>
					<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">实例</th>
										<th class="center">类型</th>
										<th class="center">版本</th>
										<th class="center">CPU/内存</th>
										<th class="center">存储</th>
										<th class="center">访问地址</th>
										<th class="center">端口</th>
										<th class="center">操作</th>
									</tr>
								</thead>

								<tbody>
												<tr>
													<td class='center'></td>
													<td class='center'>虚拟机</td>
													<td class='center'></td>
													<td class='center'>
														${cmpCloudInfo.cpu}核/${cmpCloudInfo.memory}G
													</td>
													<td class='center'>${cmpCloudInfo.dataDiskInfo}</td>
													<td class='center'></td>
													<td class='center'></td>
													<td class='center'>
													<button  style="float:left;margin-left: 100px;" type="button" 
														onclick="execute('${workorder.appNo}','${cmpCloudInfo.cpu}','${cmpCloudInfo.memory}','${cloudInfoCollect.diskTotal}');">安装</button>
													</td>
													</tr>
								</tbody>
						</table>
							<div class="alert alert-info">部署执行详情</div>
							<div id="shell_msg_div" style="height: 150px; width: 100%; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;"></div>
						
						<div class="alert alert-info">服务目录</div>
						<div>
						<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<tbody>
									<thead>
										<tr>
											<th class='center'>云资源申请总览</th>
										</tr>
									</thead>
									
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">申请云主机数量</td>
										<td class='center'>${cloudInfoCollect.vmCount}台</td>
										<td class='center' style="background-color: rgb(244,244,244);">cpu/内存总量</td>
										<td class='center'>${cloudInfoCollect.cpuTotal}核/${cloudInfoCollect.memoryTotal}G</td>
										<td class='center' style="background-color: rgb(244,244,244);">数据盘总量</td>
										<td class='center'>${cloudInfoCollect.diskTotal}G</td>
									</tr>
								</tbody>
						</table>
						<h2></h2>
						<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<tbody>
									<thead>
										<tr>
											<th class='center'>申请详情</th>
										</tr>
									</thead>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">操作系统</td>
										<td class='center'>${cmpCloudInfo.osTypeName}</td>
										<td class='center' style="background-color: rgb(244,244,244);">系统盘/数据盘</td>
										<td class='center'>系统盘${cmpCloudInfo.sysDiskSize}G; 数据盘${cloudInfoCollect.diskTotal}G</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">CPU/内存</td>
										<td class='center'>${cmpCloudInfo.cpu}核/${cmpCloudInfo.memory}G</td>
										<td class='center' style="background-color: rgb(244,244,244);">安装状态</td>
										<td class='center'>${cmpCloudInfo.osStatus}</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">软件安装</td>
										<td class='center'>${cmpCloudInfo.softStatus}</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">数据盘信息</td>
										<td class='center'>${cmpCloudInfo.dataDiskInfo}</td>
										<td class='center' style="background-color: rgb(244,244,244);">使用期限</td>
										<td class='center'>${cmpCloudInfo.expireDate}</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">电子钥匙</td>
										<td class='center'></td>
									</tr>
								</tbody>
						</table>
			<div class="alert alert-info">后续任务处理</div>
		<table>
			<tr>
			<td align="right" style="width: 120px;padding:10px;">批复：</td>
			<td align="left" style="padding:10px;" width="90%">
				<textarea class="form-control limited" id="comment" name="comment" style="width: 100%"></textarea>
			</td>
		</tr>
		</table>
		<form class="form-horizontal" id="checkform" name="checkform"
			role="form">
		<table style="margin-left: 30%">
			<tr>
					<td>
				<button class="btn btn-info" id="OK" name="OK" type="button"
					onclick="doExecute('${workorder.appNo}');">
					<i class="ace-icon fa fa-check bigger-110"></i> 确定执行
				</button>
				</td>
					<td style="padding-left: 20px">
				<button class="btn" id="cancel" name="cancel" type="reset"
					onclick="top.Dialog.close();">
					<i class="ace-icon fa fa-undo bigger-110"></i> 取消
				</button>
				</td>
				</tr>
		</table>
		</form>
	</div>


	<div class="alert alert-info">关联任务</div>
	<div>
		<table id="simple-table"
			class="table table-striped table-bordered table-hover"
			style="margin-top: 5px;">
			<thead>
				<tr>
					<th class="center">任务单号</th>
					<th class="center">任务处理人</th>
					<th class="center">任务状态</th>
					<th class="center">处理结果</th>
					<th class="center">处理时间</th>
				</tr>
			</thead>

			<tbody>
				<!-- 开始循环 -->
				<c:choose>
					<c:when test="${not empty relateTaskList}">
						<c:forEach items="${relateTaskList}" var="var" varStatus="vs">
							<tr>
								<td class='center'>${var.taskNo}</td>
								<td class='center'>${var.assignee}</td>
								<td class='center'>${var.status}</td>
								<td class='center'>${var.result}</td>
								<td class='center'>${var.time}</td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>

	</div>
	<div class="alert alert-info">历史批复</div>
						<div>
						<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center" width="20%">批复人</th>
										<th class="center" width="80%">批复信息</th>
									</tr>
								</thead>

								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty commentList}">
											<c:forEach items="${commentList}" var="var" varStatus="vs">
												<tr>
													<td class='center' width="20%">${var.userId}</td>
													<td class='center' width="80%">${var.fullMessage}</td>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
								</tbody>
	</table>
	</div>
	<!-- 返回顶部 -->
	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-sm btn-inverse"> <i
		class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
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
		$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
			var th_checked = this.checked;//checkbox inside "TH" table header
			$(this).closest('table').find('tbody > tr').each(function(){
				var row = this;
				if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
				else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
			});
		});
	});
	
	
	function doExecute(appNo){
		top.jzts();
		var comment = $("#comment").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>doExecute.do?appNo='+appNo +'&comment=' + comment,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				window.top.queryPersonalTask();
				showDialog(data.result);
			}
		});
	}
	
	function execute(appNo, cpu, memory, diskSize){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>executeWork.do?appNo='+appNo +'&CPU=' + cpu +'&memory=' + memory + '&diskSize=' + diskSize,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				showDialog(data.result);
			}
		});
		
		
		//查询，并同步更新控制台
		var line = 0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>queryShell.do?shellId='+appNo +'&currentLine=' + line,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				var shellMsgMap = data.shellMsgMap;
				if (shellMsgMap[line] != null && shellMsgMap[line] == "-1"){
					return 0;
				}
				$('#shell_msg_div').append('<p>'+shellMsgMap[line]+'</p>'); 
				var res = setInterval(() => {
					line++;
					$.ajax({
						type: "POST",
						url: '<%=basePath%>queryShell.do?shellId='+appNo +'&currentLine=' + line,
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							var shellMsgMap = data.shellMsgMap;
							if (shellMsgMap[line] != null && shellMsgMap[line] == "-1"){
								clearInterval(t);
							}else{
								$('#shell_msg_div').append('<p>'+shellMsgMap[line]+'</p>'); 
							}
						}
					});
				}, 200);
			}
		});
		
		
	}
	</script>

</body>
</html>