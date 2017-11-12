<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; %>
<html>
<head>
<base href="<%=basePath%>">
<%@ include file="../system/index/top.jsp"%>
</head>
<body class="no-skin">
<div>&nbsp;</div>
<ul id="myTab" class="nav nav-tabs">
	<li><a href="#zdysqDiv" data-toggle="tab">自定义申请</a></li>
	<li><a href="#ios" data-toggle="tab">iOS</a></li>
</ul>
<div id="zdysqDiv" class="main-container">
	<table style="width:60%;margin-top: 8px;margin-left: 200px;">
		<tr height="35">
			<td align="center" style="width: 80px;">平台类型22</td>
			<c:if test="${not empty platTypeList}">
			<c:forEach items="${platTypeList}" var="var" varStatus="st">
			<td style="width: 80px;" align="center" bgcolor=${var.dictDefault=='1'?"#FFC926":"#E5E5E5"}>
				<a href="#" style="text-decoration: none; display: block;"><font color="#666666">${var.dictValue}</font></a>
			</td>
			<td align="center" style="width: 20px;">&nbsp;</td>
			</c:forEach>
			</c:if>
		</tr>
	</table>
	<!-- 返回顶部 -->
	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-sm btn-inverse"> <i
		class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
</div>
<%@ include file="../system/index/foot.jsp"%>
<script src="static/ace/js/ace/ace.js"></script>
<script type="text/javascript">
	$(top.hangge());
	
	//处理按钮点击
	function upRb(ROLE_ID,FHBUTTON_ID){
		$.ajax({
			type: "POST",
			url: "<%=basePath%>+buttonrights/upRb.do?ROLE_ID=" + ROLE_ID + "&BUTTON_ID=" + FHBUTTON_ID + "&guid=" + new Date().getTime(),
			data : encodeURI(""),
			dataType : 'json',
			//beforeSend: validateData,
			cache : false,
			success : function(data) {
			}
		});
	}
</script>
</body>
</html>