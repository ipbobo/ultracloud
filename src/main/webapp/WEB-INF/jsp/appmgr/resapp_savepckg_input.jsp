<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../system/index/top.jsp"%>
</head>
<body>
<div class="main-container" id="main-container">
	<table id="table_report" class="table table-striped table-bordered table-hover" style="margin-top:15px;">
		<tr>
			<td style="width: 120px;text-align: right;padding-top: 13px;">套餐名称:</td>
			<td><input type="text" name="pckgName" id="pckgName" value="" maxlength="60" placeholder="这里输入套餐名称" title="套餐名称""/></td>
		</tr>
	</table>
</div>
<%@ include file="../system/index/foot.jsp"%>
</body>
</html>