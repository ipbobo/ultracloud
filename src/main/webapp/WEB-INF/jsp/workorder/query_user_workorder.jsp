<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>
	<link rel="stylesheet" href="css/newSkin.css">
</head>
<body class="no-skin queryUserApplyWorkOrderPre new-page-list">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
			<form action="queryUserApplyWorkOrderPre.do" method="post"
									name="queryForm" id="queryForm">
				<input type="hidden" id="queryType" name="queryType" value="${pd.queryType}">
				<input type="hidden" name="sortCol" id="sortCol" value="${pd.sortCol}"/>
				<input type="hidden" name="sortType" id="sortType" value="${pd.sortType}"/>
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							
							<div class="widget-box">
								<div class="widget-header">
									<h4 class="smaller">条件查询</h4>
								</div>

								
									<table style="margin-top: 5px;">
										<tr>
											<td style="width: 120px; text-align: right;">&nbsp;工单类型:</td>
											<td style="width: 120px; text-align: left;">
											<select name="workorder_type" style="width: 110px;" id="workorder_type" title="选择工单类型">
													
														<option value=""  <c:if test="${pd.workorder_type == null || pd.workorder_type == '' }">selected="selected"</c:if>>选择工单类型</option>
													
													<c:forEach items="${workorderTypeList}" var="var">
														<option value="${var.dictCode}" <c:if test="${pd.workorder_type !='' && var.dictCode == pd.workorder_type }">selected="selected"</c:if> >${var.dictValue}</option>
													</c:forEach>
											</select></td>
											<td style="width: 120px; text-align: right;">&nbsp;工单状态:</td>
											<td style="width: 150px; text-align: left;">
											<select name="workorder_status" id="workorder_status" title="选择工单状态">
													<option value="" <c:if test="${pd.workorder_status == null || pd.workorder_status == '' }">selected="selected"</c:if>>选择工单类型</option>
													<c:forEach items="${workorderStatusList}" var="var">
														<option value="${var.dictCode}" <c:if test="${pd.workorder_status !='' && var.dictCode == pd.workorder_status }">selected="selected"</c:if>>${var.dictValue}</option>
													</c:forEach>
											</select></td>
											<td style="width: 80px; text-align: right;">&nbsp;项目:</td>
											<td style="width: 120px; text-align: left;"><select
												name="project" id="project" title="选择项目">
													<option value="" <c:if test="${pd.project == null || pd.project == '' }">selected="selected"</c:if>>选择项目</option>
													<c:forEach items="${projectList}" var="var">
														<option value="${var.id}"  <c:if test="${pd.project !='' && var.id == pd.project }">selected="selected"</c:if>>${var.shortname}</option>
													</c:forEach>
											</select></td>
											<!--修改begin @zjb-->

											<td style=" text-align: left;" colspan="2"></td>
											<!--修改end-->

										</tr>
										<tr>
											<td style="width: 120px; text-align: right;">&nbsp;工单号:</td>
											<td style="width: 120px; text-align: left;">
											<input type="text" style="width: 110px" name="workorder_appNo" value="${pd.workorder_appNo}" id="workorder_appNo"/></td>
											<td style="width: 120px; text-align: right;">&nbsp;时间选择:</td>
											<td style="width: 120px; text-align: left;">
											<select  style="width: 110px" name="workorder_time" id="workorder_time" title="时间选择">
													<option value="" <c:if test="${pd.workorder_time == null || pd.workorder_time == '' }">selected="selected"</c:if>>全部</option>
													<option value="1" <c:if test="${pd.workorder_time !='' && '1' == pd.workorder_time }">selected="selected"</c:if>>近一周</option>
													<option value="2" <c:if test="${pd.workorder_time !='' && '2' == pd.workorder_time }">selected="selected"</c:if>>近一个月</option>
													<option value="3" <c:if test="${pd.workorder_time !='' && '3' == pd.workorder_time }">selected="selected"</c:if>>近一年</option>
											</select></td>

											<!--修改begin @zjb-->
											<td style="width: 80px; text-align: left;"></td>
											<td style="width: 120px; text-align: left;"></td>
											<td style="text-align: right; padding-right: 20px" colspan="2">
												<button class="btn btn-info bt-blue" id="btn_query" type="submit">查询&nbsp;<i class="ace-icon fa fa-search"></i></button>
												<a class="btn btn-light btn-xs bt-green" onclick="toExcel();" title="导出到EXCEL">导出&nbsp;<i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a>
											</td>
											<!--修改end-->


										</tr>
										<tr>

										</tr>
									</table>
								
							</div>
							<table id="simple-table"
								class="table table-striped table-bordered table-hover"
								style="margin-top: 5px;">
								
								<thead>
									<tr>
										<th class="center" style="width: 35px;"></th>
										<th class="center" style="width: 50px;">序号</th>
										<th class="center sorting" onclick="sortTable(this, 'appNo');"><span id="appNo">工单号</span></th>
										<th class="center sorting" onclick="sortTable(this, 'appType');"><span id="appType">工单类型</span></th>
										<th class="center sorting" onclick="sortTable(this, 'status');"><span id="status">工单状态</span></th>
										<th class="center sorting" onclick="sortTable(this, 'projectCode');"><span id="projectCode">项目名称</span></th>
										<th class="center sorting" onclick="sortTable(this, 'createTime');"><span id="createTime">工单时间</span></th>
										<th class="center">操作</th>
									</tr>
								</thead>

								<tbody>
									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty workOrderList}">
											<c:forEach items="${workOrderList}" var="var" varStatus="vs">
												<tr>
													<td class='center'><label class="pos-rel"><input
															type='checkbox' name='ids' value="${var.id}" class="ace" /><span
															class="lbl"></span></label></td>




													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td class='center'>${var.appNo}</td>
													<td class='center'>${var.appTypeName}</td>
													<td class='center'>${var.statusName}</td>
													<td class='center'>${var.projectName}</td>
													<td class='center'>${var.createTime}</td>

													<td class="center"><c:if
															test="${QX.query != 1 && QX.check != 1 && QX.execute != 1 }">
															<span
																class="label label-large label-grey arrowed-in-right arrowed-in"><i
																class="ace-icon fa fa-lock" title="无权限"></i></span>
														</c:if>
														<div class="hidden-sm hidden-xs btn-group">
															<c:if test="${QX.check == 1  and var.status != '10'}">
																<a class="btn btn-xs btn-success" title="查看"
																	onclick="query('${var.appNo}');"> <i
																	class="ace-icon fa fa-print  align-top bigger-125"
																	title="查看"></i>
																</a>
															</c:if>
															<c:if test="${QX.check == 1  and var.status == '10'}">
																<a class="btn btn-xs btn-danger"
																	onclick="check('${var.appNo}');"> <i
																	class="ace-icon fa fa-pencil-square-o bigger-120" title="审核"></i>
																</a>
															</c:if>
															<c:if test="${QX.query == 1 and !fn:startsWith(var.status,'3')}">
																<a class="btn btn-xs btn-success" title="查看"
																	onclick="query('${var.appNo}');"> <i
																	class="ace-icon fa fa-print  align-top bigger-125"
																	title="查看"></i>
																</a>
															</c:if>
															<c:if test="${QX.query == 1 and fn:startsWith(var.status,'3')}">
																<a class="btn btn-xs btn-danger" title="确认"
																	onclick="verify('${var.appNo}');"> <i
																	class="ace-icon fa fa-print  align-top bigger-125"
																	title="确认"></i>
																</a>
															</c:if>
															<c:if test="${QX.execute == 1 and !fn:startsWith(var.status,'4')}">
																<a class="btn btn-xs btn-success" title="查看"
																	onclick="query('${var.appNo}');"> <i
																	class="ace-icon fa fa-print  align-top bigger-125"
																	title="查看"></i>
																</a>
															</c:if>
															<c:if test="${QX.execute == 1 and fn:startsWith(var.status,'4')}">
																<a class="btn btn-xs btn-danger"
																	onclick="execute('${var.appNo}');"> <i
																	class="ace-icon fa fa-wrench  bigger-120 icon-only" title="实施"></i>
																</a>
															</c:if>
														</div>
														<div class="hidden-md hidden-lg">
															<div class="inline pos-rel">
																<button
																	class="btn btn-minier btn-primary dropdown-toggle"
																	data-toggle="dropdown" data-position="auto">
																	<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																</button>

																<ul
																	class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																	<c:if test="${QX.query == 1 }">
																		<li><a style="cursor: pointer;"
																			onclick="query('${var.appNo}');"
																			class="tooltip-success" data-rel="tooltip" title="查看">
																				<span class="green"> <i
																					class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
																		</a></li>
																	</c:if>
																	<c:if test="${QX.check == 1 }">
																		<li><a style="cursor: pointer;"
																			onclick="check('${var.appNo}');"
																			class="tooltip-error" data-rel="tooltip" title="审核">
																				<span class="red"> <i
																					class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
																		</a></li>
																	</c:if>
																</ul>
															</div>
														</div></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="main_info">
												<td colspan="100" class="center">没有相关数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<div class="page-header position-relative">
								<table style="width: 100%;">
									<tr>
										<td style="vertical-align: top;"><c:if
												test="${QX.add == 1 }">
												<a class="btn btn-sm btn-success" onclick="add();">新增</a>
											</c:if> <c:if test="${QX.del == 1 }">
												<a class="btn btn-sm btn-danger"
													onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
													class='ace-icon fa fa-trash-o bigger-120'></i></a>
											</c:if></td>
										<td style="vertical-align: top;"><div class="pagination"
												style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
				</form>
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
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
/*		var sortColVal = $('#sortCol').val();
		var sortTypeVal = $('#sortType').val();
		if (sortTypeVal != null && sortTypeVal == 'asc'){
			$("#" + sortColVal).text($("#" + sortColVal).text() + '↑');
		}
		if (sortTypeVal != null && sortTypeVal == 'desc'){
			$("#" + sortColVal).text($("#" + sortColVal).text() + '↓');
		}*/
        /*新的排序显示样式控制 @zjb 2018-3-1 begin*/
        var sortColVal = $('#sortCol').val();
        var sortTypeVal = $('#sortType').val();
        if (sortTypeVal != null && sortTypeVal == 'asc'){
            $("#" + sortColVal).parent().addClass('sorting_asc');
        }
        if (sortTypeVal != null && sortTypeVal == 'desc'){
            $("#" + sortColVal).parent().addClass('sorting_desc');
        }
        /*新的排序显示样式控制 @zjb 2018-3-1 end*/
		
	});
	$(top.hangge());//关闭加载状态
	//检索
	function tosearch(){
		top.jzts();
		$("#queryForm").submit();
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
	
	//审查工单
	function check(appNo){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="工单审查";
		 diag.URL = '<%=basePath%>goWorkorderCheck.do?appNo='+ appNo;
		 diag.Width = 992;
		 diag.Height = 580;
		 diag.CancelEvent = function(){ //关闭事件
			 tosearch();
			diag.close();
		 };
		 diag.show();
	}
	
	
	//查询
	function query(appNo){
		top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="工单查询";
		 diag.URL = '<%=basePath%>goWorkorderView.do?appNo='+ appNo;
		 diag.Width = 992;
		 diag.Height = 580;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 };
		 diag.show();
	}
	//实施
	function execute(appNo){
		top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="工单实施";
		 diag.URL = '<%=basePath%>goWorkorderExecute.do?appNo='+ appNo;
		 diag.Width = 992;
		 diag.Height = 580;
		 diag.CancelEvent = function(){ //关闭事件
			tosearch();
			diag.close();
		 };
		 diag.show();
	}
	
	//确认
	function verify(appNo){
		top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="工单确认";
		 diag.URL = '<%=basePath%>goWorkorderVerify.do?appNo='+ appNo;
		 diag.Width = 992;
		 diag.Height = 580;
		 diag.CancelEvent = function(){ //关闭事件
			tosearch();
			diag.close();
		 };
		 diag.show();
	}
	
	//导出excel
	function toExcel(){
		window.location.href='<%=basePath%>workorderExcel.do?workorder_type='+$('#workorder_type').val()+'&workorder_status='+$('#workorder_status').val()+'&project='+$('#project').val()+
		'&workorder_appNo='+$('#workorder_appNo').val()+'&workorder_time='+$('#workorder_time').val()+ '&showCount='+$('#showCount').val();
	}
	
	function sortTable(th, sortCol){
		var old_sortCol = $('#sortCol').val();
		var old_sortType = $('#sortType').val();
		if (old_sortType == null || old_sortType == ''){
			$('#sortType').val('desc');
		}
		if (old_sortType != null && old_sortType != ''){
			if (old_sortType == 'asc'){
				$('#sortType').val('desc');
			}else if (old_sortType == 'desc'){
				$('#sortType').val('asc');
			}
		}
		$('#sortCol').val(sortCol);
		$("#queryForm").submit();
	}

	</script>

</body>
</html>