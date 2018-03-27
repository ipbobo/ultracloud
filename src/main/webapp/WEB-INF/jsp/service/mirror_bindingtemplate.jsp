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

						<form action="" method="post" name="Form" id="Form">
						<input name="id" id="id" value="${pd.id}" type="hidden" />
						<table style="margin-top:5px;width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-mini btn-primary" onclick="top.Dialog.close();">返回</a>
									<a class="btn btn-mini btn-primary" onclick="bind('bind');">绑定</a>
								</td>
							</tr>
						</table>
					
						<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center">模板名称</th>
									<th class="center">uuid</th>
									<th class="center">更新时间</th>
								</tr>
							</thead>
													
							<tbody>
								
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty notBindList}">
									<c:forEach items="${notBindList}" var="var" varStatus="vs">
												
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class="center">${var.name }</td>
											<td class="center">${var.uuid }</td>
											<td class="center">${var.gmt_modified }</td>
										</tr>
									
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</form>

											</table>
										</div>
										<div id="alreadybind" class="tab-pane" style="height:350px;">
											<table id="table_alreadybind" class="table table-striped table-bordered table-hover">
												
						<form action="" method="post" name="Form2" id="Form2">
						<table style="margin-top:5px;width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-mini btn-primary" onclick="top.Dialog.close();">返回</a>
									<a class="btn btn-mini btn-danger" onclick="bind('unbind');">解除绑定</a>
								</td>
							</tr>
						</table>
					
						<table id="simple-table2" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center">模板名称</th>
									<th class="center">uuid</th>
									<th class="center">更新时间</th>
								</tr>
							</thead>
													
							<tbody>
								
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty alreadyBindList}">
									<c:forEach items="${alreadyBindList}" var="var" varStatus="vs">
												
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											<td class="center">${var.name }</td>
											<td class="center">${var.uuid }</td>
											<td class="center">${var.gmt_modified }</td>
										</tr>
									
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</form>				
												
												
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
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
		
		$(function() {
			$("#notbind_li").attr("class", "active");
		});
		
		$(function() {
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
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
		
		//绑定确认
		function bind(type) {
			var msg = '';
			if('bind' == type) {
				msg = "确定要绑定吗?";
			} else {
				msg = "确定要解除绑定吗?";
			}
			
			bootbox.confirm(msg, function(result) {
				if(result) {
					var id = document.getElementById('id').value;
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>mirror/bind.do?id='+id+'&type='+type,
					    	data: {DATA_IDS:str},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								top.hangge();
								location.reload();
							}
						});
					}
				}
			});
		}
	</script>


</body>
</html>