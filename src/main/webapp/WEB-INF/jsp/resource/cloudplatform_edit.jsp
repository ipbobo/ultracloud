<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../system/index/top.jsp"%>
	<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="cloudplatform/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;类型:</td>
								<td id="js">
									<select disabled="disabled" class="chosen-select form-control" name="type" id="type" value="${pd.type}" data-placeholder="请选择类型" style="vertical-align:top;"  title="类型"  >
									<option <c:if test="${pd.type == 'vmware' }">selected="selected"</c:if> value="vmware">vmware</option>
									<option <c:if test="${pd.type == 'OpenStack' }">selected="selected"</c:if>  value="OpenStack">OpenStack</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;主机名(地址或域名):</td>
								<td><input type="text" name="ip" id="ip" value="${pd.ip}" maxlength="30"  style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:150px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;用户名:</td>
								<td><input type="text" name="username" id="username" value="${pd.username}" maxlength="30"  style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:160;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;密码:</td>
								<td><input type="password" name="password" id="password" value="${pd.password}" maxlength="30"  style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;确认密码:</td>
								<td><input type="password" name="confirmpassword" id="confirmpassword" value="${pd.confirmpassword}" maxlength="30" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;所属环境:</td>
								<td id="level_td">
									<select class="chosen-select form-control" name="environment_id" id=environment_id data-placeholder="请选择环境" style="vertical-align:top;"  title="环境" style="width:98%;" >
									<option value=""></option>
									<c:forEach items="${environmentList}" var="environment">
										<option value="${environment.id}" <c:if test="${environment.id == pd.environment_id }">selected</c:if>>${environment.name }</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
	
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			var str1 = '<c'+':if test="'+'$'+'{QX.'+$("#QX_NAME").val();
			var str2 = ' == 1 }">这里放按钮<'+'/c:'+'if>';
			$("#code").val(str1+str2);
		});
		//保存
		function save(){
			if($("#name").val()==""){
				$("#name").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#type").val()==""){
				$("#type").tips({
					side:3,
		            msg:'请选择类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#type").focus();
			return false;
			}
			if($("#ip").val()==""){
				$("#ip").tips({
					side:3,
		            msg:'请输入地址或域名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ip").focus();
			return false;
			}
			if($("#username").val()==""){
				$("#username").tips({
					side:3,
		            msg:'请输入用户名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#username").focus();
			return false;
			}
			if($("#password").val()==""){
				$("#password").tips({
					side:3,
		            msg:'请输入密码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#password").focus();
			return false;
			}
			if($("#confirmpassword").val()==""){
				$("#confirmpassword").tips({
					side:3,
		            msg:'请确认密码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#confirmpassword").focus();
			return false;
			}
			if($("#confirmpassword").val()!=$("#password").val()){
				$("#confirmpassword").tips({
					side:3,
		            msg:'二次输入的密码不一致',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#password").focus();
			return false;
			}
			if($("#environment_id").val()==""){
				$("#environment_id").tips({
					side:3,
		            msg:'请选择环境',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#environment_id").focus();
			return false;
			}

			$("#type").removeAttr("disabled", "disabled");
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>