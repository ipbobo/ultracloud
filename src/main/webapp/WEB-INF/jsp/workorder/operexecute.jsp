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
			<c:if test="${workorder.uploadFileName != ''}">
				<tr>
					<td align="right" style="width: 120px;padding:10px;">附件信息：</td>
					<td align="left" style="padding:10px;" width="90%">
						<a href="static/upload/${workorder.uploadFileName}" download="${workorder.uploadFileName}">${workorder.uploadFileName}</a>
					</td>
				</tr>
			</c:if>
			<tr>
				<td align="right" style="width: 120px;padding:10px;">退回：</td>
				<td align="left" style="padding:10px;" width="90%">
					<input type="checkbox" name="rejectFlag" id="rejectFlag" value="0">
				</td>
			</tr>
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
						
						<div class="modal fade" id="reboot_modal" tabindex="-1" role="dialog" style="overflow: auto" aria-labelledby="reboot_modalLabel" aria-hidden="true">
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
											<input  type="hidden" id="rebootSoftIds" />
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
																			type='checkbox' id='scriptcheckbox_reboot_${var.id}' readonly="readonly" name='vmcheckbox' value="${var.id}" class="ace" /><span
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
						
						<div class="modal fade" id="installSoft_modal" tabindex="-1" role="dialog" style="overflow: auto" aria-labelledby="installSoft_modalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
											&times;
										</button>
										<h4 class="modal-title">
											软件安装
										</h4>
									</div>
									<div class="modal-body">
											<input  type="hidden" id="installSoftIds" />
											<input  type="hidden" id="installScriptId" />
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
																			type='checkbox' id='scriptcheckbox_install_${var.id}' readonly="readonly" name='vmcheckbox' value="${var.id}" class="ace" /><span
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
										<button  id="install_modal_ok" type="button" class="btn btn-danger" onclick="doInstall();" data-dismiss="modal" aria-hidden="true">
											确定
										</button>
										<button  id="install_modal_cancel" type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
											取消
										</button>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>
						
						<div class="modal fade" id="script_params_modal" tabindex="-1" role="dialog" style="overflow: auto" aria-labelledby="script_params_modalLabel" aria-hidden="true">
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
										<button  id="script_params_modal_ok" onclick="setParams();" type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
											确定
										</button>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>
						
						<div class="modal fade" id="mount_disk_modal" tabindex="-1" role="dialog" style="overflow: auto" aria-labelledby="mount_disk_modalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
											&times;
										</button>
										<h4 class="modal-title">
											指定目录下创建磁盘路径并挂载
										</h4>
									</div>
									<div class="modal-body">
											<table id="paramsTable" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
												<tr>
													<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;网络名称:</td>
													<td>
														<input type="text" class="form-control limited" name="netname" id="netname" style="width: 70%; margin-top:10px; margin-left: 50px;"></input>
													</td>
											    </tr>
											    <tr>
													<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;管理员状态:</td>
													<td>
														<select name="manager_status" id="manager_status" title="管理员状态" style="width:20%;  margin-top:10px; margin-left: 50px;">
															  <option value="up">up</option>
															  <option value="down">down</option>
														</select>
													</td>
												</tr>
												 <tr>
													<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;绑定路由器:</td>
													<td>
														<input type="checkbox" name="bind_router" id="bind_router" value="1" title="绑定路由器" style="width:20%;  margin-top:10px; margin-left: 50px;" />
													</td>
												</tr>
											</table>
									</div>
									<div class="modal-footer">
										<button  id="mound_disk_modal_ok" type="button" class="btn btn-danger" onclick="doMountDisk();" data-dismiss="modal" aria-hidden="true">
											确定
										</button>
										<button  id="mound_disk_modal_cancel" type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
											取消
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
		var res = setInterval(function(){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>queryShell.do?shellId='+appNo ,
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					var maplen = data.length;
					$('#shell_msg_div').empty();
					for (var msgIndex = 0; msgIndex < data.length; msgIndex++){
						if (data[msgIndex] == "cmp:success"){
							$("#executeStatus_1").css('display','none');
							$("#executeStatus_2").css('display','block');
							clearInterval(res);
							break;
						}
						$('#shell_msg_div').append(data[msgIndex]+'<br\>'); 
					}
					document.getElementById('shell_msg_div').scrollTop = document.getElementById('shell_msg_div').scrollHeight
				}
			});
		}, 1500);
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
	
	function showRebootSoft(rebootSoftIds){
		$('#rebootSoftIds').val(rebootSoftIds);
		$('#reboot_modal').modal('show');
	}
	
	function showInstallSoft(installSoftIds){
		$('#installSoftIds').val(installSoftIds);
		$('#installSoft_modal').modal('show');
	}
	
	function showMountDisk(){
		$('#mount_disk_modal').modal('show');
	}
	
	function showSetparams(scriptId){
		$('#installScriptId').val(scriptId);
		$.ajax({
			type: "POST",
			url: '<%=basePath%>queryScriptParams.do?scriptId='+scriptId,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				 var paramsTable = $("#paramsTable");
				 $("#paramsTable tr:gt(0)").remove();
				 for(var i=0;i<data.length;i++){  
					 paramsTable.append("<tr><td class='center'>" + data[i].name + "</td><td class='center'><input name='paramInput' value='"+ data[i].value +"' /></td></tr>");
			    }
				 $('#installScriptLoadFlag').val('1');
			}
		});
	    $('#script_params_modal').modal('show');
	}
	
	function setParams(){
		var scriptId = $('#installScriptId').val();
		if (scriptId == null || scriptId == ''){
			 showDialog("请先选择脚本，并设置脚本参数!");
		}
		var rebootSoftIds = $('#rebootSoftIds').val();
		if (rebootSoftIds != null && rebootSoftIds != ''){
			document.getElementById("scriptcheckbox_reboot_"+scriptId).checked = true;
		}
		var installSoftIds = $('#installSoftIds').val();
		if (installSoftIds != null && installSoftIds != ''){
			document.getElementById("scriptcheckbox_install_"+scriptId).checked = true;
		}
		$('#script_params_modal').modal('hide');
	}
	
	
	function doMountDisk(){
		var netname = $('#netname').val();
		if (netname == null || netname == ''){
			 showDialog("请先输入网络名称!",false);
			 return false;
		}
		var appNo = $("#h_appNo").val();
		var manager_status = $("#manager_status").val();
		var bind_router = $("#bind_router").val();
		 $("#executeStatus_0").css('display','none');
		 $("#executeStatus_1").css('display','block');
		$.ajax({
				type: "POST",
				url: '<%=basePath%>doMountDisk.do?appNo='+ appNo + '&netname='+netname + '&manager_status=' + manager_status + '&bind_router=' + bind_router,
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if (data.resultCode == 'success'){
						 $("#executeStatus_1").css('display','none');
						 $("#executeStatus_2").css('display','block');
					}
					showDialog(data.resultMsg);
				}
		});
		
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
		 var rebootSoftIds = $('#rebootSoftIds').val();
		 var appNo = $("#h_appNo").val();
		 
		 $("#executeStatus_0").css('display','none');
		 $("#executeStatus_1").css('display','block');
		 queryExecuteStatus(appNo);
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>doRebootSoft.do?appNo='+ appNo + '&scriptId='+scriptId + '&rebootSoftIds=' + rebootSoftIds + '&params=' + params,
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if (data != null && data.resultCode != 'success'){
						 showDialog(data);
					}
				}
		});
	}
	
	function doInstall(){
		var params = '';
		 $('input[name="paramInput"]').each(function(){     
			 params=params+ $(this).val() + ' ';     
		 });
		 if (params == ''){
			 showDialog("请先选择脚本，并设置脚本参数!");
			 return false;
		 }
		 var scriptId = $('#scriptId').val();
		 var installSoftIds = $('#installSoftIds').val();
		 var appNo = $("#h_appNo").val();
		 $("#executeStatus_0").css('display','none');
		 $("#executeStatus_1").css('display','block');
		 queryExecuteStatus(appNo);
		 $.ajax({
				type: "POST",
				url: '<%=basePath%>doInstallSoft.do?appNo='+ appNo + '&scriptId='+scriptId + '&installSoftIds=' + installSoftIds + '&params=' + params,
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					if (data != null && data.resultCode != 'success'){
						 showDialog(data);
					}
				}
		});
	}
	
	function doExecute(appNo){
		top.jzts();
		var comment = $("#comment").val();
		var isRejectChecked = document.getElementById("rejectFlag").checked
		var rejectFlag = true;
		if (isRejectChecked == true){
			rejectFlag = 0;
		}else {
			rejectFlag = 1;
		}
		$.ajax({
			type: "POST",
			url: '<%=basePath%>doExecute.do?appNo='+appNo +'&comment=' + comment+ '&rejectFlag=' + rejectFlag,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				window.top.queryPersonalTask();
				showDialog(data.result, true);
			}
		});
	}
	
	function breakdown_info_OK(){
		$('#breakdown_info_modal').modal('hide');
	}
	</script>

</body>
</html>