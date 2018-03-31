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
								<td style="width:140px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;环境名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:140px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;挂载云磁盘数量:</td>
								<td id="disknum_td">
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
								<td style="width:140px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;每块云磁盘最大值:</td>
								<td id="diskmaximum_td">
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
								<td style="width:140px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;安装软件数量:</td>
								<td id="softnum_td">
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
								<td style="width:140px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;数据中心:</td>
								<td id="datacenter_td">
									<select class="chosen-select form-control" name="datacenter_id" id="datacenter_id" data-placeholder="请选择" style="vertical-align:top;"  title="数据中心" >
									<c:forEach items="${dcList}" var="datacenter">
										<option value="${datacenter.id }" <c:if test="${datacenter.id == pd.datacenter_id }">selected</c:if>>${datacenter.name }</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:140px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;资源到期选择:</td>
								<td id="duetopolicy_td">
									<select class="chosen-select form-control" name="duetopolicy" id="duetopolicy"  style="vertical-align:top;"   >
									<option value="auto_recycle" <c:if test="${pd.duetopolicy == 'auto_recycle' }">selected</c:if>>资源到期自动回收</option>
									<option value="auto_shutdown" <c:if test="${pd.duetopolicy == 'auto_shutdown' }">selected</c:if>>资源到期自动关机</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:140px;text-align: right;padding-top: 13px;">地域:</td>
								<td id="is_initsnapshoot_td">
									<div class="col-sm-9">
										<input type="hidden" id="DATA_IDS" name="DATA_IDS"/>
										<c:choose>
											<c:when test="${not empty areaList}">
												<c:forEach items="${areaList}" var="var" varStatus="vs">
													<label style="padding-left: 8px;padding-top:7px;">
										        	<input name="ids" type="checkbox" class="ace" value="${var.id}" <c:if test="${var.isSelect == 'select' }">checked="checked"</c:if> /><span class="lbl">${var.name}</span>
												</c:forEach>
											</c:when>
										</c:choose>
									    </label>
									</div>
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
			if($("#disknum").val()==""){
				$("#disknum_td").tips({
					side:3,
		            msg:'请选择挂载云磁盘数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#disknum").focus();
			return false;
			}
			if($("#diskmaximum").val()==""){
				$("#diskmaximum_td").tips({
					side:3,
		            msg:'请选择每块云磁盘最大值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#diskmaximum").focus();
			return false;
			}
			if($("#softnum").val()==""){
				$("#softnum_td").tips({
					side:3,
		            msg:'请选择安装软件数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#softnum").focus();
			return false;
			}
			if($("#datacenter_id").val()==""){
				$("#datacenter_td").tips({
					side:3,
		            msg:'请选择数据中心',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#datacenter_id").focus();
			return false;
			}
			if($("#duetopolicy").val()==""){
				$("#duetopolicy_td").tips({
					side:3,
		            msg:'请选择资源到期策略',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#duetopolicy").focus();
			return false;
			}
			
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++){
			  if(document.getElementsByName('ids')[i].checked){
			  	if(str=='') str += document.getElementsByName('ids')[i].value;
			  	else str += ',' + document.getElementsByName('ids')[i].value;
			  }
			}
			$("#DATA_IDS").val(str);
			
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>