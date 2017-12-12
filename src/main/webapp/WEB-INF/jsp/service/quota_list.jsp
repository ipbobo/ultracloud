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
	<input type="hidden" id="ret_msg" name="ret_msg" value="${requestScope.retMsg}" />
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
										<li class="active"><a data-toggle="tab" href="#snapshoot"><i class="green icon-home bigger-110"></i>快照配额</a></li>
										<li><a data-toggle="tab" href="#department"><i class="green icon-cog bigger-110"></i>部门配额</a></li>
										<li><a data-toggle="tab" href="#project"><i class="green icon-cog bigger-110"></i>项目配额</a></li>
									</ul>
									<div class="tab-content">
										<div id="snapshoot" class="tab-pane in active">
											<table id="table_snapshoot" class="table table-striped table-bordered table-hover" style="width:50%;">
												<tr>
													<td
														style="width: 140px; text-align: right; padding-top: 13px;">云主机手动快照数量:</td>
													<td><select class="chosen-select form-control"
														name="snapshoot_manual_num" id="snapshoot_manual_num"
														style="vertical-align: top;">
															<option value="1"
																<c:if test="${pd.snapshoot_manual_num == 1 }">selected</c:if>>1个</option>
															<option value="2"
																<c:if test="${pd.snapshoot_manual_num == 2 }">selected</c:if>>2个</option>
															<option value="3"
																<c:if test="${pd.snapshoot_manual_num == 3 }">selected</c:if>>3个</option>
															<option value="4"
																<c:if test="${pd.snapshoot_manual_num == 4 }">selected</c:if>>4个</option>
															<option value="5"
																<c:if test="${pd.snapshoot_manual_num == 5 }">selected</c:if>>5个</option>
															<option value="6"
																<c:if test="${pd.snapshoot_manual_num == 6 }">selected</c:if>>6个</option>
															<option value="7"
																<c:if test="${pd.snapshoot_manual_num == 7 }">selected</c:if>>7个</option>
															<option value="8"
																<c:if test="${pd.snapshoot_manual_num == 8 }">selected</c:if>>8个</option>
															<option value="9"
																<c:if test="${pd.snapshoot_manual_num == 9 }">selected</c:if>>9个</option>
															<option value="10"
																<c:if test="${pd.snapshoot_manual_num == 10 }">selected</c:if>>10个</option>
													</select></td>
												</tr>
												<tr>
													<td
														style="width: 140px; text-align: right; padding-top: 13px;">云主机自动快照数量:</td>
													<td><select class="chosen-select form-control"
														name="snapshoot_auto_num" id="snapshoot_auto_num"
														style="vertical-align: top;">
															<option value="1"
																<c:if test="${pd.snapshoot_auto_num == 1 }">selected</c:if>>1个</option>
															<option value="2"
																<c:if test="${pd.snapshoot_auto_num == 2 }">selected</c:if>>2个</option>
															<option value="3"
																<c:if test="${pd.snapshoot_auto_num == 3 }">selected</c:if>>3个</option>
															<option value="4"
																<c:if test="${pd.snapshoot_auto_num == 4 }">selected</c:if>>4个</option>
															<option value="5"
																<c:if test="${pd.snapshoot_auto_num == 5 }">selected</c:if>>5个</option>
															<option value="6"
																<c:if test="${pd.snapshoot_auto_num == 6 }">selected</c:if>>6个</option>
															<option value="7"
																<c:if test="${pd.snapshoot_auto_num == 7 }">selected</c:if>>7个</option>
															<option value="8"
																<c:if test="${pd.snapshoot_auto_num == 8 }">selected</c:if>>8个</option>
															<option value="9"
																<c:if test="${pd.snapshoot_auto_num == 9 }">selected</c:if>>9个</option>
															<option value="10"
																<c:if test="${pd.snapshoot_manual_num == 10 }">selected</c:if>>10个</option>
													</select></td>
												</tr>
												<tr>
													<td style="text-align: center;" colspan="10"><a class="btn btn-mini btn-primary" onclick="save();">保存</a>
														<a class="btn btn-mini btn-danger" onclick="flush();">取消</a></td>
												</tr>
											</table>
										</div>
										<div id="department" class="tab-pane">
											<table id="table_department"
												class="table table-striped table-bordered table-hover">
												bbbbbbbbbbbbbbbbbbbb
											</table>
										</div>
										<div id="project" class="tab-pane">
											<table id="table_project"
												class="table table-striped table-bordered table-hover">
												ccccccccccccccccccc
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
		
		//发送Ajax请求
		function ajaxHttpPost(url, jsonObj, tabId){
		    $.ajax({
			    type: 'post',  
			    url: url,
			    data: jsonObj,
			    dataType: 'json',  
			    success: function(data){
			    	$(top.hangge());//关闭加载状态
			    	/* var result = $('#ret_msg').val();
			    	debugger();
					if (result != ''){
						showDialog(result);
					} */
			    },
			    error: function(data) {
			    	$(top.hangge());//关闭加载状态
			    }
			});
		}
		
		//点击tab页
		function tabFunc(tabId){
			if(tabId=="zdysq"){//自定义申请
				$("#savePckgBtnId").show();
				return;
			}else{//套餐申请
				$("#savePckgBtnId").hide();
				$("#tcsq").load("pckgAppPre.do");
			}
		}
		
		//保存
		function save(){
			top.jzts();
			var jsonObj={};//JSON请求数据
			jsonObj.snapshoot_manual_num=$("#snapshoot_manual_num").val();            
			jsonObj.snapshoot_auto_num=$("#snapshoot_auto_num").val();                       
			ajaxHttpPost("quota/saveSnapshootMax.do", jsonObj, "snapshoot");//发送Ajax请求
		}
	</script>


</body>
</html>