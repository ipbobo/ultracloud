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
						<input type="hidden" name="middleware_msg" id="middleware_msg" value="">
						<input type="hidden" name="vm_msg" id="vm_msg" value="">
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
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;选择虚拟机:</td>
									<td id="tip_vm">
									<button class="btn btn-info" id="btn_add_vm"  style="float:left;margin-left: 100px;" type="button" data-toggle="modal" data-target="#vm_modal">添加</button>
									<div style="float:left; text-align: center;line-height:40px; padding-left: 40px;" id="v_vm"></div>
									</td>
								</tr>
								<tr>
									<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;操作中间件:</td>
									<td id="tip_middleware" >
										<button class="btn btn-info" style="float:left;margin-left: 100px;" id="btn_add_middleware" type="button" data-toggle="modal" data-target="#middleware_modal">添加</button>
										<div style="float:left; text-align: center;line-height:40px; padding-left: 40px;" id="v_middleware"></div>
									</td>
								</tr>
								
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
						<!-- 模态框（Modal） -->
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
												<td align="left" style="padding:10px;" id="tip_vm_btn_group">
													<div class="btn-group"></div>
														<c:if test="${not empty vmList}">
														<ul id="vmId" class="ullitab list-inline">
															<c:forEach items="${vmList}" var="var" varStatus="st">
															<li onclick="setFieldValue(this, 'vm', '${var.dictValue}')">${var.dictValue}</li>
															</c:forEach>
														</ul>
														</c:if>
												</td>
											</tr>
											<tr class="tablecls">
												<td align="right" style="width: 120px;padding:10px;">说明：</td>
												<td align="left" style="padding:10px;" >
													<textarea class="form-control limited" id="vm_textarea" style="width: 70%"></textarea>
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
						
						<!-- 模态框（Modal） -->
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
										<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
											<tr class="tablecls">
												<td align="right" style="width: 120px;padding:10px;">中间件：</td>
												<td align="left" style="padding:10px;" id="tip_middleware_btn_group">
													<div class="btn-group"></div>
														<c:if test="${not empty middlewareList}">
														<ul id="middlewareId" class="ullitab list-inline">
															<c:forEach items="${middlewareList}" var="var" varStatus="st">
															<li onclick="setFieldValue(this, 'middleware', '${var.dictValue}')">${var.dictValue}</li>
															</c:forEach>
														</ul>
														</c:if>
														
												</td>
											</tr>
											<tr class="tablecls">
												<td align="right" style="width: 120px;padding:10px;">说明：</td>
												<td align="left" style="padding:10px;" >
													<textarea class="form-control limited" id="middleware_textarea" style="width: 70%"></textarea>
												</td>
											</tr>
										</table>
										
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"  data-dismiss="modal">关闭
										</button>
										<button type="button" class="btn btn-primary" onclick="middlewareModalOK();" id="middleware_modal_ok_btn">
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
		if ($('#middleware').val()=="#" || $('#middleware').val()==""){
			$("#tip_middleware_btn_group").tips({
				side:3,
	            msg:'请选择中间件',
	            bg:'#AE81FF',
	            time:2
	        });
			return false;
		}
		$('#middleware_msg').val($('#middleware_textarea').val());
		$('#btn_add_middleware').text('修改');
		$('#v_middleware').html("<b>你选择的中间件: "+ $('#middleware').val()+ "</b>")
		$('#middleware_modal').modal('hide');
	}
	
	function vmModalOK(){
		if ($('#vm').val()=="#" || $('#vm').val()==""){
			$("#tip_vm_btn_group").tips({
				side:3,
	            msg:'请选择虚拟机',
	            bg:'#AE81FF',
	            time:2
	        });
			return false;
		}
		$('#vm_msg').val($('#vm_textarea').val());
		$('#btn_add_vm').text('修改');
		$('#v_vm').html("<b>你选择的数据库: "+ $('#vm').val()+ "</b>")
		$('#vm_modal').modal('hide');
	}

	//保存
	function save(){
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
		if($("#oper_type").val()==""){
			$("#tip_oper_type").tips({
				side:3,
	            msg:'选择操作类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#oper_type").focus();
			return false;
		}
		if($("#vm").val()==""){
			$("#tip_vm").tips({
				side:3,
	            msg:'选择虚拟机',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#btn_add_vm").focus();
			return false;
		}
		if($("#middleware").val()==""){
			$("#tip_middleware").tips({
				side:3,
	            msg:'选择中间件',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#btn_add_middleware").focus();
			return false;
		}
		
		$("#subOperService").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		
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
	}  
</script>
</html>