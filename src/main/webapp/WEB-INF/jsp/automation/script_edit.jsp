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
	<!-- 上传插件 -->
	<link href="plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="plugins/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="plugins/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<!-- 上传插件 -->
	<script type="text/javascript">
	var jsessionid = "<%=session.getId()%>";  //勿删，uploadify兼容火狐用到
	</script>
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
					
					<form action="script/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;名称:</td>
								<td><input type="text" name="name" id="name" value="${pd.name}" maxlength="30" placeholder="这里输入名称" title="名称" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;类型:</td>
								<td id="type_td">
									<select class="chosen-select form-control" name="type" id=type data-placeholder="请选择类型" style="vertical-align:top;"  title="类型"  >
									<option value=""></option>
									<c:forEach items="${dictionariesList}" var="dictionaries">
										<option value="${dictionaries.BIANMA }" <c:if test="${dictionaries.BIANMA == pd.type }">selected</c:if>>${dictionaries.NAME }</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;脚本key:</td>
								<td><input type="text" name="script_key" id="script_key" value="${pd.script_key}" maxlength="30" placeholder="这里输入脚本key" title="脚本key" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">用途:</td>
								<td><input type="text" name="purpose" id="purpose" value="${pd.purpose}" maxlength="30" placeholder="这里输入用途" title="用途" style="width:100%;"/></td>
							</tr>
							
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk red"></i>&nbsp;关联软件:</td>
								<td id="medium_td">
									<select class="chosen-select form-control" name="medium_id" id="medium_id" data-placeholder="请选择介质" style="vertical-align:top;"  title="介质" >
									<option value=""></option>
									<c:forEach items="${mediumList}" var="medium">
										<option value="${medium.id }" <c:if test="${medium.id == pd.medium_id }">selected</c:if>>${medium.name }</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">直接输入路径:</td>
								<td><input type="text" name="url" id="url" value="${pd.url}" maxlength="200" placeholder="这里输入路径" title="路径" style="width:100%;"/></td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;" id="FILEPATHn">通过文件上传:</td>
								<td>
									<input type="file" name="File_name" id="uploadify1" keepDefaultStyle = "true"/>
									<!-- <input type="hidden" name="url" id="url" value=""/> -->
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
			if($("#type").val()=="" || $("#type").val()==null){
				$("#type_td").tips({
					side:3,
		            msg:'请选择类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#type").focus();
			return false;
			}
			if($("#script_key").val()==""){
				$("#script_key").tips({
					side:3,
		            msg:'请输入脚本key',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#script_key").focus();
			return false;
			}
			if($("#medium_id").val()=="" || $("#medium_id").val()==null){
				$("#medium_td").tips({
					side:3,
		            msg:'请关联软件',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#medium_id").focus();
			return false;
			}

			if($("#url").val()=="" && $("#hasTp1").val()=="no"){
				$("#url").tips({
					side:3,
		            msg:'请输入路径或上传文件',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#url").focus();
			return false;
			}
			
			if($("#hasTp1").val()=="ok") {
				$('#uploadify1').uploadifyUpload();
			} else {
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}
		
		//====================上传=================
		$(document).ready(function(){
			var str='';
			$("#uploadify1").uploadify({
				'buttonImg'	: 	"<%=basePath%>static/images/fileup.png",
				'uploader'	:	"<%=basePath%>plugins/uploadify/uploadify.swf",
				'script'    :	"<%=basePath%>plugins/uploadify/uploadFile.jsp;jsessionid="+jsessionid,
				'cancelImg' :	"<%=basePath%>plugins/uploadify/cancel.png",
				'folder'	:	"<%=basePath%>uploadFiles/uploadFile",//上传文件存放的路径,请保持与uploadFile.jsp中PATH的值相同
				'queueId'	:	"fileQueue",
				'queueSizeLimit'	:	1,//限制上传文件的数量
				//'fileExt'	:	"*.rar,*.zip",
				//'fileDesc'	:	"RAR *.rar",//限制文件类型
				'fileExt'     : '*.*;*.*;*.*',
				'fileDesc'    : 'Please choose(.*, .*, .*)',
				'auto'		:	false,
				'multi'		:	true,//是否允许多文件上传
				'simUploadLimit':	2,//同时运行上传的进程数量
				'buttonText':	"files",
				'scriptData':	{'uploadPath':'/uploadFiles/uploadFile/'},//这个参数用于传递用户自己的参数，此时'method' 必须设置为GET, 后台可以用request.getParameter('name')获取名字的值
				'method'	:	"GET",
				'onComplete':function(event,queueId,fileObj,response,data){
					str = response.trim();//单个上传完毕执行
				},
				'onAllComplete' : function(event,data) {
					//alert(str);	//全部上传完毕执行
					$("#url").val(str);
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
		    	},
		    	'onSelect' : function(event, queueId, fileObj){
		    		console.log("ok");
		    		$("#hasTp1").val("ok");
		    	}
			});
					
		});
		//====================上传=================
			//清除空格
		String.prototype.trim=function(){
		     return this.replace(/(^\s*)|(\s*$)/g,'');
		};
		</script>
</body>
</html>