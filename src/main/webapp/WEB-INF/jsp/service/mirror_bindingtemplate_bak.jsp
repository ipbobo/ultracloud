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
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<!-- 左右双列 start -->
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/ultracloud/static/html_UI/assets/css/prettify.css">
	<link rel="stylesheet" href="static/html_UI/assets/css/bootstrap-duallistbox.css" />
	<!-- <script src="http://www.jq22.com/jquery/2.1.1/jquery.min.js"></script> -->
	<script src="js/bootstrap.min.js"></script>
	<script src="static/html_UI/assets/js/jquery.bootstrap-duallistbox.js"></script>
	<!-- 左右双列 end -->

</head>

<body>

	<div class="col-md-12">
		<form id="demoform" action="mirror/bindingtemplate.do" method="post">
			<input type="hidden" name="DATA_IDS" id="DATA_IDS" /> <input
				type="hidden" name="id" id="id" value="${pd.id}" /> <br> <select
				multiple="multiple" size="5" name="duallistbox_demo1[]"
				class="demo2">
				<c:forEach items="${notBindingList}" var="mirrortemplate">
					<option value="${mirrortemplate.id }">${mirrortemplate.name }/${mirrortemplate.osname }/${mirrortemplate.bitrate }位</option>
				</c:forEach>
				<c:forEach items="${bindedList}" var="mirrortemplate">
					<option value="${mirrortemplate.id }" selected="selected">${mirrortemplate.name }/${mirrortemplate.osname }/${mirrortemplate.bitrate }位</option>
				</c:forEach>
			</select> <br>

			<!-- <button type="submit" class="btn btn-default btn-block">Submit data</button> -->
			<div class="col-md-8 col-sm-offset-2 text-center">
				<button type="submit" class="btn btn-mini btn-primary">保存</button>
				<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
			</div>
			<div id="zhongxin" style="padding-top: 13px;"></div>
		</form>
	</div>
	<script>
		$(top.hangge());
		var demo1 = $('select[name="duallistbox_demo1[]"]')
				.bootstrapDualListbox({
					nonSelectedListLabel : '未加入模板',
					selectedListLabel : '已加入模板',
					preserveSelectionOnMove : 'moved',
					moveOnSelect : true,
					nonSelectedFilter : '',
					infoText : '',
					filterPlaceHolder : '可输入模板名称过滤',
				});

		$("#demoform").submit(function() {
			$("#DATA_IDS").val($('[name="duallistbox_demo1[]"]').val());
			//alert($('[name="duallistbox_demo1[]"]').val());
			return true;
		});
	</script>

</body>
</html>