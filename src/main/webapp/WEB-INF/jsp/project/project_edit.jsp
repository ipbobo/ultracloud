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
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<!-- jsp文件头和头部 -->
	<%@ include file="../system/index/top.jsp"%>
	<!-- 树形下拉框start -->
	<script type="text/javascript" src="plugins/selectZtree/selectTree.js"></script>
	<script type="text/javascript" src="plugins/selectZtree/framework.js"></script>
	<link rel="stylesheet" type="text/css" href="plugins/selectZtree/import_fh.css"/>
	<script type="text/javascript" src="plugins/selectZtree/ztree/ztree.js"></script>
	<link type="text/css" rel="stylesheet" href="plugins/selectZtree/ztree/ztree.css"></link>
	<!-- 树形下拉框end -->
	<!-- 左右双列 start -->
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="/ultracloud/static/html_UI/assets/css/prettify.css">
	<link rel="stylesheet" href="static/html_UI/assets/css/bootstrap-duallistbox.css" />
	<!-- <script src="http://www.jq22.com/jquery/2.1.1/jquery.min.js"></script> -->
	<script src="js/bootstrap.min.js"></script>
	<script src="static/html_UI/assets/js/jquery.bootstrap-duallistbox.js"></script>
	<!-- 左右双列 end -->
</head>
<body class="no-skin" style="width: 98%;">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container" >
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row"  >
					
					<form action="project/${msg }.do" name="Form1" id="Form1" method="post" >
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input type="hidden" name="DATA_IDS" id="DATA_IDS" />
						<div id="zhongxin" style="padding-top: 13px;padding-left: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover" style="padding-left: 13px;">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">项目名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30"  style="width:98%;"/></td>
								<td style="width:120px;text-align: right;padding-top: 13px;">项目简称:</td>
								<td><input type="text" name="shortname" id="shortname" value="${pd.shortname}" maxlength="30" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">项目等级:</td>
								<td id="js">
									<select class="chosen-select form-control" name="level" id=level data-placeholder="请选择等级" style="vertical-align:top;"  title="等级" style="width:98%;" >
									<option value=""></option>
									<c:forEach items="${dictionariesList}" var="dictionaries">
										<option value="${dictionaries.NAME }" <c:if test="${dictionaries.NAME == pd.level }">selected</c:if>>${dictionaries.NAME }</option>
									</c:forEach>
									</select>
								</td>
								<td style="width:120px;text-align: right;padding-top: 13px;">审核用户组:</td>
								<td id="juese">
								<select class="chosen-select form-control" name="usergroup_id" id="usergroup_id" data-placeholder="请选择用户组" style="vertical-align:top;" style="width:98%;" >
								<option value=""></option>
								<c:forEach items="${usergroupList}" var="usergroup">
									<option value="${usergroup.id }" <c:if test="${usergroup.id == pd.usergroup_id }">selected</c:if>>${usergroup.name }</option>
								</c:forEach>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">项目归属部门:</td>
								<td>
									<input type="hidden" name="DEPARTMENT_ID" id="DEPARTMENT_ID" value="${pd.DEPARTMENT_ID}"/>
									<div class="selectTree" id="selectTree"></div>
								</td>
								<td style="width:120px;text-align: right;padding-top: 13px;">项目责任人:</td>
								<td>
									<input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME}" maxlength="30"  style="width:70%;"/>&nbsp;&nbsp;
									<a class="btn btn-mini btn-primary" onclick="selectLeaderUser('USERNAME');">选择</a>
								</td>
							</tr>
 				 			<tr>
								<td style="width:120px;height:130px;text-align: right;padding-top: 13px;">项目组成员:</td>
								<div class="col-xs-12">
								<td colspan="8">
										<select multiple="multiple" size="5" name="duallistbox_demo1[]" class="demo2">
											<c:forEach items="${notBindingUserList}" var="user">
												<option value="${user.USER_ID }">${user.NAME }</option>
											</c:forEach>
											<c:forEach items="${bindedUserList}" var="user">
												<option value="${user.USER_ID }" selected="selected">${user.NAME }</option>
											</c:forEach>
										</select>
								</td>
								</div>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">描述:</td>
								<td  colspan="10">
									<textarea rows="3" cols="10" id="detail" name="detail" style="width:98%;"></textarea>
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
			var str1 = '<c'+':if test="'+'$'+'{QX.'+$("#QX_NAME").val();
			var str2 = ' == 1 }">这里放按钮<'+'/c:'+'if>';
			$("#code").val(str1+str2);
			

		});
		
		//下拉树
		var defaultNodes = {"treeNodes":${zTreeNodes}};
		function initComplete(){
			//绑定change事件
			$("#selectTree").bind("change",function(){
				if(!$(this).attr("relValue")){
			      //  top.Dialog.alert("没有选择节点");
			    }else{
					//alert("选中节点文本："+$(this).attr("relText")+"<br/>选中节点值："+$(this).attr("relValue"));
					$("#DEPARTMENT_ID").val($(this).attr("relValue"));
			    }
			});
			//赋给data属性
			$("#selectTree").data("data",defaultNodes);  
			$("#selectTree").render();
			$("#selectTree2_input").val("${null==depname?'请选择':depname}");
		}
		
		//拼代码
		function changecode(value){
			var str1 = '<c'+':if test="'+'$'+'{QX.';
			var str2 = ' == 1 }">这里放按钮<'+'/c:'+'if>';
			$("#code").val(str1 + value +str2);
		}
		
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
			$("#Form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		//选择责任人
		function selectLeaderUser(USERNAME){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择项目责任人";
			 diag.URL = '<%=basePath%>user/listwindows.do';
			 diag.Width = 800;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				 $("#"+USERNAME).val(diag.innerFrame.contentWindow.document.getElementById('xzvalue').value);
				 diag.close();
			 };
			 diag.show();
		}
		
		var demo1 = $('select[name="duallistbox_demo1[]"]')
		.bootstrapDualListbox({
			nonSelectedListLabel : '未加入',
			selectedListLabel : '已加入',
			preserveSelectionOnMove : 'moved',
			moveOnSelect : true,
			nonSelectedFilter : '',		
			infoText : '',
			filterPlaceHolder : '可输入用户名过滤',
			selectorMinimalHeight: 10
		});

		$("#Form1").submit(function() {
			console.log('111111111111');
			$("#DATA_IDS").val($('[name="duallistbox_demo1[]"]').val());
			console.log($('[name="duallistbox_demo1[]"]').val());
			return true;
		});
		</script>
</body>
</html>