<%--suppress ALL --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<style>
	input.textbox {
		border-radius: 5px !important;
		width: 60%;
		height: 30px;
		margin-left: 50px;
	}
</style>
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

							<!-- 检索  -->
							<form action="kvm/listVirtual.do" method="post"
								name="userForm" id="userForm">
								<input name="xzvalue" id="xzvalue" value="" type="hidden" /> <input
									name="hostmachine_id" id="hostmachine_id"
									value="${pd.hostmachine_id} }" type="hidden" />
								<table style="margin-top: 5px; width: 100%;">
									<tr>
										<td style="vertical-align: top;">
											<!-- <a class="btn btn-mini btn-primary" onclick="top.Dialog.close();">返回</a> -->
											<a class="btn btn-mini btn-primary" onclick="showCreateDialog()">新建</a>
											<a class="btn btn-mini btn-primary"
											onclick="handle('startup');">开机</a> <a
											class="btn btn-mini btn-danger" onclick="handle('shutdown');">关机</a>
											<a class="btn btn-mini btn-danger"
											onclick="handle('delete');">删除</a> <a
											class="btn btn-mini btn-primary" onclick="handle('restart');">重启</a>
											<a class="btn btn-mini btn-danger"
											onclick="handle('hangup');">挂起</a> <a
											class="btn btn-mini btn-primary" onclick="handle('recover');">恢复</a>
											<div class="btn-group">
												<a class="btn btn-primary btn-sm" data-toggle="dropdown"
													aria-haspopup="true" aria-expanded="false">更多操作</a> <a
													class="btn btn-primary btn-sm dropdown-toggle"
													data-toggle="dropdown" aria-haspopup="true"
													aria-expanded="false"> <span class="caret"></span> <!-- <span class="sr-only">Toggle Dropdown</span> -->
												</a>
												<ul class="dropdown-menu">
													<li><a onclick="modifyConfig()">修改配置</a></li>
													<li><a target="_blank" href="kvm/console">进入控制台</a></li>
												</ul>
											</div>
										</td>
										<td>
											<div class="nav-search"
												style="float: right; padding-top: 0px; margin-top: 0px;">
												<span class="input-icon"> <input
													class="nav-search-input" autocomplete="off"
													id="nav-search-input" type="text" name="keywords"
													value="${pd.keywords }" placeholder="关键词搜索" /> <i
													class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
										</td>
										<c:if test="${QX.cha == 1 }">
											<td
												style="vertical-align: top; padding-left: 2px; width: 32px;"><a
												class="btn btn-light btn-xs" onclick="searchs();" title="检索"><i
													id="nav-search-icon"
													class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
										</c:if>
									</tr>
								</table>
								<!-- 检索  -->

								<table id="datagrid"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 35px;"><label
												class="pos-rel"><input type="checkbox" class="ace"
													id="zcheckbox" /><span class="lbl"></span></label></th>
											<th class="center">虚拟机名称</th>
											<th class="center">IP</th>
											<th class="center">CPU</th>
											<th class="center">内存</th>
											<th class="center">磁盘</th>
											<th class="center">状态</th>
										</tr>
									</thead>

									<tbody>

										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1}">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center'><label class="pos-rel"><input
																	type='checkbox' name='ids' value="${var.id}"
																	class="ace" /><span class="lbl"></span></label></td>
															<td class="center">${var.name}</td>
															<td class="center">${var.ip}</td>
															<td class="center">${var.cpu}</td>
															<td class="center"><c:if
																	test="${not empty var.memory}">
													${var.memory}G
												</c:if></td>
															<td class="center"><c:if
																	test="${not empty var.datadisk}">
													${var.datadisk}G
												</c:if></td>
															<td class="center"><c:choose>
																	<c:when test="${var.status == 0}">
														运行中
												    </c:when>
																	<c:when test="${var.status == 1}">
														挂起
												    </c:when>
																	<c:when test="${var.status == 2}">
														关机
												    </c:when>
																	<c:otherwise>
														未知
												    </c:otherwise>
																</c:choose></td>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${QX.cha == 0 }">
													<tr>
														<td colspan="10" class="center">您无权查看</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="10" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>

								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;"><div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>

							</form>

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

		<div class="modal fade" id="platform_modal" tabindex="-1" role="dialog" aria-labelledby="platform_modalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="middleware_modalLabel">
							创建虚拟机
						</h4>
					</div>
					<div class="modal-body">
						<input type="hidden"   name="h_appNo" id="h_appNo">
						<input type="hidden"   name="h_cpu" id="h_cpu">
						<input type="hidden"   name="h_memory" id="h_memory">
						<input type="hidden"   name="h_diskSize" id="h_diskSize">
						<table  style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;宿主机</td>
								<td>
									<select name="host" style="width:60%;margin-left: 50px;">
										<option value="#" selected="selected">请选择宿主机</option>
										<c:forEach items="${hostList}" var="var">
											<option value="${var.id}">${var.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;名称:</td>
								<td>
									<input class="textbox" type="text" name="vname">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;CPU:</td>
								<td>
									<input class="textbox" type="number" name="vcpus">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;内存:</td>
								<td>
									<input class="textbox" type="number" name="vmemory">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;磁盘:</td>
								<td>
									<input class="textbox" type="number" name="vdisk">
								</td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;padding-top: 13px;"><i class="ace-icon fa fa-asterisk"></i>&nbsp;&nbsp;镜像:</td>
								<td>
									<select name="vimage" style="width:60%;margin-left: 50px;">
										<option value="#" selected="selected">请选择镜像</option>
										<option value="/root/CentOS-6-x86_64-GenericCloud-1710.qcow2">
											CentOS-6-x86_64-GenericCloud-1710.qcow2
										</option>
									</select>
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button id="platform_modal_btn_cancel" type="button" class="btn btn-default"  data-dismiss="modal">取消</button>
						<button id="platform_modal_btn_ok" type="button" class="btn btn-primary" onclick="createVm()">确定</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>

<script type="text/javascript">
$(top.hangge());

//检索
function searchs(){
	top.jzts();
	$("#userForm").submit();
}

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

//选择确定
function select(){
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
		$("#xzvalue").val(str);
		top.Dialog.close();
	}
}

function getSelected() {
	return $('#datagrid').find('input:checked').map(function() { return $(this).val(); }).get();
}

function handle(type) {
	var url, msg;
	if(type == 'startup') {
		msg = '开机';
		url = 'kvm/startVm';
	} else if(type == 'shutdown') {
		msg = '关机';
		url = 'kvm/stopVm';
	} else if(type == 'delete') {
		msg = '删除';
		url = 'kvm/destroyVm';
	} else if(type == 'restart') {
		msg = '重启';
		url = 'kvm/rebootVm';
	} else if(type == 'hangup') {
		msg = '挂起';
		url = 'kvm/suspendVm';
	} else if(type == 'recover') {
		msg = '恢复';
		url = 'kvm/resumeVm';
	}
	
	bootbox.confirm("确定要进行" + msg + "操作吗?", function(result) {
		if(result) {
			top.jzts();
			
			$.post(url, { ls: getSelected() }, function(data) {
				nextPage(${page.currentPage});
				consol.log(data);
			});
		}
	});
}

function showCreateDialog() {
    $('#platform_modal').modal('show');
}

function createVm() {
    var host = $('select[name=host]').val();
    var vname = $('input[name=vname]').val();
    var vcpus = $('input[name=vcpus]').val();
    var vmemory = $('input[name=vmemory]').val();
    var vdisk = $('input[name=vdisk]').val();
    var vimage = $('select[name=vimage]').val();

    if (host && vname && vcpus && vmemory && vdisk) {
        $.ajax({
            type: "POST",
            url: 'kvm/createVm',
            contentType: 'application/json',
			data: JSON.stringify({
                host: host,
                vname: vname,
                vcpus: vcpus,
                vmemory: vmemory,
                vdisk: vdisk,
                vimage: vimage
            }),
            success: function(ret) {
				// clearCreateDialog();
            }
		});
	} else {
        bootbox.alert('必要参数缺失');
	}
}

function clearCreateDialog() {
    $('select[name=host]').val('');
    $('input[name=vname]').val('');
    $('input[name=vcpus]').val('');
    $('input[name=vmemory]').val('');
    $('input[name=vdisk]').val('');
    $('select[name=vimage]').val('');
}
</script>
</html>
