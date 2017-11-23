<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
</script>
</head>
<body>
<form id="tcmainForm" name="tcmainForm" action="addPckgList.do" enctype="multipart/form-data" method="post">
<input type="hidden" name="tcareaCode" id="tcareaCode" value="1"/>
<input type="hidden" name="tcplatType" id="tcplatType" value="vmware"/>
<input type="hidden" name="tcdeployType" id="tcdeployType" value="1"/>
<input type="hidden" name="pckgId" id="pckgId" value=""/>
<table style="width: 100%;" border=1>
	<c:if test="${not empty pckgList}">
	<c:forEach items="${pckgList}" var="var" varStatus="st">
	<tr style="width: 100%;border:1px solid #cccccc;">
		<td align="left" style="width: 10px;padding:10px;">
			<input type="checkbox" name="orderId" onclick='choosePckg("{\"${var.id}\")'/>
		</td>
		<td>
			<table style="width: 100%;border-collapse:separate;border-spacing:0px 10px;">
			<tr>
				<td align="left" style="width: 60px;">ECS</td>
				<td align="right" style="padding: 10px;">${var.virNum}台&nbsp;&nbsp;<div style="float: right;background-image: url(images/close.gif);" onmouseover="$(this).addClass('img_close_mouseover')" onmouseout="$(this).removeClass('img_close_mouseover')" onclick="delPckg(this, '${var.id}', '${var.pckgName}')" class="img_close"></div></td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">计费方式：</td>
				<td align="left">包年包月</td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">地域：</td>
				<td align="left">${var.areaCode}</td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">实例规格：</td>
				<td align="left">${var.cpu}&nbsp;核&nbsp;${var.memory}&nbsp;GB</td>
			</tr>
			<tr>
				<td align="left">I/O优化：</td>
				<td align="left">I/O优化实例</td>
			</tr>
			<tr>
				<td align="left">网络：</td>
				<td align="left">公网带宽&nbsp;4Mbps(专用网络)</td>
			</tr>
			<tr>
				<td align="left">镜像：</td>
				<td align="left">${var.osType}&nbsp;${var.osBitNum}&nbsp;位</td>
			</tr>
			<tr>
				<td align="left">系统盘：</td>
				<td align="left">默认</td>
			</tr>
			<tr>
				<td align="left">数据盘：</td>
				<td align="left">${var.diskType}&nbsp;(&nbsp;${var.diskSize}&nbsp;GB)</td>
			</tr>
			<tr>
				<td align="left">购买量：</td>
				<td align="left">1&nbsp;个月&nbsp;${var.virNum}&nbsp;台</td>
			</tr>
			</table>
		</td>
	</tr>
	</c:forEach>
	</c:if>
</table>
<!-- <table style="position:fixed;bottom:0;width:100%;border-top:1px solid #f5f5f5;">
	<tr>
		<td align="left" style="padding:10px;">
			<div class="divbtn">
			    <span class="btncls" style="background-color:#f5620a;"><a id="addList" href="javascript:void()" onclick="addList()">加入清单</a></span>  
			    <span style="width:30px;float:right;">&nbsp;</span>
			    <span id="savePckgBtnId" class="btncls"><a href="javascript:void()" onclick="savePckgPre()">保存为套餐</a></span>
			</div>
		</td>
	</tr>
</table>
<table style="width:100%;border-top:1px solid #f5f5f5;"><tr><td style="padding:10px;"><div class="divbtn"></div></td></tr></table> -->
</form>
</body>
</html>