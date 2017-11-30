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

						<div class="page-header">
						<h1>
								工单查看
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									工单号：${workorder.appNo}
								</small>
							</h1>
						</div><!-- /.page-header -->
						
						<div class="row">
							<div class="col-sm-2">
							</div>
							<div class="col-sm-8">
								<!-- PAGE CONTENT BEGINS -->
								
								<div class="widget-header">
									<form class="form-horizontal" id="checkform" name="checkform" role="form">
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 工单编号: </label>

										<div class="col-sm-6">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> ${workorder.appNo} </label>
										</div>
										</div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 运维申请单编号: </label>

										<div class="col-sm-6">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> ${workorder.orderNo} </label>
										</div>
									</div>
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn" id="cancel" name="cancel" type="reset" onclick="top.Dialog.close();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												确定
											</button>
										</div>
									</div>
									</form>
								</div>
							</div>
							<div class="col-sm-2">
							</div>
						</div>

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
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
	//检索
	function tosearch(){
		top.jzts();
		$("#Form").submit();
	}
	$(function() {
		//复选框全选控制
		var active_class = 'active';
		$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
			var th_checked = this.checked;//checkbox inside "TH" table header
			$(this).closest('table').find('tbody > tr').each(function(){
				var row = this;
				if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
				else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
			});
		});
	});
	
	

	</script>

</body>
</html>