<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="/t-tags"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="static/ace/css/font-awesome.css" />
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<script type="text/javascript" src="js/commonUtil.js"></script><!-- 公共JS -->
<script type="text/javascript">
//已购历史列表查询
function doSearch(){
	var beginDate=$("#beginDate").val();//开始日期
	var endDate=$("#endDate").val();//结束日期
	var projCode=$("#projCode").val();//项目
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
	
	reloadBuyHis(beginDate, endDate, projCode);//重新加载已购历史列表
}
</script>
</head>
<body>
<table style="width: 100%;">
	<tr style="width: 100%;border:1px solid #cccccc;">
		<td style="width: 176px;padding-left: 5px;" colspan="2">
			申请时间：<input class="span10 date-picker" name="beginDate" id="beginDate"  value="${pd.beginDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/>&nbsp;到&nbsp;<input class="span10 date-picker" name="endDate" id="endDate"  value="${pd.endDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/>
		</td>
		<td style="width: 20px;padding-left: 5px;" align="right">项目：</td>
		<td style="width: 150px;">
			<select style="vertical-align:top;width: 100%;" class="chosen-select form-control" name="projCode" id="projCode" data-placeholder="请选择项目">
			<option value="">请选择</option>
			<c:forEach items="${projectList}" var="var">
				<option value="${var.dictCode}" <c:if test="${pd.projCode==var.dictCode || (pd.projCode=='' && var.dictDefault=='1')}">selected</c:if>>${var.dictValue}</option>
			</c:forEach>
		  	</select>
		</td>
		<td style="padding-left: 5px;" width="30px"><a class="btn btn-light btn-xs" onclick="doSearch()" title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
	</tr>
	<c:choose>
	<c:when test="${not empty buyHisList}">
	<c:forEach items="${buyHisList}" var="var" varStatus="st">
	<tr style="width: 100%;border:1px solid #cccccc;">
		<td align="left" style="width: 30px;">&nbsp;</td>
		<td colspan="4">
			<table style="width: 100%;border-collapse:separate;border-spacing:0px 10px;">
			<tr>
				<td align="left" style="width: 60px;" colspan="2">ECS（${var.orderNo}）</td>
				<td align="right" style="padding-right: 10px;">${var.virNum}台</td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">地域：</td>
				<td align="left" colspan="2">${var.areaCodeName}</td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">环境：</td>
				<td align="left" colspan="2">${var.envCodeName}</td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">项目：</td>
				<td align="left" colspan="2">${var.projectCodeName}</td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">资源类型：</td>
				<td align="left" colspan="2">${var.resTypeName}</td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">实例规格：</td>
				<td align="left" colspan="2">${var.cpu}&nbsp;核&nbsp;${var.memory}&nbsp;GB</td>
			</tr>
			<tr>
				<td align="left">镜像：</td>
				<td align="left" colspan="2">${var.osTypeName}&nbsp;${var.osBitNumName}</td>
			</tr>
			<tr>
				<td align="left" valign="top">数据盘：</td>
				<td align="left" valign="top" colspan="2">
					<t:list key="${var.diskTypeName}" val="${var.diskSize}" name="vars">
						${vars.dictCode}&nbsp;(&nbsp;${vars.dictValue}&nbsp;GB)<br>
					</t:list>
				</td>
			</tr>
			<tr>
				<td align="left">购买量：</td>
				<td align="left" colspan="2">${var.virNum}&nbsp;台</td>
			</tr>
			<tr>
				<td align="left">申请时间：</td>
				<td align="left" colspan="2">${var.createTime}</td>
			</tr>
			<tr>
				<td align="left">到期时间：</td>
				<td align="left" colspan="2">${var.expireDate}</td>
			</tr>
			</table>
		</td>
	</tr>
	</c:forEach>
	</c:when>
	<c:otherwise>
		<tr style="width: 100%;border:1px solid #cccccc;">
			<td colspan="5" class="center">没有相关数据</td>
		</tr>
	</c:otherwise>
	</c:choose>
</table>
<script type="text/javascript">
$('.date-picker').datepicker({autoclose: true,todayHighlight: true});//datepicker
$("#buyHisNum").html("${buyHisNum}");
</script>
</body>
</html>