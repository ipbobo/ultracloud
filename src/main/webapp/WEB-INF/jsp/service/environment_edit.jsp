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
	<script type="text/javascript" src="static/ace/js/jquery.js"></script>
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
					
					<form action="environment/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">环境名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">挂载云磁盘数量:</td>
								<td id="js">
									<select class="chosen-select form-control" name="disknum" id="disknum"  style="vertical-align:top;"   >
									<option value="1" <c:if test="${pd.disknum == 1 }">selected</c:if>>1块</option>
									<option value="2" <c:if test="${pd.disknum == 2 }">selected</c:if>>2块</option>
									<option value="3" <c:if test="${pd.disknum == 3 }">selected</c:if>>3块</option>
									<option value="4" <c:if test="${pd.disknum == 4 }">selected</c:if>>4块</option>
									<option value="5" <c:if test="${pd.disknum == 5 }">selected</c:if>>5块</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">每块云磁盘最大值:</td>
								<td id="js">
									<select class="chosen-select form-control" name="diskmaximum" id="diskmaximum"  style="vertical-align:top;"   >
									<option value="600" <c:if test="${pd.diskmaximum == 600 }">selected</c:if>>600GB</option>
									<option value="800" <c:if test="${pd.diskmaximum == 800 }">selected</c:if>>800GB</option>
									<option value="1000" <c:if test="${pd.diskmaximum == 1000 }">selected</c:if>>1000GB</option>
									<option value="1500" <c:if test="${pd.diskmaximum == 1500 }">selected</c:if>>1500GB</option>
									<option value="2000" <c:if test="${pd.diskmaximum == 2000 }">selected</c:if>>2000GB</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">安装软件数量:</td>
								<td id="js">
									<select class="chosen-select form-control" name="softnum" id="softnum"  style="vertical-align:top;"   >
									<option value="1" <c:if test="${pd.softnum == 1 }">selected</c:if>>1</option>
									<option value="2" <c:if test="${pd.softnum == 2 }">selected</c:if>>2</option>
									<option value="3" <c:if test="${pd.softnum == 3 }">selected</c:if>>3</option>
									<option value="4" <c:if test="${pd.softnum == 4 }">selected</c:if>>4</option>
									<option value="5" <c:if test="${pd.softnum == 5 }">selected</c:if>>5</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">数据中心:</td>
								<td id="js">
									<select class="chosen-select form-control" name="datacenter_id" id="datacenter_id" data-placeholder="请选择" style="vertical-align:top;"  title="数据中心" >
									<c:forEach items="${dcList}" var="datacenter">
										<option value="${datacenter.id }" <c:if test="${datacenter.id == pd.datacenter_id }">selected</c:if>>${datacenter.name }</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">资源到期选择:</td>
								<td id="js">
									<select class="chosen-select form-control" name="duetopolicy" id="duetopolicy"  style="vertical-align:top;"   >
									<option value="auto_recycle" <c:if test="${pd.duetopolicy == 'auto_recycle' }">selected</c:if>>资源到期自动回收</option>
									<option value="auto_shutdown" <c:if test="${pd.duetopolicy == 'auto_shutdown' }">selected</c:if>>资源到期自动关机</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">是否需要初始快照:</td>
								<td id="js">
									<select class="chosen-select form-control" name="is_initsnapshoot" id="is_initsnapshoot"  style="vertical-align:top;"   >
									<option value="0" <c:if test="${pd.is_initsnapshoot == 0 }">selected</c:if>>否</option>
									<option value="1" <c:if test="${pd.is_initsnapshoot == 1 }">selected</c:if>>是</option>
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
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
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
		            msg:'请输入环境名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#datacenter_id").val()==""){
				$("#datacenter_id").tips({
					side:3,
		            msg:'请选择数据中心',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#datacenter_id").focus();
			return false;
			}
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>