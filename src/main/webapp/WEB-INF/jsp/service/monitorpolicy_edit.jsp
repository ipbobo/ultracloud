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
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<link rel="stylesheet" href="static/html_UI/assets/css/jquery-ui.css" />
		<script type="text/javascript" src="static/ace/js/jquery.js"></script>
		<script src="static/html_UI/assets/js/bootstrap.js"></script>
		
		<script src="static/html_UI/assets/js/jquery-ui.js"></script>
		<script src="static/html_UI/assets/js/jquery.ui.touch-punch.js"></script>

		<!-- ace scripts -->
		<script src="static/html_UI/assets/js/ace/elements.scroller.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.colorpicker.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.fileinput.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.typeahead.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.wysiwyg.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.spinner.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.treeview.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.wizard.js"></script>
		<script src="static/html_UI/assets/js/ace/elements.aside.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.ajax-content.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.touch-drag.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.sidebar.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.submenu-hover.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.widget-box.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.settings.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.settings-rtl.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.settings-skin.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.widget-on-reload.js"></script>
		<script src="static/html_UI/assets/js/ace/ace.searchbox-autocomplete.js"></script>
	
	<!-- jsp文件头和头部 -->
	<%@ include file="../system/index/top.jsp"%>
</head>
<body class="no-skin" style="width: 98%;">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container" >
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row"  >
					
					<form action="monitorpolicy/${msg }.do" name="Form1" id="Form1" method="post" >
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input type="hidden" name="DATA_IDS" id="DATA_IDS" />
						<div id="zhongxin" style="padding-top: 13px;padding-left: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover" style="padding-left: 13px;">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;监控指标:</td>
								<td><input readonly="readonly" type="text" name="name" id="name" value="${pd.name}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;统计周期:</td>
								<td><input type="text" name="cycle" id="cycle" value="${pd.cycle}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:50%;"/>&nbsp;天</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;阀值范围:</td>
								<td style="padding-top: 13px;">
								<input readonly="readonly" type="number" name="min" id="min" value="${pd.min}" maxlength="2" title="最小值" style="width:30%;"/>&nbsp;-&nbsp;
								<input readonly="readonly" type="number" name="max" id="max" value="${pd.max}" maxlength="2" title="最大值" style="width:30%;"/>
								<!-- <p style="vertical-align: middle;">前后输入框分别为阀值更好限和上限</p> -->
								<div style="padding-top: 5px;">
									<div class="space-4"></div>
									<div id="slider"></div>
								</div>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;是否邮件通知:</td>
								<td id="js">
									<select class="chosen-select form-control" name="isemail" id="isemail"  style="vertical-align:top;width:50%;" >
									<option value="1" <c:if test="${pd.isemail == 1 }">selected</c:if>>是</option>
									<option value="0" <c:if test="${pd.isemail == 0 }">selected</c:if>>否</option>
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			$( "#slider" ).slider({
				range: true,
				min: 0,
				max: 100,
				values: [ 10, 80 ],
				slide: function( event, ui ) {
			        $( "#min" ).val(ui.values[ 0 ]);
			        $( "#max" ).val(ui.values[ 1 ]);
			    }
			});

		});
		
		//拼代码
		function changecode(value){
			var str1 = '<c'+':if test="'+'$'+'{QX.';
			var str2 = ' == 1 }">这里放按钮<'+'/c:'+'if>';
			$("#code").val(str1 + value +str2);
		}
		
		//保存
		function save() {
			if($("#name").val()==""){
				$("#name").tips({
					side:3,
		            msg:'请输入项目名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#name").focus();
			return false;
			}
			
			$("#Form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		</script>
</body>
</html>