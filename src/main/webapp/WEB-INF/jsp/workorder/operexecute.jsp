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
	<input type="hidden"   name="h_appNo" id="h_appNo"  value="${workorder.appNo}">
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
		<jsp:include page="operview_pv.jsp"></jsp:include>
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
			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info" id="OK" name="OK" type="button" onclick="doExecute('${workorder.appNo}');">
						<i class="ace-icon fa fa-check bigger-110"></i>
						确定
					</button>

					&nbsp; &nbsp; &nbsp;
					<button class="btn" id="cancel" name="cancel" type="reset" onclick="top.Dialog.close();">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						取消
					</button>
				</div>
			</div>
		</form>
	</div>
	<div class="alert alert-info">运维执行详情</div>
	<div id="shell_msg_div" style="height: 150px; width: 100%; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;"></div>


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
			
						<div class="modal fade" id="breakdown_info_modal" tabindex="-1" role="dialog" aria-labelledby="breakdown_info_modalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
											&times;
										</button>
										<h4 class="modal-title">
											故障详情
										</h4>
									</div>
									<div class="modal-body">
							<table  style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
							<tr class="tablecls">
								<td style="width:70px;text-align: right;padding-top: 13px;">&nbsp;&nbsp;故障描述:</td>
									<td>
										<textarea class="form-control limited" name="breakdown_info_show" id="breakdown_info_show" style="width: 70%; height:150px; margin-left: 30px;" readonly="readonly">
											${opServe.breakdownInfo}
										</textarea>
									</td>
							</tr>
							<tr>
								<td style="width:70px;text-align: right;padding-top: 13px;">&nbsp;&nbsp;期望结果:</td>
									<td>
										<textarea class="form-control limited"  name="except_result" id="except_result" style="width: 70%; height:150px; margin-left: 30px;"  readonly="readonly">
											${opServe.exceptResult}
										</textarea>
									</td>
									</tr>
									</table>
										
									</div>
									<div class="modal-footer">
										<button  id="middleware_btn_ok" type="button" class="btn btn-primary" onclick="breakdown_info_OK();" id="middleware_modal_ok_btn">
											确定
										</button>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>
						
						
						<div class="modal fade" id="partition_info_modal" tabindex="-1" role="dialog" aria-labelledby="partition_info_modalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
											&times;
										</button>
										<h4 class="modal-title">
											故障详情
										</h4>
									</div>
									<div class="modal-body">
							<table  style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
							<tr class="tablecls">
								<td style="width:70px;text-align: right;padding-top: 13px;">&nbsp;&nbsp;分区数量及大小:</td>
									<td>
										<textarea class="form-control limited" name="partition_info_show" id="partition_info_show" style="width: 70%; height:150px; margin-left: 30px;" readonly="readonly">
											${opServe.partitionInfo}
										</textarea>
									</td>
							</tr>
							<tr>
									</table>
										
									</div>
									<div class="modal-footer">
										<button  id="middleware_btn_ok" type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
											确定
										</button>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>
						
						<div class="modal fade" id="reboot_modal" tabindex="-1" role="dialog" aria-labelledby="reboot_modalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
											&times;
										</button>
										<h4 class="modal-title">
											中间件重启
										</h4>
									</div>
									<div class="modal-body">
											<input  type="hidden" id="rebootSoftId" />
											<input  type="hidden" id="scriptId" />
											<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
												<thead>
													<tr>
														<th class="center" style="width: 35px;"><label
															class="pos-rel"><input type="checkbox" class="ace"
																id="zcheckbox" /><span class="lbl"></span></label></th>
														<th class="center">脚本名</th>
														<th class="center">脚本路径</th>
														<th class="center">脚本描述</th>
														<th class="center">参数</th>
													</tr>
												</thead>
												
													<c:choose>
														<c:when test="${not empty scriptList}">
															<c:forEach items="${scriptList}" var="var" varStatus="vs">
																<tr>
															<td class='center'><label class="pos-rel"><input
																			type='checkbox' id='scriptcheckbox_${var.id}' readonly="readonly" name='vmcheckbox' value="${var.id}" class="ace" /><span
																			class="lbl"></span></label></td>
																		<td class='center'>${var.name}</td>
																		<td class='center'>${var.url}</td>
																		<td class='center'>${var.purpose}</td>
																		<td class='center'><a onclick="showSetparams('${var.id}');">设置参数</a></td>
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
									</div>
									<div class="modal-footer">
										<button  id="reboot_modal_ok" type="button" class="btn btn-danger" onclick="doReboot();" data-dismiss="modal" aria-hidden="true">
											确定
										</button>
										<button  id="reboot_modal_cancel" type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
											取消
										</button>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>
						
						<div class="modal fade" id="reboot_params_modal" tabindex="-1" role="dialog" aria-labelledby="reboot_params_modalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
											&times;
										</button>
										<h4 class="modal-title">
											脚本参数
										</h4>
									</div>
									<div class="modal-body">
											<table id="paramsTable" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
												<thead>
													<tr>
														<th class="center">参数名称</th>
														<th class="center">参数值</th>
													</tr>
												</thead>
											</table>
									</div>
									<div class="modal-footer">
										<button  id="reboot_params_modal_ok" onclick="setParams();" type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
											确定
										</button>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
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
	
	$( document ).ready(function() {
		var executeStatus = $("#executeStatus").val();
		var appNo = $("#h_appNo").val();
	    if (executeStatus == null || executeStatus == '' || executeStatus == '0'){
	    	executeStatus = '0';
	    }
	    $("#executeStatus_" + executeStatus).css('display','block');
	    if (executeStatus == null || executeStatus == '' || executeStatus == '1'){
	    	queryExecuteStatus(appNo);
	    }
	});
	
	
	function queryExecuteStatus(appNo){
		//查询，并同步更新控制台
		var line = 0;
		var res = setInterval(function(){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>queryShell.do?shellId='+appNo +'&currentLine=' + line,
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					var maplen = data.length;
					var shellMsgMap = data.currentShellMsg;
					if (shellMsgMap == null)
						return;
					if (shellMsgMap[maplen] != null && shellMsgMap[maplen] == "cmp:install finished"){
						$("#executeStatus_1").css('display','none');
						$("#executeStatus_2").css('display','block');
						clearInterval(res);
					}
					for (var i = line; i <maplen; i++ ){
							if (shellMsgMap[i] != null && shellMsgMap[i] != "-1" && shellMsgMap[i] != "undefined"){
								$('#shell_msg_div').append(shellMsgMap[i]+'<br\>'); 
								document.getElementById('shell_msg_div').scrollTop = document.getElementById('shell_msg_div').scrollHeight
							}
					}
					line = maplen;
				}
			});
		}, 2500);
	}
	
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
	
	function showRebootSoft(deploySoftId){
		$('#rebootSoftId').val(deploySoftId);
		$('#reboot_modal').modal('show');
	}
	
	function showSetparams(scriptId){
		$('#scriptId').val(scriptId);
		$.ajax({
			type: "POST",
			url: '<%=basePath%>queryScriptParams.do?scriptId='+scriptId,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				 var paramsTable = $("#paramsTable");
				 for(var i=0;i<data.length;i++){  
					 paramsTable.append("<tr><td class='center'>" + data[i].name + "</td><td class='center'><input name='paramInput' value='"+ data[i].value +"' /></td></tr>");
			    }
			}
		});
		$('#reboot_modal').modal('hide');
	    $('#reboot_params_modal').modal('show');
	}
	
	function setParams(){
		var scriptId = $('#scriptId').val();
		if (scriptId == null || scriptId == ''){
			 showDialog("请先选择脚本，并设置脚本参数!");
		}
		document.getElementById("scriptcheckbox_"+scriptId).checked = true;
		$('#reboot_params_modal').modal('hide');
		$('#reboot_modal').modal('show');
	}
	
	
	function doReboot(){
		var params = '';
		 $('input[name="paramInput"]').each(function(){     
			 params=params+ $(this).val() + ' ';     
		 });
		 if (params == ''){
			 showDialog("请先选择脚本，并设置脚本参数!");
			 return false;
		 }
		 var scriptId = $('#scriptId').val();
		 var deploySoftId = $('#rebootSoftId').val();
		 var appNo = $("#h_appNo").val();
		 queryExecuteStatus(appNo);
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>doRebootSoft.do?appNo='+ appNo + '&scriptId='+scriptId + '&deploySoftId=' + deploySoftId + '&params=' + params,
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if (data != null && data != 'success'){
						 showDialog(data);
					}
				}
		});
	}
	
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
	
	function breakdown_info_OK(){
		$('#breakdown_info_modal').modal('hide');
	}
	</script>

</body>
</html>