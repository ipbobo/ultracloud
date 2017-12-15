<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">

							<div class="span6" style="padding-top: 13px;">
								<div class="tabbable">
									<ul class="nav nav-tabs" id="myTab">
										<li id="vmware_li"><a id="vmware_a" data-toggle="tab" href="#vmware"><i class="green icon-cog bigger-110"></i>vmware</a></li>
										<li id="OpenStack_li"><a id="OpenStack_a" data-toggle="tab" href="#OpenStack"><i class="green icon-cog bigger-110"></i>OpenStack</a></li>
									</ul>
									<div class="tab-content" style="height:100%;">
										<div id="vmware" class="tab-pane in active" style="height:500px;">
											<table id="table_vmware" class="table table-striped table-bordered table-hover">
												<iframe name="vmwareFrame" id="vmwareFrame" frameborder="0" src="<%=basePath%>/hostmachine/listType.do?type=vmware" style="margin:0 auto;width:100%;height:100%;"></iframe>
											</table>
										</div>
										<div id="OpenStack" class="tab-pane" style="height:500px;">
											<table id="table_OpenStack" class="table table-striped table-bordered table-hover">
												<iframe name="OpenStackFrame" id="OpenStackFrame" frameborder="0" src="<%=basePath%>/hostmachine/listType.do?type=OpenStack" style="margin:0 auto;width:100%;height:100%;"></iframe>
											</table>
										</div>
									</div>
								</div>
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
		$(top.hangge());
		
		$(function() {
			$("#vmware_li").attr("class", "active");
		});
	</script>

</body>
</html>