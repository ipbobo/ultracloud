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
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
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
					
					<form action="product/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						<input type="hidden" name="isImediate" id="isImediate" value="${pd.isImediate}"/>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;产品名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">产品类型:</td>
								<td id="js">
									<select class="chosen-select form-control" name="type" id="type"  style="vertical-align:top;" disabled="disabled"  >
									<option value="cpu" <c:if test="${pd.type == 'cpu' }">selected</c:if>>CPU</option>
									<option value="memory" <c:if test="${pd.type == 'memory' }">selected</c:if>>内存</option>
									<option value="disk" <c:if test="${pd.type == 'disk' }">selected</c:if>>磁盘</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;计价单位:</td>
								<td>
								<input type="number" name="unit" id="unit" value="${pd.unit}" maxlength="30" style="width:70%;"/>
								<c:if test="${pd.type == 'cpu' }">核</c:if>
								<c:if test="${pd.type == 'memory' }">G</c:if>
								<c:if test="${pd.type == 'disk' }">G</c:if>
								</td>
							</tr>
							<c:if test="${pd.price_cur != null}">
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">旧价格:</td>
									<td><input  readonly="true" type="number" name="price_old" id="price_old" value="${pd.price_old}" maxlength="30" style="width:70%;"/>&nbsp;元/天</td>
								</tr>
								<tr>
									<td style="width:130px;text-align: right;padding-top: 13px;">当前价格:</td>
									<td><input readonly="true" type="number" name="price_cur" id="price_cur" value="${pd.price_cur}" maxlength="30" style="width:70%;"/>&nbsp;元/天</td>
								</tr>
							</c:if>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;新价格:</td>
								<td><input type="number" name="price_new" id="price_new" value="${pd.price_new}" maxlength="30" style="width:70%;"/>&nbsp;元/天</td>
							</tr>
							<tr>
								<td style="width:130px;text-align: right;padding-top: 13px;">新价格生效日期:</td>
								<td>
									<div class="col-sm-6">
										<label style="float:left;padding-left: 8px;padding-top:7px;">
											<input name="form-field" type="radio" class="ace" id="form-field-radio-yes" onclick="isImediateRadio('yes');"/>
											<span class="lbl">立即生效</span>
										</label>
										<label style="float:left;padding-left: 5px;padding-top:7px;">
											<input name="form-field" type="radio" class="ace" id="form-field-radio-no" onclick="isImediateRadio('no');"/>
											<span class="lbl">设定日期</span>
										</label>
										<label style="float:left;padding-left: 5px;padding-top:3px;">
											<input class="span10 date-picker" name="clockTime" id="clockTime" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:120px;" placeholder="日期" title="日期">
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
		
		$(function(){ 
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
			
		 	if($("#isImediate").val() == '1'){
				$("#isImediate").val('1');
				$("#clockTime").val('');
				$("#form-field-radio-no").removeAttr("checked", "checked");
				$("#form-field-radio-yes").attr("checked", "checked");
				
			}else{
				$("#isImediate").val('0');
				//$("#expirationtime_day").attr("disabled", false);
				$("#form-field-radio-no").attr("checked", "checked");
				$("#form-field-radio-yes").removeAttr("checked", "checked");
			}
		}); 
		
		//单选触发事件
		function isImediateRadio(value) {
			if(value == 'yes'){
				$("#isImediate").val('1');
				$("#clockTime").val('');
				$("#form-field-radio-no").removeAttr("checked", "checked");
				$("#form-field-radio-yes").attr("checked", "checked");
				
			}else{
				$("#isImediate").val('0');
				//$("#expirationtime_day").attr("disabled", false);
				$("#form-field-radio-no").attr("checked", "checked");
				$("#form-field-radio-yes").removeAttr("checked", "checked");
			}
		}
		
		//保存
		function save(){
			if($("#name").val()==""){
				$("#name").tips({
					side:3,
		            msg:'请输入产品名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			if($("#unit").val()==""){
				$("#unit").tips({
					side:3,
		            msg:'请输入计价单位',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#unit").focus();
			return false;
			}
			if($("#price_new").val()==""){
				$("#price_new").tips({
					side:3,
		            msg:'请输入新价格',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#price_new").focus();
			return false;
			}
			
			$("#type").removeAttr("disabled", "disabled");
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>