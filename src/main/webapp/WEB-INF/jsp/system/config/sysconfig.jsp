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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
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
								<input type="hidden" name="result" id="result" value="${result}">
								<form action="sysconfig/update.do" name="sysconfigForm" id="sysconfigForm"  enctype="multipart/form-data" method="post">
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">系统名称:</td>
											<td><input type="text" name="sys_name" id="sys_name" value="${pd.sysName}" maxlength="32" placeholder="这里输入系统名称" title="系统名称" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">系统LOGO:</td>
											<td><img src="<%=basePath%>${pd.logo}" width="210"/> <input type="file" id="logo" name="logo" value="" /> </td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">公司名称:</td>
											<td><input type="text" name="company_name" id="company_name" value="${pd.companyName}" maxlength="32" placeholder="这里输入公司名称" title="公司名称" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">公司简称:</td>
											<td><input type="text" name="company_shortName" id="company_shortName" value="${pd.companyShortName}" maxlength="32" placeholder="这里输入公司简称" title="公司简称" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">咨询电话:</td>
											<td><input type="text" name="tel" id="tel" value="${pd.tel}" maxlength="32" placeholder="这里输入咨询电话" title="咨询电话" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">网站:</td>
											<td><input type="text" name="website" id="website" value="${pd.website}" maxlength="32" placeholder="这里输入网站" title="网站" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">版权信息:</td>
											<td><input type="text" name="copr" id="copr" value="${pd.copr}" maxlength="32" placeholder="这里输入版权信息" title="版权信息" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
								</form>
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
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if ($("#result").val() != null && $("#result").val() != ""){
			showDialog($("#result").val());
		}
	});
	//保存
	function save(){
		if($("#sys_name").val()==""){
			showDialog("系统名称不能为空");
			return false;
		}
		var logo = document.getElementById("logo").value;
		if (logo != null && logo != ''){
			if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(logo))
	        {
				showDialog("图片类型必须是.gif,jpeg,jpg,png中的一种")
	          return false;
	        }
		}
		
		$("#sysconfigForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		
	}
	
	
	
</script>
</html>