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
<link rel="stylesheet" href="css/style.css"/>
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css" />
<jsp:include page="../msg/msg_dialog.jsp"></jsp:include>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<input type="hidden" id="ret_msg" name="ret_msg" value="${requestScope.retMsg}" />
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
					<form action="subOperService.do" name="subOperService" id="subOperService" method="post">
						<div class="col-xs-12">
						<input type="hidden" name="middleware" id="middleware" value=""/>
						<input type="hidden" name="vm" id="vm" value="">
						<input type="hidden" name="remark1" id="remark1" value="">
							<div id="zhongxin" style="padding-top: 50px;">
							<table id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;" >
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;服务类型</td>
									<td id="tip_service_type">
									<select name="service_type" id="service_type" title="请选择服务类型" style="width:20%;margin-left: 100px;" onchange="onServiceTypeSelected(this.value)">
										<option value="#" selected="selected">请选择服务类型</option>
									   <c:forEach items="${serviceTypeList}" var="var">
					                   <option value="${var.dictCode}">${var.dictValue}</option>
				                     </c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;操作类型:</td>
									<td id="tip_oper_type">
									<select name="oper_type" id="oper_type" title="请选择操作类型" style="width:20%;margin-left: 100px;">
									</select>
									</td>
								</tr>
							</table>
							<div id="op_1" style="display: none;">
							<!-- 中间件启停 -->
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;选择虚拟机:</td>
										<td id="tip_vm">
										<button class="btn btn-info" id="btn_add_vm"  style="float:left;margin-left: 100px;" type="button" data-toggle="modal" data-target="#vm_modal">添加</button>
										<div style="float:left; text-align: center;line-height:40px; padding-left: 40px;" id="v_vm"></div>
										</td>
									</tr>
								</table>
							</div>
							<div id="op_vm_select">
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;选择虚拟机:</td>
										<td id="tip_vm">
										<button class="btn btn-info" id="btn_add_vm_2"  style="float:left;margin-left: 100px;" type="button" data-toggle="modal" data-target="#vm_select_modal">添加</button>
										<div style="float:left; text-align: center;line-height:40px; padding-left: 40px;" id="v_vm_2"></div>
										</td>
									</tr>
								</table>
							</div>
							
							</div>
							<div id="op_2" style="display: none;">
							<!-- 安装软件 -->
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;选择软件:</td>
										<td id="tip_oper_soft">
										<select name="install_soft" id="install_soft" title="请选择软件" style="width:20%;margin-left: 100px;" onchange="selectSoftVersion(this.value)">
											  <option value="#">请选择软件</option>
											  <c:forEach items="${softName}" var="var">
					                  				 <option value="${var}">${var}</option>
				                     		  </c:forEach>
										</select>
										</td>
									</tr>
									<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;软件版本:</td>
									<td>
									<select name="soft_version" id="soft_version" title="请选择软件版本" style="width:20%;margin-left: 100px;">
									</select>
									</td>
								</tr>
								</table>
							</div>
							
							<div id="op_3" style="display: none;">
							<!-- 故障处理 -->
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;故障发生时间:</td>
										<td>
											<input id="breakdown_time" name="breakdown_time" type="text"  style="width:20%;margin-left: 100px;"/>
										</td>
									</tr>
									<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;故障描述:</td>
									<td>
										<textarea class="form-control limited" name="breakdown_info" id="breakdown_info" style="width: 70%;margin-left: 100px;"></textarea>
									</td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;期望解决时间:</td>
										<td>
											<input id="except_solve_time" name="except_solve_time" type="text"  style="width:20%;margin-left: 100px;"/>
										</td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;期望结果:</td>
										<td>
											<textarea class="form-control limited" name="except_result" id="except_result" style="width: 70%;margin-left: 100px;"></textarea>
										</td>
									</tr>
									<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;故障级别:</td>
									<td>
										<select name="breakdown_level" id="breakdown_level" title="故障级别" style="width:20%;margin-left: 100px;">
											  <option value="1">一般</option>
											  <option value="2">中等</option>
											  <option value="3">严重</option>
										</select>
									</td>
									</tr>
								</table>
							</div>
							<div id="op_4" style="display: none;">
							<!-- 系统分区 -->
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;分区数量及大小:</td>
										<td id="tip_oper_soft">
											<textarea class="form-control limited" name="partition_info" id="partition_info" style="width: 70%;margin-left: 100px;"></textarea>
										</td>
								</tr>
								</table>
							</div>
							<div id="op_6" style="display: none;">
							<!-- 挂载磁盘 -->
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;指定目录:</td>
										<td>
											<input type="text" class="form-control limited" name="directory" id="directory" style="width: 70%;margin-left: 100px;"></input>
										</td>
								</tr>
								<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;使用期限:</td>
										<td>
											<input id="exp_time" name="exp_time" type="text"  style="width:20%;margin-left: 100px;"/>
										</td>
								</tr>
								</table>
							</div>
							<div id="op_7" style="display: none;">
							<!-- ROOT权限申请 -->
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
								<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;指定目录:</td>
										<td>
											<input type="text" class="form-control limited" name="directory2" id="directory2" style="width: 70%;margin-left: 100px;"></input>
										</td>
								</tr>
								<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;ROOT密码:</td>
										<td>
											<input type="text" class="form-control limited" name="rootpwd" id="rootpwd" style="width: 70%;margin-left: 100px;"></input>
										</td>
								</tr>
								<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;使用期限:</td>
										<td>
											<input id="exp_time_pwd" name="exp_time_pwd" type="text"  style="width:20%;margin-left: 100px;"/>
										</td>
								</tr>
								</table>
							</div>
							<div id="op_8" style="display: none;">
							<!-- VIP申请 -->
								<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr>
											<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;VIP数量:</td>
											<td>
												<input type="text" class="form-control limited" name="vip_num" id="vip_num" style="width: 70%;margin-left: 100px;"></input>
											</td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;指定目录:</td>
										<td>
											<input type="text" class="form-control limited" name="directory3" id="directory3" style="width: 70%;margin-left: 100px;"></input>
										</td>
								</tr>
								</table>
							</div>
							
							<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;文件上传:</td>
									<td>
										<input style="margin-left: 100px;" type="file" name="uploadFile" id="uploadFile" value="选择文件"/>
										<input style="margin-left: 100px;" type="button" value="点击上传" onclick="doUpload();">
										<input type="hidden" name="file_name" id="file_name">
									</td>	
								</tr>
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;申请说明:</td>
									<td id="tip_appmsg">
										<textarea class="form-control limited" name="app_msg" id="app_msg" style="width: 70%;margin-left: 100px;"></textarea>
									</td>
								</tr>
								
								<tr>
									<td class="center" colspan="2">
										<a class="btn btn-mini btn-primary" onclick="save();">递交申请</a>
									</td>
								</tr>
							</table>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						</form>
						</div>
		<!-- OP1 选择虚拟机（Modal） -->
		<div class="modal fade" id="vm_modal" tabindex="-1" role="dialog" aria-labelledby="vm_modalLabel" style="overflow: auto" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="vm_modalLabel">
							虚拟机
						</h4>
					</div>
					<div class="modal-body">
						项目查询:
						<select name="vmModalProject" id="vmModalProject" title="分项目查询" style="width:20%;margin-left: 100px;" onchange="onVmModalProjectSelect(this.value)">
										<option value="#" selected="selected">请选择项目</option>
									   <c:forEach items="${projectNameMap}" var="var">
					                   <option value="${var.key}">${var.value}</option>
				                     </c:forEach>
						</select>
						<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
							<tr class="tablecls">
								<td align="right" style="width: 120px;padding:10px;">虚拟机：</td>
								<td align="left" style="padding:10px;">
									<table id="table_vm_1"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								
									<thead>
									<tr id="vm_modal_head">
										<th class="center" style="width: 35px;"></th>
										<th class="center">虚拟机编号</th>
										<th class="center">虚拟机名称</th>
										<th class="center">操作</th>
										<th class="center">已选择</th>
									</tr>
								</thead>
								
									<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
												<tr id="vm_modal_tr_${var.projectId}" class="vm_modal_tr">
											<td class='center' ><label class="pos-rel"><input
															type='checkbox' id='checkbox_${var.id}' name='softcheckbox' value="${var.id}" class="ace" /><span
															class="lbl"></span></label></td>
														<td class='center'>${var.id}</td>
														<td class='center'>${var.name}</td>
														<td class='center'><input type="button" id="softChoiceBtn" value="选择中间件" onclick="softChoice('${var.id}');"></td>
														<td style="display: none;"><input type="hidden" id="soft_${var.id}" name="soft_${var.id}"></td>
														<td id="span_${var.id}"></td>
														
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
								</td>
							</tr>
							<tr class="tablecls">
								<td align="right" style="width: 120px;padding:10px;">说明：</td>
								<td align="left" style="padding:10px;" >
									<textarea class="form-control limited" id="vm_msg" style="width: 70%"></textarea>
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"  data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="vm_modal_ok_btn" onclick="vmModalOK();">
							确定
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		
						<!-- op_1 软件选择（Modal） -->
						<div class="modal fade" id="middleware_modal" tabindex="-1" role="dialog" aria-labelledby="middleware_modalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
											&times;
										</button>
										<h4 class="modal-title" id="middleware_modalLabel">
											中间件
										</h4>
									</div>
									<div class="modal-body">
									<input type="hidden"   name="middleware_modal_vmId" id="middleware_modal_vmId">
									<table  style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
									<tr class="tablecls">
										<td align="right" style="width: 120px;padding:10px;">中间件：</td>
										<td align="left" style="padding:10px;">
											<table id="deployed_soft_table"
										class="table table-striped table-bordered table-hover"
										style="margin-top: 5px;">
										
											<thead>
											<tr>
												<th class="center" style="width: 35px;"><label
													class="pos-rel"><input type="checkbox" class="ace"
														id="zcheckbox" /><span class="lbl"></span></label></th>
												<th class="center">中间件部署序号</th>
												<th class="center">中间件名称</th>
											</tr>
										</thead>
											
											</table>
										</td>
									</tr>
								</table>
								</div>
									<div class="modal-footer">
										<button id="middleware_btn_cancel" type="button" class="btn btn-default" onclick="middlewareModalCancel()"  data-dismiss="modal">取消
										</button>
										<button  id="middleware_btn_ok" type="button" class="btn btn-primary" onclick="middlewareModalOK();" id="middleware_modal_ok_btn">
											确定
										</button>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal -->
						</div>
						
						
<!-- OP2-8 选择虚拟机(纯选择虚拟机，不联动)（Modal） -->
		<div class="modal fade" id="vm_select_modal" tabindex="-1" role="dialog" style="overflow: auto" aria-labelledby="vm_modalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="vm_modalLabel">
							虚拟机
						</h4>
					</div>
					<div class="modal-body">	
						项目查询:
						<select name="vmModalProject" id="vmModalProject" title="分项目查询" style="width:20%;margin-left: 100px;" onchange="onVmSelectModalProjectSelect(this.value)">
										<option value="#" selected="selected">请选择项目</option>
									   <c:forEach items="${projectNameMap}" var="var">
					                   <option value="${var.key}">${var.value}</option>
				                     </c:forEach>
						</select>
						<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
							<tr class="tablecls">
								<td align="right" style="width: 120px;padding:10px;">虚拟机：</td>
								<td align="left" style="padding:10px;">
									<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
									<thead>
									<tr>
										<th class="center" style="width: 35px;"></th>
										<th class="center">虚拟机编号</th>
										<th class="center">虚拟机名称</th>
									</tr>
								</thead>
								
									<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
												<tr id="vm_select_modal_tr_${var.projectId}" class="vm_select_modal_tr">
											<td class='center'><label class="pos-rel"><input
															type='checkbox' id='vmcheckbox_${var.id}' name='vmcheckbox' value="${var.id}" class="ace" /><span
															class="lbl"></span></label></td>
														<td class='center'>${var.id}</td>
														<td class='center'>${var.name}</td>
														
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
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"  data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary" id="vm_modal_ok_btn" onclick="vmSelectModalOK();">
							确定
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>						
<script type="text/javascript">
	$(top.hangge());
	
	$(document).ready(function(){
		var result = $('#ret_msg').val();
		if (result != ''){
			window.top.queryPersonalTask();
			showDialog(result);
		}
		$('#breakdown_time').datetimepicker({
			format: 'YYYY-MM-DD hh:mm:ss',  
	        locale: moment.locale('zh-cn')
			});
		$('#except_solve_time').datetimepicker({
			format: 'YYYY-MM-DD hh:mm:ss',  
	        locale: moment.locale('zh-cn')
			});
		$('#exp_time').datetimepicker({
			format: 'YYYY-MM-DD hh:mm:ss',  
	        locale: moment.locale('zh-cn')
			});
		$('#exp_time_pwd').datetimepicker({
			format: 'YYYY-MM-DD hh:mm:ss',  
	        locale: moment.locale('zh-cn')
			});
	});
	
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	
	function setFieldCls(obj, fieldName){
		$("#"+fieldName+"Id li").each(function(){//$("#id li")
			if($(this).hasClass("active")){
				$(this).removeClass("active");
			}
		});
		$(obj).addClass("active");
	}

	function setFieldValue(obj, fieldName, fieldValue){
		$("#"+fieldName).val(fieldValue);
		setFieldCls(obj, fieldName);
	}
	
	function middlewareModalOK(){
		var checkbox = $('#middleware_checkbox');
	    var vmId = $('#middleware_modal_vmId').val();
	    $('#soft_'+vmId).val("");
	    $('#span_'+vmId).html("");
	    document.getElementById("checkbox_"+vmId).checked = false;
	    var checkedCount = 0;
	    text = $("input:checkbox[name='middleware_checkbox']:checked").map(function(index,soft) {
	    	checkedCount = checkedCount+1;
            return $(soft).val();
        }).get().join(',');
	    if (checkedCount != 0){
	    	$('#soft_'+vmId).val(text);
	    	$('#span_'+vmId).html("已选择" + checkedCount + "个中间件");
	    	document.getElementById("checkbox_"+vmId).checked = true;
	    }
		$('#middleware_modal').modal('hide');
		$(document.body).addClass("modal-open");  
	    //$('#vm_modal').modal('show');
	}
	
	function vmModalOK(){
		var vm_select_count = 0;
		 text = $("input:checkbox[name='softcheckbox']:checked").map(function(index,vm_selected) {
			 	var m_soft_id = 'soft_'+ $(vm_selected).val();
			 	if ($('#'+m_soft_id).val() == ''){
			 		return "";
			 	}
			 	vm_select_count = vm_select_count +1;
	            return $('#'+m_soft_id).val();
	        }).get().join('|');
		vm = $("input:checkbox[name='softcheckbox']:checked").map(function(index,vm_selected) {
			 return $(vm_selected).val();
        }).get().join(',');
		//var checkbox = $('#middleware_checkbox');
		$('#vm').val(vm);
		$('#remark1').val(text);
		$('#btn_add_vm').text('修改');
		$('#v_vm').html("<b>你已经选择: "+ vm_select_count+ "台虚拟机</b>");
		$('#vm_modal').modal('hide');
	}
	
	function vmSelectModalOK(){
		var vm_select_count = 0;
		 text = $("input:checkbox[name='vmcheckbox']:checked").map(function(index,vm_selected) {
			 vm_select_count = vm_select_count +1;
	         return $(vm_selected).val();
	        }).get().join(',');
		 
		//var checkbox = $('#middleware_checkbox');
		$('#vm').val(text);
		$('#btn_add_vm_2').text('修改');
		$('#v_vm_2').html("<b>你已经选择: "+ vm_select_count+ "台虚拟机</b>");
		$('#vm_select_modal').modal('hide');
	}

	//保存
	function save(){
		
		var vm =  $("#vm").val();
		var vm_msg = $("#vm_msg").val();
		var app_msg = $("#app_msg").val();
		var oper_type = $("#oper_type").val();
		var install_soft= $("#install_soft").val();
		var service_type = $("#service_type").val();
		var soft_version = $("#soft_version").val();
		var breakdown_time = $("#breakdown_time").val();
		var except_solve_time = $("#except_solve_time").val();
		var except_result = $("#except_result").val();
		var breakdown_level = $("#breakdown_level").val();
		var breakdown_info = $("#breakdown_info").val();
		var partition_info = $("#partition_info").val();
		var directory = $("#directory").val();
		var directory2 = $("#directory2").val();
		var exp_time =  $("#exp_time").val();
		var vip_num = $("#vip_num").val();
		var directory3 = $("#directory3").val();
		var rootpwd = $("#rootpwd").val();
		var exp_time_pwd =  $("#exp_time_pwd").val();
		var remark1 = $("#remark1").val();
		var file_name = $("#file_name").val();
		if( service_type ==""){
			$("#tip_service_type").tips({
				side:3,
	            msg:'选择服务类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#service_type").focus();
			return false;
		}
		if (service_type == 2){
			if (vm == null){
				showDialog("请选择虚拟机");
				return false;
			}
			if (install_soft == null){
				showDialog("请选择需安装的软件");
				return false;
			}
			if (soft_version == null){
				showDialog("请选择软件版本");
				return false;
			}
		}
		if (service_type == 3){
			if (breakdown_time == null || breakdown_time == ''){
				showDialog("请输入故障时间");
				return false;
			}
			if (breakdown_info == null){
				showDialog("请输入故障信息");
				return false;
			}
			if (except_solve_time == null || except_solve_time == ''){
				showDialog("请输入期望解决故障时间");
				return false;
			}
			if (breakdown_level == null){
				showDialog("请选择故障级别");
				return false;
			}
		}
		if (service_type == 4){
			if (partition_info == null){
				showDialog("请输入分区数量及大小");
				return false;
			}
		}
		if (service_type == 6){
			if (directory == null){
				showDialog("请输入指定目录");
				return false;
			}
		}
		if (service_type == 7){
			if (directory2 == null){
				showDialog("请输入指定目录");
				return false;
			}
			if (exp_time_pwd == null || exp_time_pwd == ''){
				showDialog("请输入使用期限");
				return false;
			}
			if (rootpwd == null){
				showDialog("请输入ROOT密码");
				return false;
			}
		}
		if (service_type == 8){
			if (directory3 == null){
				showDialog("请输入指定目录");
				return false;
			}
			if (vip_num == null){
				showDialog("请输入云主机数量");
				return false;
			}
			
		}
		
		
		//递交
		top.top.jzts();
		jQuery.ajax({  
			url : "subOperService.do",  
			data : {'service_type': service_type,'vm' : vm, 'vm_msg':vm_msg, 'app_msg':app_msg,
						'oper_type':oper_type, 'install_soft':install_soft, 'soft_version':soft_version,
						'breakdown_time':breakdown_time, 'breakdown_info':breakdown_info, 'except_solve_time':except_solve_time,
						'except_result':except_result,'breakdown_level':breakdown_level, 'partition_info':partition_info, 'directory':directory,
						 'directory2':directory2, 'exp_time':exp_time, 'rootpwd':rootpwd, 'exp_time_pwd':exp_time_pwd,'directory3':directory3, 'vip_num':vip_num, 'remark1':remark1, 'file_name':file_name
					},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				top.hangge();
				showDialog(data.result, true);
			}
		});  
		
	}
	
	
	
	
	function onServiceTypeSelected(service_type){
		jQuery.ajax({  
			url : "onServiceTypeSelected.do",  
			data : {serviceType : service_type},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:onchangecallback  
		});  
	}  
	
	function onchangecallback(data){   
	    var select_root=document.getElementById('oper_type');  
	    select_root.options.length=0;
	    for(var i=0;i<data.length;i++){  
	            var xValue=data[i].dictCode;  
	             var xText=data[i].dictValue;
	             var option=new Option(xText,xValue);  
	             select_root.add(option);  
	    }
	    var serviceType = $("#service_type").val();
	    for (i = 1; i<9; i++){
	    	$("#op_" + i).attr("style", "display:none;");
	    }
	    if (serviceType != 1){
	    	$("#op_vm_select").attr("style", "display:block;");
	    }else{
	    	$("#op_vm_select").attr("style", "display:none;");
	    }
	    $("#op_" + serviceType).attr("style", "display:block;");
	    
	}  
	
	
	function selectSoftVersion(soft_name){
		jQuery.ajax({  
			url : "querySoftVersion.do",  
			data : {softName : soft_name},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				   var select_root=document.getElementById('soft_version');  
				    select_root.options.length=0;
				    for(var i=0;i<data.length;i++){  
				            var xValue=data[i];  
				             var xText=data[i];
				             var option=new Option(xText,xValue);  
				             select_root.add(option);  
				    }
			} 
		});  
	}
	
	
	
	function softChoice(vmId){
		//$('#vm_modal').modal('hide');
		$('#middleware_modal').modal('show');
		$('#middleware_modal_vmId').val(vmId);
		jQuery.ajax({  
			url : "queryDeployedSoft.do",  
			data : {vmId : vmId},  
			type : "post",  
			cache : false,  
			dataType : "json",  
			success:function(data){
				   var table = $("#deployed_soft_table");
				   table.empty();
				   for (var i=0;i<data.length;i++){
					   var _tr =  $("<tr>"+
								        "<td><label class='pos-rel'><input type='checkbox' id='middleware_checkbox' name='middleware_checkbox' value='"+ data[i].id +"' class='ace' /><span class='lbl'></span> </label></td>"+
								        "<td>"+data[i].id+"</td>"+
								        "<td>"+data[i].softName+"</td>"
								        +"</tr>");
					   table.append(_tr);
				   }
			}
		});  
	}
	
	function onVmModalProjectSelect(projectId){
		$(".vm_modal_tr").each(function(){       
		    $(this).attr("style", "display:none;");
		});   
		$("[id=vm_modal_tr_"+ projectId +"]").each(function(){       
		    $(this).attr("style", "display:' ';");
		});   
	}
	
	function doUpload() {  
	     var formData = new FormData($( "#subOperService" )[0]);  
	     $.ajax({  
	          url: 'appFileUpload.do' ,  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (data) {  
	        	  $("#file_name").val(data);
	              showDialog("文件上传成功");
	          },  
	          error: function (data) {  
	        	  showDialog("文件上传异常"); 
	          }  
	     });  
	}  
	         
	function onVmSelectModalProjectSelect(projectId){
		$(".vm_select_modal_tr").each(function(){       
		    $(this).attr("style", "display:none;");
		});   
		$("[id=vm_select_modal_tr_"+ projectId +"]").each(function(){       
		    $(this).attr("style", "display:' ';");
		});   
	}
	
</script>
</html>