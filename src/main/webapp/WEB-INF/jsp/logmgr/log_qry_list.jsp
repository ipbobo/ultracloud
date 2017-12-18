<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../system/index/top.jsp"%>
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript" src="static/ace/js/ace/ace.js"></script><!-- ace scripts -->
<script type="text/javascript" src="static/ace/js/chosen.jquery.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<script type="text/javascript">
//查询
function doSearch(){
	var beginDate=$("#beginDate").val();
	var endDate=$("#endDate").val();
	if(beginDate!="" && !isDate(beginDate)){
    	alert("开始日期不正确，请重新输入！");
        $("#beginDate").focus();
        return;
    }
    
    if(endDate!="" && !isDate(endDate)){
    	alert("结束日期不正确，请重新输入！");
        $("#endDate").focus();
        return;
    }
    
    //比较起始日期是否大于等于结束日期
	if(beginDate!="" && endDate!=""){
	    if(beginDate>endDate){
	    	alert("结束日期必须大于等于开始日期，请重新输入！");
	       	$("#endDate").focus();
	        return;
	    }
	}
	
	top.jzts();
	$("#mainForm").submit();
}

//批量删除日志
function deleteAll(msg){
	var str = '';
	for(var i=0; i<document.getElementsByName('ids').length;i++){
	  	if(document.getElementsByName('ids')[i].checked){
		  	if(str=='') str += document.getElementsByName('ids')[i].value;
		  	else str += ',' + document.getElementsByName('ids')[i].value;
	  	}
	}
	
	if(str==''){
		bootbox.dialog({message: "<span class='bigger-110'>请先选择待删除的记录！</span>", buttons: { "button":{ "label":"确定", "className":"btn-sm btn-success"}}});
		$("#zcheckbox").tips({side:1, msg:'点这里全选', bg:'#AE81FF', time:8});
		return;
	}
	
	del(str, msg);//删除日志
}

//删除日志
function del(ids, msg){
	bootbox.confirm(msg, function(result) {
		if(result) {
			top.jzts();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>log/deleteAll.do?tm='+new Date().getTime(),
		    	data: {"ids": ids},
				dataType:'json',
				cache: false,
				success: function(data){
					$(top.hangge());//关闭加载状态
					bootbox.dialog({message: "<span class='bigger-110'>"+data.retMsg+"</span>", buttons: { "button":{ "label":"确定", "className":"btn-sm btn-success"}}});
					if(data.retCode=="0"){//返回成功
						$("#mainForm").submit();
					}
				}
			});
		}
	});
}
</script>
</head>
<body>
<div class="widget-box">
	<div class="widget-header"><h4 class="smaller">条件查询</h4></div>
	<form action="log/list.do" method="post" name="mainForm" id="mainForm">
		<table style="border-collapse:separate;border-spacing:0px 5px;">
			<tr>
				<td>开始日期：<input class="span10 date-picker" name="beginDate" id="beginDate"  value="${pd.beginDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
				<td style="padding-left:10px;">结束日期：<input class="span10 date-picker" name="endDate" id="endDate"  value="${pd.endDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
				<td style="padding-left:10px"><a class="btn btn-light btn-xs" onclick="doSearch();" title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
			</tr>
		</table>
	</form>
</div>
<div class="widget-box widget-header" style="margin:0px">
	<a class="btn btn-mini btn-danger" onclick="deleteAll('确定要删除选中的记录吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
</div>
<table id="simple-table" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th class="center" style="width: 35px;"><label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label></th>
			<th class="center" style="width: 50px;">序号</th>
			<th class="center">操作者</th>
			<th class="center">操作类型</th>
			<th class="center">操作对象</th>
			<th class="center">操作详细描述</th>
			<th class="center">操作状态</th>
			<th class="center">操作者IP</th>
			<th class="center" style="width: 140px;">创建时间</th>
			<th class="center">操作</th>
		</tr>
	</thead>
	<c:choose>
	<c:when test="${not empty list}">
	<c:forEach items="${list}" var="var" varStatus="vs">
		<tr>
			<td class='center'><label class="pos-rel"><input type='checkbox' name='ids' value="${var.id}" class="ace"/><span class="lbl"></span></label></td>
			<td class='center' style="width: 30px;">${vs.index+1+page.currentResult}</td>
			<td class='center'>${var.username}</td>
			<td class='center'>${var.operTypeName}</td>
			<td class='center'>${var.opt_object}</td>
			<td class='center'>${var.detail}</td>
			<td class='center'>${var.optStatusName}</td>
			<td class='center'>${var.ip}</td>
			<td class='center'>${var.gmt_create}</td>
			<td class="center"><a class="btn btn-xs btn-danger" onclick="del('${var.id}', '确定要删除该条记录吗?');"><i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i></a></td>
		</tr>
	</c:forEach>
	</c:when>
	<c:otherwise>
		<tr class="main_info">
			<td colspan="100" class="center">没有相关数据</td>
		</tr>
	</c:otherwise>
	</c:choose>
</table>
<table style="width: 100%;"><tr><td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;margin-bottom: 0px;">${page.pageStr}</div></td></tr></table>
<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i></a><!-- 返回顶部 -->
<%@ include file="../system/index/foot.jsp"%>
<script type="text/javascript" src="static/ace/js/date-time/bootstrap-datepicker.js"></script><!-- 日期框 -->
<script type="text/javascript" src="static/ace/js/bootbox.js"></script><!-- 删除时确认窗口 -->
<script type="text/javascript">
$(top.hangge());//关闭加载状态
$('.date-picker').datepicker({autoclose: true,todayHighlight: true});//datepicker
//复选框全选控制
$(function() {
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
</script>
</body>
</html>