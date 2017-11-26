<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
							
						<form action="queryUserApplyWorkOrderPre.do" method="post" name="queryForm" id="queryForm">
						<table style="margin-top:15px;">
							<tr>
								<td style="width:120px;text-align: right;">&nbsp;工单类型:</td>
								<td  style="width:120px;text-align: left;">
									<select name="workorder_type" id="workorder_type" title="选择工单类型">
										<option value="" selected="selected">选择工单类型</option>
									   <c:forEach items="${workorderTypeList}" var="var">
					                     <option value="${var.dictCode}">${var.dictValue}</option>
				                       </c:forEach>
									</select>
								</td>
								<td style="width:120px;text-align: right;">&nbsp;工单状态:</td>
								<td style="width:120px;text-align: left;">
									<select name="workorder_status" id="workorder_status" title="选择工单状态">
										<option value="" selected="selected">选择工单类型</option>
									   <c:forEach items="${workorderStatusList}" var="var">
					                     <option value="${var.dictCode}">${var.dictValue}</option>
				                       </c:forEach>
									</select>
								</td>
								<td style="width:80px;text-align: right;">&nbsp;项目:</td>
								<td style="width:120px;text-align: left;">
									<select name="project" id="project" title="选择项目">
										<option value="" selected="selected">选择项目</option>
									   <c:forEach items="${projectList}" var="var">
					                     <option value="${var.id}">${var.shortname}</option>
				                       </c:forEach>
									</select>
								</td>
								<td style="width:120px;text-align: right;" rowspan="2">
									<button class="btn btn-info" id="btn_query" type="submit">查询</button>
								 </td>
							</tr>
							<tr>
								<td style="width:120px;text-align: right;">&nbsp;工单号:</td>
								<td  style="width:120px;text-align: left;">
									<input type="text" name="workorder_type" id="workorder_type" />
								</td>
								<td style="width:120px;text-align: right;">&nbsp;时间选择:</td>
								<td style="width:120px;text-align: left;">
									<select name="workorder_status" id="workorder_status" title="时间选择">
										<option value="" selected="selected">全部</option>
										<option value="1">近一周</option>
										<option value="2">近一个月</option>
										<option value="3">近一年</option>
									</select>
								</td>
								
								 
								<td style="width:80px;text-align: right;">&nbsp;导出:</td>
								<td style="width:120px;text-align: left;">
									&nbsp;&nbsp;<a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a>
								</td>
								
							</tr>
							<tr>
								
							</tr>
						</table>	
						</form>	
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">工单ID</th>
									<th class="center">申请编号</th>
									<th class="center">工单类别</th>
									<th class="center">工单创建时间</th>
									<th class="center">申请人</th>
									<th class="center">工单状态</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty workOrderList}">
									<c:forEach items="${workOrderList}" var="var" varStatus="vs">
										<tr>
											<td class='center'>
												<label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace" /><span class="lbl"></span></label>
											</td>
											
									
											
											
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<td class='center'>${var.id}</td>
											<td class="center">${var.appNo}</td>
											<td class='center'>${var.appType}</td>
											<td class='center'>${var.createTime}</td>
											<td class='center'>${var.applyUserName}</td>
											<td class='center'>${var.status}</td>
											<td class="center">
												<c:if test="${QX.query != 1 && QX.check != 1 && QX.execute != 1 }">
													<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.query == 1 }">
													<a class="btn btn-xs btn-success" title="查看" onclick="query('${var.id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="查看"></i>
													</a>
													</c:if>
													<c:if test="${QX.check == 1 }">
													<a class="btn btn-xs btn-danger" onclick="check('${var.id}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="审核"></i>
													</a>
													</c:if>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
			
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.query == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="query('${var.id}');" class="tooltip-success" data-rel="tooltip" title="查看">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.check == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="check('${var.id}');" class="tooltip-error" data-rel="tooltip" title="审核">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<c:if test="${QX.add == 1 }">
									<a class="btn btn-sm btn-success" onclick="add();">新增</a>
									</c:if>
									<c:if test="${QX.del == 1 }">
									<a class="btn btn-sm btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
									</c:if>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
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
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
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
	$(top.hangge());//关闭加载状态
	//检索
	function tosearch(){
		top.jzts();
		$("#Form").submit();
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
	
	//新增
	function add(){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="新增";
		 diag.URL = '<%=basePath%>project/goAdd.do';
		 diag.Width = 992;
		 diag.Height = 580;
		 diag.CancelEvent = function(){ //关闭事件
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				 if('${page.currentPage}' == '0'){
					 top.jzts();
					 setTimeout("self.location=self.location",100);
				 }else{
					 nextPage(${page.currentPage});
				 }
			}
			diag.close();
		 };
		 diag.show();
	}
	
	
	//查询
	function query(Id){
		top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="工单详情";
		 diag.URL = '<%=basePath%>workorder/goDetail.do?id='+Id;
		 diag.Width = 992;
		 diag.Height = 580;
		 diag.CancelEvent = function(){ //关闭事件
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				 nextPage(${page.currentPage});
			}
			diag.close();
		 };
		 diag.show();
	}
	
	//修改
	function edit(Id){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑";
		 diag.URL = '<%=basePath%>project/goEdit.do?id='+Id;
		 diag.Width = 992;
		 diag.Height = 580;
		 diag.CancelEvent = function(){ //关闭事件
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				 nextPage(${page.currentPage});
			}
			diag.close();
		 };
		 diag.show();
	}
	
	//批量操作
	function makeAll(msg){
		bootbox.confirm(msg, function(result) {
			if(result) {
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
					if(msg == '确定要删除选中的数据吗?'){
						top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>project/deleteAll.do?tm='+new Date().getTime(),
					    	data: {DATA_IDS:str},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								 $.each(data.list, function(i, list){
										nextPage(${page.currentPage});
								 });
							}
						});
					}
				}
			}
		});
	}

	</script>

</body>
</html>