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
					
					<form action="kvm/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;模板名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;存放地址:</td>
								<td><input type="text" name="url" id="url" value="${pd.url}" maxlength="30"  style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;操作系统类型:</td>
								<td id="js">
									<select class="chosen-select form-control" name="ostype" id="ostype"  style="vertical-align:top;"   >
									<option value="redhat" <c:if test="${pd.ostype == 'redhat' }">selected</c:if>>redhat</option>
									<option value="windows" <c:if test="${pd.ostype == 'windows' }">selected</c:if>>windows</option>
									<option value="centos" <c:if test="${pd.ostype == 'centos' }">selected</c:if>>centos</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">操作系统名称:</td>
								<td><input type="text" name="osname" id="osname" value="${pd.osname}" maxlength="30" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;位数:</td>
								<td><input type="number" name="bitrate" id="bitrate" value="${pd.bitrate}" maxlength="30" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="detail" id="detail" value="${pd.detail}" maxlength="30"  style="width:100%;"/></td>
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
		            msg:'请输入KVM主机名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#url").val()==""){
				$("#url").tips({
					side:3,
		            msg:'请输入存放路径',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#url").focus();
			return false;
			}
			if($("#ostype").val()==""){
				$("#ostype").tips({
					side:3,
		            msg:'请选择操作系统类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ostype").focus();
			return false;
			}
			if($("#bitrate").val()==""){
				$("#bitrate").tips({
					side:3,
		            msg:'请输入位数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#bitrate").focus();
			return false;
			}

			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>