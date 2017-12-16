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
						<div class="col-xs-12">
						<form action="subOperService.do" name="subOperService" id="subOperService" method="post">
						<input type="hidden" name="middleware" id="middleware" value=""/>
						<input type="hidden" name="vm" id="vm" value="">
							<div id="zhongxin" style="padding-top: 50px;">
							<table id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;" >
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;服务类型</td>
									<td id="tip_service_type">
									<select name="service_type" id="service_type" title="清选择服务类型" style="width:20%;margin-left: 100px;" onchange="onServiceTypeSelected(this.value)">
										<option value="#" selected="selected">清选择服务类型</option>
									   <c:forEach items="${serviceTypeList}" var="var">
					                   <option value="${var.dictCode}">${var.dictValue}</option>
				                     </c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;操作类型:</td>
									<td id="tip_oper_type">
									<select name="oper_type" id="oper_type" title="清选择操作类型" style="width:20%;margin-left: 100px;">
										<option value="#">请选择操作类型</option>
									</select>
									</td>
								</tr>
							</table>
							<div id="op_1" style="display: none;">
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
							<table class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;申请说明:</td>
									<td id="tip_appmsg">
										<textarea class="form-control limited" name="app_msg" id="app_msg" style="width: 70%"></textarea>
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
		<div class="modal fade" id="vm_modal" tabindex="-1" role="dialog" aria-labelledby="vm_modalLabel" aria-hidden="true">
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
						<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
							<tr class="tablecls">
								<td align="right" style="width: 120px;padding:10px;">虚拟机：</td>
								<td align="left" style="padding:10px;">
									<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								
									<thead>
									<tr>
										<th class="center" style="width: 35px;"><label
											class="pos-rel"><input type="checkbox" class="ace"
												id="zcheckbox" /><span class="lbl"></span></label></th>
										<th class="center">虚拟机编号</th>
										<th class="center">虚拟机名称</th>
										<th class="center">操作</th>
										<th class="center">已选择</th>
									</tr>
								</thead>
								
									<c:choose>
										<c:when test="${not empty vmList}">
											<c:forEach items="${vmList}" var="var" varStatus="vs">
												<tr>
											<td class='center'><label class="pos-rel"><input
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
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
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
	    $('#vm_modal').modal('show');
	}
	
	function vmModalOK(){
		var vm_select_count = 0;
		 text = $("input:checkbox[name='softcheckbox']:checked").map(function(index,vm_selected) {
			 	var m_soft_id = 'soft_'+ $(vm_selected).val();
			 	if ($('#'+m_soft_id).val() == ''){
			 		return "";
			 	}
			 	vm_select_count = vm_select_count +1;
	            return $(vm_selected).val() + ':' + $('#'+m_soft_id).val();
	        }).get().join('|');
		 
		//var checkbox = $('#middleware_checkbox');
		$('#vm').val(text);
		$('#btn_add_vm').text('修改');
		$('#v_vm').html("<b>你已经选择: "+ vm_select_count+ "台虚拟机</b>");
		$('#vm_modal').modal('hide');
	}

	//保存
	function save(){
		var service_type = $("#service_type").val();
		if($("#service_type").val()==""){
			$("#tip_service_type").tips({
				side:3,
	            msg:'选择服务类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#service_type").focus();
			return false;
		}
		
		if (service_type == 1){
			//中间件启停
			var vm =  $("#vm").val();
			var vm_msg = $("#vm_msg").val();
			var app_msg = $("#app_msg").val();
			var oper_type = $("#oper_type").val();
			
			jQuery.ajax({  
				url : "subOperService.do",  
				data : {'service_type': service_type,'vm' : vm, 'vm_msg':vm_msg, 'app_msg':app_msg, 'oper_type':oper_type},  
				type : "post",  
				cache : false,  
				dataType : "json",  
				success:function(data){
					showDialog(data.result);
				}
			});  
			
		}
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
	    $("#op_" + serviceType).attr("style", "display:block;");
	    
	}  
	
	
	function softChoice(vmId){
		$('#vm_modal').modal('hide');
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
	
</script>
</html>