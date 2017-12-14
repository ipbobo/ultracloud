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
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
</head>
<body class="no-skin">
	<input type="hidden" id="ret_msg" name="ret_msg"
		value="${requestScope.retMsg}" />
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">

							<div class="span6" style="padding-top: 13px;">
								<h3>虚拟机最大到期时间配置</h3>
								<table class="table table-striped table-bordered table-hover" style="width:50%;">
									<input type="hidden" name="expirationtime_isforever" id="expirationtime_isforever" value="${pd.expirationtime_isforever}"/>
									<tr height="42px;">
										<td>
											<div style="float: left; padding-top: 3px;">
												<label style="float: left; padding-left: 20px;"><input
													name="form-field-radioq" id="form-field-radio-day"
													onclick="isForEver('no');" type="radio" value="icon-edit"
													checked="checked"><span class="lbl">虚拟机最大到值时间距离现在&nbsp;</span></label>
													<input class="nav-search-input" style="width:80px;" name="expirationtime_day" id="expirationtime_day" type="number" value="${pd.expirationtime_day}" />&nbsp;天
											</div>
										</td>
									</tr>
									<tr height="42px;">
										<td>
											<div style="float: left; padding-top: 3px;">
												<label style="float: left; padding-left: 20px;"><input
													name="form-field-radioq" id="form-field-radio-forever"
													onclick="isForEver('yes');" type="radio" value="icon-edit"><span
													class="lbl">永久</span></label>
											</div>
										</td>
									</tr>
									<tr>
										<td style="text-align: center;" colspan="10"><a
											class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
											class="btn btn-mini btn-danger" onclick="flush();">取消</a></td>
									</tr>
								</table>
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
		$(function(){ 
		 	if($("#expirationtime_isforever").val() == 'yes'){
				$("#expirationtime_isforever").val('yes');
				$("#expirationtime_day").attr("disabled", true);
				$("#form-field-radio-day").removeAttr("checked", "checked");
				$("#form-field-radio-forever").attr("checked", "checked");
				
			}else{
				$("#expirationtime_isforever").val('no');
				$("#expirationtime_day").attr("disabled", false);
				$("#form-field-radio-day").attr("checked", "checked");
				$("#form-field-radio-forever").removeAttr("checked", "checked");
			}
		}); 
	
		$(top.hangge());
		
		//单选触发事件
		function isForEver(value){
			if(value == 'yes'){
				$("#expirationtime_isforever").val('yes');
				$("#expirationtime_day").attr("disabled", true);
				$("#form-field-radio-day").removeAttr("checked", "checked");
				$("#form-field-radio-forever").attr("checked", "checked");
				
			}else{
				$("#expirationtime_isforever").val('no');
				$("#expirationtime_day").attr("disabled", false);
				$("#form-field-radio-day").attr("checked", "checked");
				$("#form-field-radio-forever").removeAttr("checked", "checked");
			}
		}

		//发送Ajax请求
		function ajaxHttpPost(url, jsonObj) {
			$.ajax({
				type : 'post',
				url : url,
				data : jsonObj,
				dataType : 'json',
				success : function(data) {
					$(top.hangge());
					location.reload();
				},
				error : function(data) {
					$(top.hangge());//关闭加载状态
					location.reload();
				}
			});
		}

		//保存
		function save() {
			top.jzts();
			var jsonObj = {};//JSON请求数据
			jsonObj.expirationtime_isforever = $("#expirationtime_isforever").val();
			jsonObj.expirationtime_day = $("#expirationtime_day").val();
			ajaxHttpPost("quota/saveExpirationtime.do", jsonObj);//发送Ajax请求
		}

		//取消
		function flush() {
			console.log("quota snapshoot flush----------------->");
			location.reload();
		}
	</script>


</body>
</html>