<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../system/index/top.jsp"%>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
//获取软件参数
function getSoftParam(){
	var paramArr=new Array();
	$("input[type='text']").each(function() {
		paramArr.push($(this).attr("name")+':'+$(this).val());
	});
	
	return paramArr.join();
}
</script>
</head>
<body>
<div class="main-container">
	<table style="width: 100%;margin-top: 10px;" class="table table-striped table-bordered table-hover">
	<c:choose>
		<c:when test="${not empty paramList}">
		<c:forEach items="${paramList}" var="var" varStatus="st">
		<tr>
			<td style="width: 120px;vertical-align: middle;text-align: right;">${var.dictDesc}:</td>
			<td><input type="text" name="${var.dictCode}" id="${var.dictCode}" value="${var.dictValue}" style="width: 100%;" maxlength="200" placeholder="这里输入参数[${var.dictCode}]的值" title="参数[${var.dictCode}]的值"/></td>
		</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
		<tr class="main_info"><td colspan="2" class="center">没有相关参数</td></tr>
		</c:otherwise>
	</c:choose>
	</table>
</div>
</body>
</html>