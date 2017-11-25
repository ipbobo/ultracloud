<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
//删除清单
function delCmpOrder(obj, orderId){
	if(confirm("确定要删除该清单["+orderId+"]吗?")){
		$.ajax({
		    type: 'post',  
		    url: 'delCmpOrder.do?orderId='+orderId,
		    dataType: 'json',  
		    success: function(data){
			    if(data.retCode=="0"){//删除成功
			    	$(obj).parent().parent().parent().parent().parent().parent().remove();
			    }
		    },
		    error: function(data) {}
		});
	}
}

//清空购物车
function clearShoppingCart(){
	if(confirm("确定要清空购物车吗?")){
		$.ajax({
		    type: 'post',  
		    url: "clearShoppingCart.do",
		    dataType: 'json',  
		    success: function(data){
		    	alert(data.retMsg);
		    	$("input:checkbox[name='orderId']").each(function() {
					$(this).parent().parent().remove();
				});
		    },
		    error: function(data) {
		    	alert(data.retMsg);
		    }
		});
	}
}

//批量购买
function batchBuy(){
	var len = $("input:checkbox[name='orderId']:checked").length;
    if(len==0){
    	alert("请选择清单");
    	return;
    }
    
    var orderIdArr=new Array();
	$("input:checkbox[name='orderId']:checked").each(function() {
		orderIdArr.push($(this).val());
	});
	
	$.ajax({
	    type: 'post',  
	    url: "appCommit.do",
	    data: {"orderIdStr": orderIdArr.join()},
	    dataType: 'json',  
	    success: function(data){
	    	alert(data.retMsg);
		    maskLayerClick();//关闭遮罩层
	    },
	    error: function(data) {
	    	alert(data.retMsg);
	    }
	});
}
</script>
</head>
<body>
<table style="width: 100%;" border=1>
	<c:if test="${not empty shoppingCartList}">
	<c:forEach items="${shoppingCartList}" var="var" varStatus="st">
	<tr style="width: 100%;border:1px solid #cccccc;">
		<td align="center" style="width: 30px;">
			<input type="checkbox" name="orderId" id="orderId" value="${var.id}" checked/>
		</td>
		<td>
			<table style="width: 100%;border-collapse:separate;border-spacing:0px 10px;">
			<tr>
				<td align="left" style="width: 60px;">ECS</td>
				<td align="right" style="padding: 10px;">${var.virNum}台&nbsp;&nbsp;<div style="float: right;background-image: url(images/close.gif);" onmouseover="$(this).addClass('img_close_mouseover')" onmouseout="$(this).removeClass('img_close_mouseover')" onclick="delCmpOrder(this, '${var.id}')" class="img_close"></div></td>
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
</body>
</html>