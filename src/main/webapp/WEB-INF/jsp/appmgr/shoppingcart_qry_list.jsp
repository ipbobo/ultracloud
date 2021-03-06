<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="/t-tags"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
//全选
function checkAllFunc(){
	$("[name=orderNo]:checkbox").prop("checked", $("#checkAllId").is(":checked"));
	getAllTotalAmt();//购物车计算金额
}

//删除清单
function delCmpOrder(obj, orderNo){
	if(confirm("确定要删除该清单["+orderNo+"]吗?")){
		$.ajax({
		    type: 'post',  
		    url: 'delCmpOrder.do?orderNo='+orderNo,
		    dataType: 'json',  
		    success: function(data){
			    if(data.retCode=="0"){//删除成功
			    	$(obj).parent().parent().parent().parent().parent().parent().remove();
			    	$("#shoppingCartNum").html(($("#shoppingCartNum").html())*1-1);
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
		    	showAlert(data.retMsg);
		    	$("input:checkbox[name='orderNo']").each(function() {
					$(this).parent().parent().remove();
					$("#shoppingCartNum").html(0);
				});
				$("#allTotalAmt").html("￥0.00");
		    },
		    error: function(data) {
		    	showAlert(data.retMsg);
		    }
		});
	}
}

//批量购买
function batchBuy(){
	var len = $("input:checkbox[name='orderNo']:checked").length;
    if(len==0){
    	showAlert("请选择清单");
    	return;
    }
    
    var orderNoArr=new Array();
    var totalAmtArr=new Array();
    var allProjectCode="";
	$("input:checkbox[name='orderNo']:checked").each(function() {
		orderNoArr.push($(this).val());
		var dayNum=getDateDiff(getCurrDate(), $(this).siblings(".expireDate").val());
		var virNum=dayNum*($(this).siblings(".virNum").val());
		var cpuNum=virNum*($(this).siblings(".cpu").val());
		var memNum=virNum*($(this).siblings(".mem").val());
		var storeNum=virNum*getStoreNum($(this).siblings(".store").val());
		totalAmtArr.push((cpuNum*cpuPrice+memNum*memPrice+storeNum*storePrice).toFixed(2)+"");
		var projectCode=$(this).siblings(".projectCode").val();
		allProjectCode=(allProjectCode==""?projectCode:allProjectCode);
		if(allProjectCode!=projectCode){
			allProjectCode="";
    		return;
		}
	});
	
	if(allProjectCode==""){
		showAlert("该工单中包含多个项目");
   		return;
	}
	
	$.ajax({
	    type: 'post',
	    url: "appCommit.do",
	    data: {"orderNoStr": orderNoArr.join(), "totalAmtStr": totalAmtArr.join(), "allProjectCode": allProjectCode},
	    dataType: 'json',
	    success: function(data){
	    	showAlert(data.retMsg);
		    $("#shoppingCartNum").html(($("#shoppingCartNum").html())*1-len);
		    $("#buyHisNum").html(($("#buyHisNum").html())*1+len);
		    maskLayerClick();//关闭遮罩层
	    },
	    error: function(data) {
	    	showAlert(data.retMsg);
	    }
	});
}

//获取磁盘数量
function getStoreNum(storeVal){
	var storeNum=0;//磁盘数量
	var storeVals=storeVal.split(",");
	$.each(storeVals, function (i, item) {
		storeNum+=item*1;//磁盘数量
	});
	
	return storeNum;
}

//购物车计算金额
function getAllTotalAmt(){
	var cpuNum=0;
	var memNum=0;
	var storeNum=0;
	$("input:checkbox[name='orderNo']:checked").each(function() {
		var dayNum=getDateDiff(getCurrDate(), $(this).siblings(".expireDate").val());
		var virNum=dayNum*($(this).siblings(".virNum").val());
		cpuNum+=virNum*($(this).siblings(".cpu").val());
		memNum+=virNum*($(this).siblings(".mem").val());
		storeNum+=virNum*getStoreNum($(this).siblings(".store").val());
	});
	
	$("#allTotalAmt").html(amtFmt((cpuNum*cpuPrice+memNum*memPrice+storeNum*storePrice).toFixed(2)+"", '￥'));
}
</script>
</head>
<body>
<table style="width: 100%;">
	<c:if test="${not empty shoppingCartList}">
	<c:forEach items="${shoppingCartList}" var="var" varStatus="st">
	<tr style="width: 100%;border:1px solid #cccccc;">
		<td align="center" style="width: 30px;">
			<input type="checkbox" name="orderNo" value="${var.orderNo}" onclick="getAllTotalAmt()" checked/>
			<input type="hidden" name="cpuVal" value="${var.cpu}" class="cpu"/><!-- 购物车计算金额使用 -->
			<input type="hidden" name="memVal" value="${var.memory}" class="mem"/><!-- 购物车计算金额使用 -->
			<input type="hidden" name="storeVal" value="${var.diskSize}" class="store"/><!-- 购物车计算金额使用 -->
			<input type="hidden" name="virNumVal" value="${var.virNum}" class="virNum"/><!-- 购物车计算金额使用 -->
			<input type="hidden" name="expireDateVal" value="${var.expireDate}" class="expireDate"/><!-- 购物车计算金额使用 -->
			<input type="hidden" name="projectCode" value="${var.projectCode}" class="projectCode"/><!-- 工单合并时使用 -->
		</td>
		<td>
			<table style="width: 100%;border-collapse:separate;border-spacing:0px 10px;">
			<tr>
				<td align="left" style="width: 60px;" colspan="2">ECS（${var.orderNo}）</td>
				<td align="right" style="padding-right: 10px;">${var.virNum}台&nbsp;&nbsp;<div style="float: right;background-image: url(images/close.gif);" onmouseover="$(this).addClass('img_close_mouseover')" onmouseout="$(this).removeClass('img_close_mouseover')" onclick="delCmpOrder(this, '${var.orderNo}')" class="img_close"></div></td>
			</tr>
			<tr>
				<td align="left" style="width: 60px;">虚拟机名称：</td>
				<td align="left" colspan="2">${var.virName}</td>
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
				<td align="left">到期时间：</td>
				<td align="left" colspan="2">${var.expireDate}</td>
			</tr>
			<tr>
				<td align="left">申请时间：</td>
				<td align="left" colspan="2">${var.createTime}</td>
			</tr>
			</table>
		</td>
	</tr>
	</c:forEach>
	</c:if>
</table>
<script type="text/javascript">
$("#shoppingCartNum").html("${shoppingCartNum}");
getAllTotalAmt();//购物车计算金额
</script>
</body>
</html>