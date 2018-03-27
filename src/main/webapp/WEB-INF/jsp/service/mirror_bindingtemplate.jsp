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
							<div id="zhongxin" style="padding-top: 13px;">
							<div class="span6" style="padding-top: 13px;">
								<input type="hidden" name="id" id="id" value="${pd.id}"/>
								<input type="hidden" name="type" id="type" value="${pd.type}"/>
								<input type="hidden" name="cloudplatform_id" id="cloudplatform_id" value="${pd.cloudplatform_id}"/>
								<div class="tabbable">
									<ul class="nav nav-tabs" id="myTab">
										<li id="notbind_li"><a id="notbind_a" data-toggle="tab" href="#notbind"><i class="green icon-cog bigger-110"></i>未绑定模板</a></li>
										<li id="alreadybind_li"><a id="alreadybind_a" data-toggle="tab" href="#alreadybind"><i class="green icon-cog bigger-110"></i>已绑定模板</a></li>
									</ul>
									<div class="tab-content" style="height:100%;">
										<div id="notbind" class="tab-pane in active" style="height:350px;">
											<table id="table_notbind" class="table table-striped table-bordered table-hover">
												<iframe name="notbindFrame" id="notbindFrame" frameborder="0" style="margin:0 auto;width:100%;height:100%;"></iframe>
											</table>
										</div>
										<div id="alreadybind" class="tab-pane" style="height:350px;">
											<table id="table_alreadybind" class="table table-striped table-bordered table-hover">
												<iframe name="alreadybindFrame" id="alreadybindFrame" frameborder="0" src="<%=basePath%>/mirror/listAlreadyBind.do?cloudplatform_id=${pd.cloudplatform_id}&type=${pd.type}&id=${pd.id}" style="margin:0 auto;width:100%;height:100%;"></iframe>
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
			$("#notbindFrame").attr("src", "<%=basePath%>mirror/listNotbind.do?cloudplatform_id=${pd.cloudplatform_id}&type=${pd.type}&id=${pd.id}");
			$("#notbind_li").attr("class", "active");
		});
		
		//发送Ajax请求
		function ajaxHttpPost(url, jsonObj, tabId){
		    $.ajax({
			    type: 'post',  
			    url: url,
			    data: jsonObj,
			    dataType: 'json',  
			    success: function(data){
			    	$(top.hangge());
			    	location.reload();
			    },
			    error: function(data) {
			    	$(top.hangge());//关闭加载状态
			    	location.reload();
			    }
			});
		}
	</script>


</body>
</html>