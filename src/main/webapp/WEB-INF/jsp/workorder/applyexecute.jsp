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

	<div class="alert alert-info">服务目录</div>
	<div>
					<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								<thead>
									<tr>
										<th class="center">类型</th>
										<th class="center">环境</th>
										<th class="center">CPU/内存</th>
										<th class="center">存储</th>
										<th class="center">镜像</th>
										<th class="center">数量</th>
										<th class="center">操作</th>
									</tr>
								</thead>

								<tbody>
											<c:choose>
													<c:when test="${not empty cmpCloudInfoList}">
														<c:forEach items="${cmpCloudInfoList}" var="cmpCloudInfo" varStatus="vs">
												<tr onclick="showDitail('${cmpCloudInfo.orderNo}');">
													<td class='center'>${cmpCloudInfo.resTypeName}</td>
													<td class='center'>${cmpCloudInfo.environment}</td>
													<td class='center'>
														${cmpCloudInfo.cpu}核/${cmpCloudInfo.memory}G
													</td>
													<td class='center'>${cmpCloudInfo.dataDiskInfo}</td>
													<td class='center'>${cmpCloudInfo.imgCodeName}</td>
													<td class='center'>${cmpCloudInfo.vmNum}</td>
													<td class='center'>
														<input type="hidden" name="hid_orderNo" value="${cmpCloudInfo.orderNo}">
														<input type="hidden" name="${cmpCloudInfo.orderNo}_executeStatus" id="${cmpCloudInfo.orderNo}_executeStatus" value="${cmpCloudInfo.executeStatus}">
													
														<button id="${cmpCloudInfo.orderNo}_op_0"  style="float:left;margin-left: 100px; display: none;" type="button" 
															onclick="showSetParam('${cmpCloudInfo.orderNo}', '${cmpCloudInfo.appNo}', '${cmpCloudInfo.cpu}', '${cmpCloudInfo.memory}', '${cmpCloudInfo.sysDiskSize}');">安装</button>
														<button id="${cmpCloudInfo.orderNo}_op_1"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
															>安装中...</button>
														<button id="${cmpCloudInfo.orderNo}_op_2"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
															>安装完毕</button>
														<button id="${cmpCloudInfo.orderNo}_op_3"  disabled="disabled" style="float:left;margin-left: 100px; display: none;" type="button" 
															>安装异常</button>
													</td>
													</tr>
											</c:forEach>
												</c:when>
												</c:choose>
								</tbody>
						</table>
							
						
						<div>
						
							<c:choose>
					<c:when test="${not empty cmpCloudInfoList}">
						<c:forEach items="${cmpCloudInfoList}" var="cmpCloudInfo" varStatus="vs">
						<table id="${cmpCloudInfo.orderNo}_detail" 
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px; display: none;">
								<tbody>
									<thead>
										<tr>
											<th class='center'>申请详情</th>
										</tr>
									</thead>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">地域:</td>
										<td class='center'>${cmpCloudInfo.areaName}</td>
										<td class='center' style="background-color: rgb(244,244,244);">环境:</td>
										<td class='center'>${cmpCloudInfo.environment}</td>
										<td class='center' style="background-color: rgb(244,244,244);">项目:</td>
										<td class='center'>${cmpCloudInfo.projectName}</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">CPU:</td>
										<td class='center'>${cmpCloudInfo.cpu}核</td>
										<td class='center' style="background-color: rgb(244,244,244);">内存:</td>
										<td class='center'>${cmpCloudInfo.memory}G</td>
										<td class='center' style="background-color: rgb(244,244,244);">存储:</td>
										<td class='center'>${cmpCloudInfo.dataDiskInfo}</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">镜像:</td>
										<td class='center'>${cmpCloudInfo.imgCodeName}</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">软件:</td>
										<td class='center'>${cmpCloudInfo.soft}</td>
									</tr>
									<tr>
										<td class='center' style="background-color: rgb(244,244,244);">申请人:</td>
										<td class='center'>${cmpCloudInfo.applicant}</td>
									</tr>
								</tbody>
						</table>
						</c:forEach>
				</c:when>
				</c:choose>
				<div class="alert alert-info">部署执行详情</div>
			<div id="shell_msg_div" style="height: 150px; width: 100%; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;"></div>
						
			<div class="alert alert-info">后续任务处理</div>
				<table>
						<c:if test="${workorder.uploadFileName != ''}">
								<tr>
									<td align="right" style="width: 120px;padding:10px;">附件信息：</td>
									<td align="left" style="padding:10px;" width="90%">
										<a href="static/upload/${workorder.uploadFileName}" download="${workorder.uploadFileName}">${workorder.uploadFileName}</a>
									</td>
								</tr>
							</c:if>
			</table>
		<table>
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

	<!-- 云平台、数据中心、集群选择 -->
	<div class="modal fade" id="platform_modal" tabindex="-1" role="dialog" aria-labelledby="platform_modalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="middleware_modalLabel">
						第二步 ---云平台、数据中心、集群选择
					</h4>
				</div>
				<div class="modal-body">
				<input type="hidden"   name="h_appNo" id="h_appNo">
				<input type="hidden"   name="h_orderNo" id="h_orderNo">
				<input type="hidden"   name="h_cpu" id="h_cpu">
				<input type="hidden"   name="h_memory" id="h_memory">
				<input type="hidden"   name="h_diskSize" id="h_diskSize">
				<table  border='1' cellspacing='0' cellpadding='0' style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;云平台</td>
						<td id="tip_cloudplatform">
						<select name="cloudplatform" id="cloudplatform" title="请选择云平台" style="width:40%;margin-left: 100px;" onchange="onCloudPlatformSelected(this.value)">
							<option value="#" selected="selected">请选择服云平台</option>
						   <c:forEach items="${cloudplatformList}" var="var">
		                 	  <option value="${var.id}">${var.name}</option>
	                       </c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;数据中心:</td>
						<td id="tip_datacenter">
							<select name="datacenter" id="datacenter" title="请选择数据中心" style="width:40%;margin-left: 100px;" onchange="onDataCenterSelected(this.value)">
								<option value="#" selected="selected">请选择数据中心</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;集群:</td>
						<td id="tip_cluster">
							<select name="cluster" id="cluster" title="请选择集群" style="width:40%;margin-left: 100px;">
								<option value="#" selected="selected">请选择集群</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;网络:</td>
						<td id="tip_network">
							<select name="network" id="network" title="请选择网络" style="width:40%;margin-left: 100px;" onchange="onNetworkSelected(this.value)">
								<option value="#" selected="selected">请选择网络</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;IP:</td>
						<td id="tip_network">
							<select name="ip" id="ip" title="请选择IP" multiple="multiple" style="width:40%;margin-left: 100px;">
								<option value="#" selected="selected">请选择IP</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
				<div class="modal-footer">
					<button id="platform_modal_btn_cancel" type="button" class="btn btn-default"  data-dismiss="modal">取消
					</button>
					<button  id="platform_modal_btn_ok" type="button" class="btn btn-primary" onclick="execute();" id="platform_modal_ok_btn">
						执行
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- 部署设置 -->
	<div class="modal fade" id="autodeploy_modal" tabindex="-1" role="dialog" aria-labelledby="autodeploy_modalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="middleware_modalLabel">
						第一步 ---脚本参数设置
					</h4>
				</div>
				<div class="modal-body">
				<!-- 
				<table  style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
					<tr>
						<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;部署方案选择:</td>
						<td id="tip_auto_deploy_config">
							<select name="auto_deploy_config_id" id="auto_deploy_config_id" title="请选择部署方案" style="width:40%;margin-left: 100px;" onchange="onAutoDeploySelect(this.value)">
								<option value="#" selected="selected">请选择部署方案</option>
							</select>
						</td>
					</tr>
				</table>
				 -->
						<form>
						<ul id="autoDeployTab" class="nav nav-tabs">
							
						</ul>
						<div id="autoDeployTabContent" class="tab-content">
							
						</div>
						</form>
					</div>
				<div class="modal-footer">
					<button id="platform_modal_btn_cancel" type="button" class="btn btn-default"  data-dismiss="modal">取消
					</button>
					<button  id="platform_modal_btn_ok" type="button" onclick="toInstall();" class="btn btn-primary" data-dismiss="modal" id="platform_modal_ok_btn">
						下一步
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>


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
		
		$("input[name='hid_orderNo']").each(function(){  
			var orderNo = $(this).val();
			var executeStatus = $("#" + orderNo + "_executeStatus").val();
			 $("#" + orderNo + "_op_" + executeStatus).show();
		}); 
		
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
	
	$(top.hangge());//关闭加载状态
	//检索
	function tosearch(){
		top.jzts();
		//$("#Form").submit();
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
		var isRejectChecked = document.getElementById("rejectFlag").checked
		var rejectFlag = true;
		if (isRejectChecked == true){
			rejectFlag = 0;
		}else {
			rejectFlag = 1;
		}
		$.ajax({
			type: "POST",
			url: '<%=basePath%>doExecute.do?appNo='+appNo +'&comment=' + comment + '&rejectFlag=' + rejectFlag,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				window.top.queryPersonalTask();
				showDialog(data.result, true);
			}
		});
	}
	
	function toInstall(appNo, cpu, memory, diskSize){
		$('#autodeploy_modal').modal('hide');
		$('#platform_modal').modal('show');
	}
	
	function execute(){
		var appNo = $("#h_appNo").val();
		var orderNo = $("#h_orderNo").val();
		var cpu =$("#h_cpu").val();
		var memory = $("#h_memory").val();
		var diskSize = $("#h_diskSize").val();
		var cloudPlatform = $("#cloudplatform").val();
		var datacenter = $("#datacenter").val();
		var cluster = $("#cluster").val();
		var network = $("#network").val();
		var ip = $("#ip").val();
		var auto_deploy_config_id = $("#auto_deploy_config_id").val();
		if (cloudPlatform == null || cloudPlatform == '#' || cloudPlatform == ''){
			$('#platform_modal').modal('hide');
			showDialog("请选择云平台");
			return false;
		}
		if (datacenter == null || datacenter == '#' || datacenter == ''){
			$('#platform_modal').modal('hide');
			showDialog("请选择数据中心");
			return false;
		}
		if (cluster == null || cluster == '#' || cluster == ''){
			$('#platform_modal').modal('hide');
			showDialog("请选择集群");
			return false;
		}
		if (ip == null || ip == '#' || ip == ''){
			$('#platform_modal').modal('hide');
			showDialog("请选择IP");
			return false;
		}
		$('#platform_modal').modal('hide');
		$("#"+orderNo+"_op_0").css('display','none');
		$("#"+orderNo+"_op_1").css('display','block');
		queryExecuteStatus(orderNo);
		
		var submitScriptParam = "";
		x=$("form").serializeArray();
	    $.each(x, function(i, field){
	    	submitScriptParam+=("&" + field.name + "=" + field.value );
	    });
		$.ajax({
			type: "POST",
			url: '<%=basePath%>executeWork.do?appNo='+appNo + '&orderNo='+orderNo +'&CPU=' + cpu +'&memory=' + memory +'&network=' + network+'&ip=' + ip+'&auto_deploy_config_id=' + auto_deploy_config_id+ '&diskSize=' + 
					diskSize + '&cloudPlatformId=' + cloudPlatform + '&datacenterId=' + datacenter + '&clusterId=' + cluster + submitScriptParam ,
			dataType:'json',
			//beforeSend: validateData,
			cache: false,
			success: function(data){
				//showDialog(data.result);
			}
		});
	}
	
	function queryExecuteStatus(orderNo){
		//查询，并同步更新控制台
		var res = setInterval(function(){
			$.ajax({
				type: "POST",
				url: '<%=basePath%>queryShell.do?shellId='+orderNo ,
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					var maplen = data.length;
					$('#shell_msg_div').empty();
					for (var msgIndex = 0; msgIndex < data.length; msgIndex++){
						if (data[msgIndex] == "cmp:success"){
							$("#"+orderNo+"_op_1").css('display','none');
							$("#"+orderNo+"_op_2").css('display','block');
							clearInterval(res);
							break;
						}else if (data[msgIndex] == "cmp:error"){
							$("#"+orderNo+"_op_1").css('display','none');
							$("#"+orderNo+"_op_3").css('display','block');
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
	
	
	function onCloudPlatformSelected(cloudPlatformId){
		jQuery.ajax({  
			url : "<%=basePath%>onCloudplatformSelected.do",  
			data : {'cloudPlatformId' : cloudPlatformId},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				var select_root=document.getElementById('datacenter');  
			    select_root.options.length=0;
			    var _option=new Option("请选择数据中心","#"); 
			    select_root.add(_option);
			    for(var i=0;i<data.length;i++){  
			            var xValue=data[i].id;  
			             var xText=data[i].name;
			             var option=new Option(xText,xValue);  
			             select_root.add(option);  
			    }
			}  
		});  
	} 
	var networkMap = {};
	function onDataCenterSelected(dataCenterId){
		jQuery.ajax({  
			url : "<%=basePath%>onDataCenterSelected.do",  
			data : {'dataCenterId' : dataCenterId},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				var select_root=document.getElementById('cluster');  
			    select_root.options.length=0;
			 	var _option=new Option("请选择集群","#"); 
			    select_root.add(_option);
			    for(var i=0;i<data.length;i++){ 
			    	var xValue=data[i].id;  
		             var xText=data[i].name;
		             var option=new Option(xText,xValue);  
		             select_root.add(option);  
			    }
			}  
		});  
		
		jQuery.ajax({  
			url : "<%=basePath%>toNetworkQuery.do",  
			data : {'dataCenterId' : dataCenterId},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				var select_root=document.getElementById('network');  
			    select_root.options.length=0;
			 	var _option=new Option("请选择网络","#"); 
			    select_root.add(_option);
			    for(var i=0;i<data.length;i++){  
			    	if (data[i].ippool != null){
			    		networkMap[data[i].id] = data[i].ippool;
			    	}
			    	var xValue=data[i].id;  
		             var xText=data[i].name;
		             var option=new Option(xText,xValue);  
		             select_root.add(option);  
			    }
			}  
		});  
		
	} 
	
	function onNetworkSelected(networkid){
		var ippool = networkMap[networkid];
		jQuery.ajax({  
			url : "<%=basePath%>toCheckNetwork.do",  
			data : {'ippool' : ippool},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				var select_root=document.getElementById('ip');  
			    select_root.options.length=0;
			 	var _option=new Option("请选择IP","#"); 
			    select_root.add(_option);
				for(var i=0;i<data.length;i++){ 
				    	var xValue = data[i];  
			             var xText = data[i];
			             var option=new Option(xText,xValue);  
			             select_root.add(option);  
				}
			}  
		});  
		
	}
	
	
	function toAutoDeploySelect(){
		jQuery.ajax({  
			url : "<%=basePath%>toAutoDeploySelect.do",  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				var select_root=document.getElementById('auto_deploy_config_id');  
			    select_root.options.length=0;
			 	var _option=new Option("请选择部署方案","#"); 
			    select_root.add(_option);
			    for(var i=0;i<data.length;i++){  
			    	var xValue=data[i].id;  
		             var xText=data[i].name;
		             var option=new Option(xText,xValue);  
		             select_root.add(option);  
			    }
			}  
		}); 
		$('#autodeploy_modal').modal('show');
	}
	
	//目前不用。。。。。
	function onAutoDeploySelect(autodeployid){
		jQuery.ajax({  
			url : "<%=basePath%>onAutoDeploySelect.do",  
			data : {'autodeployid' : autodeployid},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				
				 var autoDeployTab = $("#autoDeployTab");
				 $("#autoDeployTab").empty();
				 
				 var autoDeployTabContent =  $("#autoDeployTabContent");
				 $("#autoDeployTabContent").empty();
				 
				 for(var i=0;i<data.length;i++){ 
						var autoDeployNode = data[i];
						autoDeployTab.append("<li><a href='#" + autoDeployNode.id +"' data-toggle='tab'>" + autoDeployNode.name +"</a></li>");
						var scriptNodeList = autoDeployNode.scriptNodeList;
						if (scriptNodeList != null){
							var appendHTML = "";
							if (i == 0){
								appendHTML+=("<div class='tab-pane fade in active' id='" + autoDeployNode.id +"'>");
							}else{
								appendHTML+=("<div class='tab-pane fade' id='" + autoDeployNode.id +"'>");
							}
							appendHTML += "<table border='1' cellspacing='0' cellpadding='0' id='paramsTable' style='width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;'> "  + 
							"<thead> <tr> <th class='center'>参数名称</th> <th class='center'>参数值</th> </tr> </thead>"
							
							
							for(var z=0;z<scriptNodeList.length;z++){ 
								 var scriptNode = scriptNodeList[z];
								 appendHTML+=("<tr><td class='center'>" + scriptNode.name + "</td><td class='center'><input name='"+ scriptNode.paramKey +"' id='"+ scriptNode.paramKey +"' value='"+ scriptNode.defaultVal +"' /></td></tr>");
							}
							 appendHTML+="</table> </div>"
								 autoDeployTabContent.append(appendHTML);
						}
				 }
			}  
		}); 
	}
	
	function showSetParam(orderNo, appNo, cpu, memory, diskSize){
		$("#h_appNo").val(appNo);
		$("#h_orderNo").val(orderNo);
		$("#h_cpu").val(cpu);
		$("#h_memory").val(memory);
		$("#h_diskSize").val(diskSize);
		jQuery.ajax({  
			url : "<%=basePath%>toSetParam.do",  
			data : {'orderNo' : orderNo},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				
				 var autoDeployTab = $("#autoDeployTab");
				 $("#autoDeployTab").empty();
				 
				 var autoDeployTabContent =  $("#autoDeployTabContent");
				 $("#autoDeployTabContent").empty();
				 
				 for(var i=0;i<data.length;i++){ 
						var autoDeployNode = data[i];
						if (autoDeployNode.modFlag == '1'){
							autoDeployTab.append("<li ><a style='color: red;' href='#" + autoDeployNode.id +"' data-toggle='tab'>" + autoDeployNode.name +"</a></li>");
						}else{
							autoDeployTab.append("<li><a href='#" + autoDeployNode.id +"' data-toggle='tab'>" + autoDeployNode.name +"</a></li>");
						}
						
						
						var scriptNodeList = autoDeployNode.scriptNodeList;
						if (scriptNodeList != null){
							var appendHTML = "";
							if (i == 0){
								appendHTML+=("<div class='tab-pane fade in active' id='" + autoDeployNode.id +"'>");
							}else{
								appendHTML+=("<div class='tab-pane fade' id='" + autoDeployNode.id +"'>");
							}
							appendHTML += "<table border='1' cellspacing='0' cellpadding='0' id='paramsTable' style='width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;'> "  + 
							"<thead> <tr> <th class='center'>参数名称</th> <th class='center'>参数值</th> <th class='center'>默认值</th></tr></thead>"
							
							
							for(var z=0;z<scriptNodeList.length;z++){ 
								 var scriptNode = scriptNodeList[z];
								 appendHTML+=("<tr><td class='center'>" + scriptNode.name + "</td><td class='center'><input name='"+ scriptNode.paramKey +"' id='"+ scriptNode.paramKey +"' value='"+ scriptNode.value +"' /></td><td class='center'>" + scriptNode.defaultVal + "</td></tr>");
							}
							 appendHTML+="</table> </div>"
								 autoDeployTabContent.append(appendHTML);
						}
				 }
			}  
		});
		
		$('#autodeploy_modal').modal('show');
	}
	
	
	function showDitail(orderNo){
		$("table[id$='_detail']").each(function(){  
			$(this).hide();
		}); 
		$('#'+orderNo+'_detail').show();
	}
	</script>

</body>
</html>