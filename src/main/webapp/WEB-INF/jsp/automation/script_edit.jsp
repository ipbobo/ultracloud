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
					
					<form action="script/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">类型:</td>
								<td>
									<select name="type" title="类型">
										<option value="2" <c:if test="${pd.type == '2' }">selected</c:if> >执行批处理</option>
										<option value="1" <c:if test="${pd.type == '1' }">selected</c:if> >创建帐号</option>
										<option value="0" <c:if test="${pd.type == '0' }">selected</c:if> >安装软件</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">脚本key:</td>
								<td><input type="text" name="script_key" id="script_key" value="${pd.script_key}" maxlength="30" placeholder="这里输入脚本key" title="脚本key" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">用途:</td>
								<td><input type="text" name="purpose" id="purpose" value="${pd.purpose}" maxlength="30" placeholder="这里输入用途" title="用途" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">路径:</td>
								<td><input type="text" name="url" id="url" value="${pd.url}" maxlength="30" placeholder="这里输入路径" title="路径" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">关联介质:</td>
								<td id="js">
									<select class="chosen-select form-control" name="medium_id" id="medium_id" data-placeholder="请选择介质" style="vertical-align:top;"  title="介质" style="width:98%;" >
									<option value=""></option>
									<c:forEach items="${mediumList}" var="medium">
										<option value="${medium.id }" <c:if test="${medium.id == pd.medium_id }">selected</c:if>>${medium.name }</option>
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
			if($("#script_key").val()==""){
				$("#script_key").tips({
					side:3,
		            msg:'请输入脚本key',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#script_key").focus();
			return false;
			}
			if($("#purpose").val()==""){
				$("#purpose").tips({
					side:3,
		            msg:'请输入用途',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#purpose").focus();
			return false;
			}
			if($("#url").val()==""){
				$("#url").tips({
					side:3,
		            msg:'请输入路径',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#url").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		</script>
</body>
</html>