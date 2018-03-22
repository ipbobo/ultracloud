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
					
					<form action="datacenternetwork/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<input type="hidden" name="type" id="type" value="${pd.type}" />
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input type="hidden" name="ippool" id="ippool" value="${pd.ippool}"/>
						<input type="hidden" name="gateway" id="gateway" value="${pd.gateway}"/>
						<input type="hidden" name="mask" id="mask" value="${pd.mask}"/>
						<input type="hidden" name="dns1" id="dns1" value="${pd.dns1}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;网络名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;资源池:</td>
								<td id="js">
									<select class="chosen-select form-control" name="cluster_id" id="cluster_id" style="vertical-align:top;" style="width:98%;" >
										<c:forEach items="${clusterList}" var="var">
											<option value="${var.id }" <c:if test="${var.id  == pd.cluster_id }">selected</c:if>>${var.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;数据中心:</td>
								<td id="cluster_td">
									<select class="chosen-select form-control" name="datacenter_id" id="datacenter_id" style="vertical-align:top;" style="width:98%;" >
										<c:forEach items="${varList}" var="var">
											<option value="${var.id }" <c:if test="${var.id  == pd.datacenter_id }">selected</c:if>>${var.name }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<!-- 
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;网络区域:</td>
								<td id="js">
									<select  class="chosen-select form-control" name="area" id="area" value="${pd.type}" data-placeholder="请选择网络区域" style="vertical-align:top;" >
									<option <c:if test="${pd.area == 'area1' }">selected="selected"</c:if> value="area">area1</option>
									<option <c:if test="${pd.area == 'area2' }">selected="selected"</c:if>  value="cluster2">area2</option>
									</select>
								</td>
							</tr>
							-->
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;网络标签:</td>
								<td id="js">
									<select  class="chosen-select form-control" name="vlan" id="vlan" value="${pd.type}" data-placeholder="请选择网络标签" style="vertical-align:top;" >
									<option <c:if test="${pd.vlan == 'vlan001' }">selected="selected"</c:if> value="cluster1">vlan001</option>
									<option <c:if test="${pd.vlan == 'vlan002' }">selected="selected"</c:if>  value="cluster2">vlan002</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;IP地址段:</td>
								<td>
								<input type="text" name="ippool_1" id="ippool_1" maxlength="3" style="width:50px;"/>.
								<input type="text" name="ippool_2" id="ippool_2" maxlength="3" style="width:50px;"/>.
								<input type="text" name="ippool_3" id="ippool_3" maxlength="3" style="width:50px;"/>.
								<input type="text" name="ippool_4" id="ippool_4" maxlength="3" style="width:50px;"/>&nbsp;—
								<input type="text" name="ippool_5" id="ippool_5" maxlength="3" style="width:50px;"/>
								</td>
							</tr>
							<tr>
								<td style="width:150px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;网关:</td>
								<td>
								<input type="text" name="gateway_1" id="gateway_1" maxlength="3" style="width:50px;"/>.
								<input type="text" name="gateway_2" id="gateway_2" maxlength="3" style="width:50px;"/>.
								<input type="text" name="gateway_3" id="gateway_3" maxlength="3" style="width:50px;"/>.
								<input type="text" name="gateway_4" id="gateway_4" maxlength="3" style="width:50px;"/>
								</td>
							</tr>
							<tr>
								<td style="width:160;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;网络掩码:</td>
								<td>
								<input type="text" name="mask_1" id="mask_1" maxlength="3" style="width:50px;"/>.
								<input type="text" name="mask_2" id="mask_2" maxlength="3" style="width:50px;"/>.
								<input type="text" name="mask_3" id="mask_3" maxlength="3" style="width:50px;"/>.
								<input type="text" name="mask_4" id="mask_4" maxlength="3" style="width:50px;"/>
								</td>
							</tr>
							<tr>
								<td style="width:160px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;DNS:</td>
								<td>
								<input type="text" name="dns_1" id="dns_1" maxlength="3" style="width:50px;"/>.
								<input type="text" name="dns_2" id="dns_2" maxlength="3" style="width:50px;"/>.
								<input type="text" name="dns_3" id="dns_3" maxlength="3" style="width:50px;"/>.
								<input type="text" name="dns_4" id="dns_4" maxlength="3" style="width:50px;"/>
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
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#ippool_1").val()==""){
				$("#ippool_1").tips({
					side:3,
		            msg:'请输入IP地址段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ippool_1").focus();
			return false;
			}
			if($("#ippool_2").val()==""){
				$("#ippool_2").tips({
					side:3,
		            msg:'请输入IP地址段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ippool_2").focus();
			return false;
			}
			if($("#ippool_3").val()==""){
				$("#ippool_3").tips({
					side:3,
		            msg:'请输入IP地址段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ippool_3").focus();
			return false;
			}
			if($("#ippool_4").val()==""){
				$("#ippool_4").tips({
					side:3,
		            msg:'请输入IP地址段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ippool_4").focus();
			return false;
			}
			if($("#ippool_5").val()==""){
				$("#ippool_5").tips({
					side:3,
		            msg:'请输入IP地址段',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ippool_5").focus();
			return false;
			}
			if($("#gateway_1").val()==""){
				$("#gateway_1").tips({
					side:3,
		            msg:'请输入网关',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#gateway_1").focus();
			return false;
			}
			if($("#gateway_2").val()==""){
				$("#gateway_2").tips({
					side:3,
		            msg:'请输入网关',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#gateway_2").focus();
			return false;
			}
			if($("#ippool_3").val()==""){
				$("#ippool_3").tips({
					side:3,
		            msg:'请输入网关',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ippool_3").focus();
			return false;
			}
			if($("#gateway_4").val()==""){
				$("#gateway_4").tips({
					side:3,
		            msg:'请输入网关',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#gateway_4").focus();
			return false;
			}
			if($("#mask_1").val()==""){
				$("#mask_1").tips({
					side:3,
		            msg:'请输入网络掩码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mask_1").focus();
			return false;
			}
			if($("#mask_2").val()==""){
				$("#mask_2").tips({
					side:3,
		            msg:'请输入网络掩码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mask_2").focus();
			return false;
			}
			if($("#mask_3").val()==""){
				$("#mask_3").tips({
					side:3,
		            msg:'请输入网络掩码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mask_3").focus();
			return false;
			}
			if($("#mask_4").val()==""){
				$("#mask_4").tips({
					side:3,
		            msg:'请输入网络掩码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#mask_4").focus();
			return false;
			}
			if($("#dns_1").val()==""){
				$("#dns_1").tips({
					side:3,
		            msg:'请输入DNS',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#dns_1").focus();
			return false;
			}
			if($("#dns_2").val()==""){
				$("#dns_2").tips({
					side:3,
		            msg:'请输入DNS',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#dns_2").focus();
			return false;
			}
			if($("#dns_3").val()==""){
				$("#dns_3").tips({
					side:3,
		            msg:'请输入DNS',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#dns_3").focus();
			return false;
			}
			if($("#dns_4").val()==""){
				$("#dns_4").tips({
					side:3,
		            msg:'请输入DNS',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#dns_4").focus();
			return false;
			}

			$("#ippool").val($("#ippool_1").val()+"."+$("#ippool_2").val()+"."+$("#ippool_3").val()+"."+$("#ippool_4").val()+"-"+$("#ippool_5").val());
			$("#gateway").val($("#gateway_1").val()+"."+$("#gateway_2").val()+"."+$("#gateway_3").val()+"."+$("#gateway_4").val());
			$("#mask").val($("#mask_1").val()+"."+$("#mask_2").val()+"."+$("#mask_3").val()+"."+$("#mask_4").val());
			$("#dns1").val($("#dns_1").val()+"."+$("#dns_2").val()+"."+$("#dns_3").val()+"."+$("#dns_4").val());

			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>