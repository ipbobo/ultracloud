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
					
					<form action="quota/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}"/>
						<input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${null == pd.PARENT_ID ? DEPARTMENT_ID:pd.PARENT_ID}"/>
						<div id="zhongxin">
						<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top:15px;">
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">cpu配额:</td>
								<td><input type="number" name="cpu_quota" id="cpu_quota" value="${pd.cpu_quota}" maxlength="50"  style="width:90%;"/>&nbsp;核</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">内存配额:</td>
								<td><input type="number" name="memory_quota" id="memory_quota" value="${pd.memory_quota}" maxlength="50"  style="width:90%;"/>&nbsp;G</td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">磁盘配额:</td>
								<td><input type="number" name="disk_quota" id="disk_quota" value="${pd.disk_quota}" maxlength="50"  style="width:90%;"/>&nbsp;G</td>
							</tr>
							<tr>
								<td class="center" colspan="10">
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			console.log("quota department_edit------------->");
			if($("#cpu_quota").val()==""){
				$("#cpu_quota").tips({
					side:3,
		            msg:'请输入cpu配额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#cpu_quota").focus();
			return false;
		}
			if($("#memory_quota").val()==""){
				$("#memory_quota").tips({
					side:3,
		            msg:'请输入内存配额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#memory_quota").focus();
			return false;
		}
			if($("#disk_quota").val()==""){
				$("#disk_quota").tips({
					side:3,
		            msg:'请输入磁盘配额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#disk_quota").focus();
			return false;
		}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		</script>
</body>
</html>