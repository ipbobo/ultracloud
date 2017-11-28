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
						
						<form action="leave/startWorkflow.do" name="Form" id="Form" method="post">
							<div id="zhongxin" style="padding-top: 13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">类型:</td>
								<td id="js">
									<select class="chosen-select form-control" name="type" id=type data-placeholder="请选择类型" style="vertical-align:top;"  title="类型"  >
									<option value="nj_holiday">年假</option>
									<option value="gs_holiday">工伤假</option>
									<option value="bing_holiday">病假</option>
									<option value="shi_holiday">事假</option>
									<option value="shenyu_holiday">生育假</option>
									<option value="hun_holiday">婚假</option>
									</select>
								</td>
								</tr>
								<tr>
								<td style="width:120px;height:130px;text-align: right;padding-top: 13px;">请假原因:</td>
									<td >
										<textarea rows="5" cols="10" id="detail" name="detail" style="width:98%;"></textarea>
									</td>
								</tr>
								<tr>
									<td style="text-align: center;" colspan="10">
										<button class="btn btn-mini btn-primary" type="submit" onclick="submitApply()">提交</button>
									</td>
								</tr>
							</table>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

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
	$(top.hangge());//关闭加载状态
	
	</script>

</body>
</html>