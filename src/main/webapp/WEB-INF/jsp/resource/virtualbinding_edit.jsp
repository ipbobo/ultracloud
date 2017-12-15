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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
</head>
<body class="no-skin">
	<input type="hidden" id="ret_msg" name="ret_msg" value="${requestScope.retMsg}" />
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">

							<div class="span6" style="padding-top: 13px;">
								<h3>虚拟机绑定</h3>
								<table class="table table-striped table-bordered table-hover" style="width:50%;">
									<input type="hidden" name="expirationtime_isforever" id="expirationtime_isforever" value="${pd.expirationtime_isforever}"/>
									<tr height="42px;">
										<td style="width:120px;text-align: right;padding-top: 13px;">环境:</td>
										<td id="juese">
										<select class="chosen-select form-control" name="cluster_id" id="cluster_id" data-placeholder="请选择" style="vertical-align:top;" style="width:98%;" >
										<option value="">请选择</option>
										<c:forEach items="${clusterList}" var="cluster">
											<option value="${cluster.id }">${cluster.name }</option>
										</c:forEach>
										</select>
										</td>
									</tr>
									<tr height="42px;">
										<td style="width:120px;text-align: right;padding-top: 13px;">项目:</td>
										<td id="juese">
										<select class="chosen-select form-control" name="project_id" id="project_id" data-placeholder="请选择" style="vertical-align:top;" style="width:98%;" >
										<option value="">请选择</option>
										<c:forEach items="${projectList}" var="project">
											<option value="${project.id }">${project.name }</option>
										</c:forEach>
										</select>
										</td>
									</tr>
									<tr height="42px;">
										<td style="width:120px;text-align: right;padding-top: 13px;">使用人:</td>
										<td id="juese">
										<select class="chosen-select form-control" name="USERNAME" id="USERNAME" data-placeholder="请选择" style="vertical-align:top;" style="width:98%;" >
										<option value="">请选择</option>
										<c:forEach items="${userList}" var="user">
											<option value="${user.USERNAME }">${user.USERNAME }</option>
										</c:forEach>
										</select>
										</td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">云主机:</td>
										<td id="juese" colspan="10">
											<textarea rows="3" cols="10" id="virtualmachines" name="virtualmachines" style="width:98%;"></textarea>
											<a class="btn btn-mini btn-primary" onclick="selectHostmachine();">选择云主机</a>
										</td>
									</tr>
									<tr>
										<td style="text-align: center;" colspan="10"><a
											class="btn btn-mini btn-primary" onclick="save();">绑定</a> <a
											class="btn btn-mini btn-danger" onclick="flush();">取消</a></td>
									</tr>
								</table>
							</div>
							<!--/span-->
						</div>

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->


	<!-- 返回顶部 -->
	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-sm btn-inverse"> <i
		class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	//选择云主机
	function selectHostmachine() {
		 var cluster_id = $("#cluster_id").val();
		 var project_id = $("#project_id").val();
		 var USERNAME = $("#USERNAME").val();
		 
		 if($("#cluster_id").val()=="") {
				$("#cluster_id").tips({
					side:3,
		            msg:'请选择环境',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cluster_id").focus();
			return false;
		 }
		 if($("#project_id").val()=="") {
				$("#project_id").tips({
					side:3,
		            msg:'请选择项目',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#project_id").focus();
			return false;
		 }
		 if($("#USERNAME").val()=="") {
				$("#USERNAME").tips({
					side:3,
		            msg:'请选择使用人',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USERNAME").focus();
			return false;
		 }
		 
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="选择云主机";
		 diag.URL = '<%=basePath%>virtualbinding/goListVirtualmachine.do?cluster_id='+cluster_id;
		 diag.Width = 800;
		 diag.Height = 600;
		 diag.CancelEvent = function(){ //关闭事件
			 $("#"+USERNAME).val(diag.innerFrame.contentWindow.document.getElementById('xzvalue').value);
			 diag.close();
		 };
		 diag.show();
	}
	
	
	
		$(function(){ 
		 	if($("#expirationtime_isforever").val() == 'yes'){
				$("#expirationtime_isforever").val('yes');
				$("#expirationtime_day").attr("disabled", true);
				$("#form-field-radio-day").removeAttr("checked", "checked");
				$("#form-field-radio-forever").attr("checked", "checked");
				
			}else{
				$("#expirationtime_isforever").val('no');
				$("#expirationtime_day").attr("disabled", false);
				$("#form-field-radio-day").attr("checked", "checked");
				$("#form-field-radio-forever").removeAttr("checked", "checked");
			}
		}); 
	
		$(top.hangge());
		
		//单选触发事件
		function isForEver(value){
			if(value == 'yes'){
				$("#expirationtime_isforever").val('yes');
				$("#expirationtime_day").attr("disabled", true);
				$("#form-field-radio-day").removeAttr("checked", "checked");
				$("#form-field-radio-forever").attr("checked", "checked");
				
			}else{
				$("#expirationtime_isforever").val('no');
				$("#expirationtime_day").attr("disabled", false);
				$("#form-field-radio-day").attr("checked", "checked");
				$("#form-field-radio-forever").removeAttr("checked", "checked");
			}
		}

		//发送Ajax请求
		function ajaxHttpPost(url, jsonObj) {
			$.ajax({
				type : 'post',
				url : url,
				data : jsonObj,
				dataType : 'json',
				success : function(data) {
					$(top.hangge());
					location.reload();
				},
				error : function(data) {
					$(top.hangge());//关闭加载状态
					location.reload();
				}
			});
		}

		//保存
		function save() {
			top.jzts();
			var jsonObj = {};//JSON请求数据
			jsonObj.expirationtime_isforever = $("#expirationtime_isforever").val();
			jsonObj.expirationtime_day = $("#expirationtime_day").val();
			ajaxHttpPost("quota/saveExpirationtime.do", jsonObj);//发送Ajax请求
		}

		//取消
		function flush() {
			console.log("quota snapshoot flush----------------->");
			location.reload();
		}
	</script>


</body>
</html>