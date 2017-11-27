<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
//删除套餐
function delPckg(obj, pckgId, pckgName){
	if(confirm("确定要删除该套餐["+pckgName+"]吗?")){
		$.ajax({
		    type: 'post',  
		    url: 'delPckg.do?pckgId='+pckgId,
		    dataType: 'json',  
		    success: function(data){
			    if(data.retCode=="0"){//删除成功
			    	$(obj).parent().parent().parent().parent().parent().remove();
			    }
		    },
		    error: function(data) {}
		});
	}
}

//获取IOPS
function getIops(diskType, diskSize, iopsId){
	if(diskType=="1"){//高效云盘1120+6
		$("#"+iopsId).html(1120+(diskSize-20)*6);
	}else if(diskType=="2"){//SSD云盘1800+30
		$("#"+iopsId).html(1800+(diskSize-20)*30);
	}
}

//新增套餐磁盘行
function addPckgDiskRow(diskType, diskSize, diskEncrypt){
	var diskTypes=diskType.split(",");
    var diskSizes=diskSize.split(",");
    var diskEncrypts=diskEncrypt.split(",");
    $.each($("#tcdiskTableId tr"), function (i, item) {
    	if(i!=0)item.remove();
	});
	
	$("#tcdiskType").val(diskTypes[0]);
    $("#tcdiskSize").val(diskSizes[0]);
    $("#tcdiskEncrypt").prop("checked", diskEncrypts[0]==1);//复选框，注意要用prop()，attr()只能赋值为初始值
    getIops(diskTypes[0], diskSizes[0], "tciopsId");//获取IOPS
    $.each(diskTypes, function (i, item) {
	    if(i!=0){
		    var tdStr="<td align=\"left\" style=\"width: 120px;padding-right:10px;padding-top:10px;\"><select class=\"chosen-select form-control\" name=\"tcdiskType\" id=\"tcdiskType"+i+"\" data-placeholder=\"请选择磁盘类型\" style=\"vertical-align:top;width: 120px;\" disabled>"+$("#tcdiskType").html()+"</select></td>"
			    +"<td align=\"left\" style=\"width: 120px;padding-top:10px;\"><input type=\"text\" name=\"tcdiskSize\" id=\"tcdiskSize"+i+"\" value=\"20\" style=\"width: 120px;\" maxlength=\"5\" disabled/></td>"
			    +"<td align=\"left\" style=\"width: 200px;padding-top:10px;\">GB&nbsp;&nbsp;&nbsp;<span id=\"tciopsId"+i+"\">1120</span>&nbsp;IOPS&nbsp;&nbsp;&nbsp;<input name=\"tcdiskEncrypt\" id=\"tcdiskEncrypt"+i+"\" type=\"checkbox\" value=\"\" disabled/>加密</td>";
		    $("#tcdiskTableId").append("<tr>"+tdStr+"</tr>");
		    $("#tcdiskType"+i).val(item);
		    $("#tcdiskSize"+i).val(diskSizes[i]);
		    $("#tcdiskEncrypt"+i).prop("checked", diskEncrypts[i]==1);//不选择复选框
		    getIops(item, diskSizes[i], "tciopsId"+i);//获取IOPS
	    }
    });
}

//新增套餐软件安装行
function addPckgSoftRow(softName, softVer, softParam){
	var softNames=softName.split(",");
    var softVers=softVer.split(",");
    var softParams=softParam.split(",");
    $.each($("#tcsoftTableId tr"), function (i, item) {
    	if(i!=0)item.remove();
	});
	
	$("#tcsoftName").val(softNames[0]);
    $("#tcsoftVer").val(softVers[0]);
    $("#tcsoftParam").val(softParams[0]);
    $.each(softNames, function (i, item) {
	    if(i!=0){
	    	var tdStr="<td align=\"left\" style=\"width: 120px;padding-right:10px;padding-top:10px;\"><select class=\"chosen-select form-control\" name=\"tcsoftName\" id=\"tcsoftName"+i+"\" data-placeholder=\"请选择软件名称\" style=\"vertical-align:top;width: 120px;\" disabled>"+$("#tcsoftName").html()+"</select></td>"
		    	+"<td align=\"left\" style=\"width: 120px;padding-top:10px;\"><select class=\"chosen-select form-control\" name=\"tcsoftVer\" id=\"tcsoftVer"+i+"\" data-placeholder=\"请选择软件版本\" style=\"vertical-align:top;width: 120px;\" disabled>"+$("#tcsoftVer").html()+"</select></td>"
			    +"<td align=\"left\" style=\"width: 120x;padding-top:10px;\"><input type=\"hidden\" name=\"tcsoftParam\" id=\"tcsoftParam"+i+"\" value=\"\"/></td>";
			$("#tcsoftTableId").append("<tr>"+tdStr+"</tr>");
		    $("#tcsoftName"+i).val(item);
		    $("#tcsoftVer"+i).val(softVers[i]);
		    $("#tcsoftParam"+i).val(softParams[i]);
	    }
    });
}

//选择套餐
function choosePckg(jsonStr){
	var jsonObj=$.parseJSON(jsonStr);//套餐JSON对象
	$("#pckgId").val(jsonObj.id);
	setFieldValue(document.getElementById("tcenvCodeId"+jsonObj.envCode), 'tcenvCode', jsonObj.envCode);
	$("#tcprojectCode").val(jsonObj.projectCode);
	setFieldValue(document.getElementById("tcresTypeId"+jsonObj.resType), 'tcresType', jsonObj.resType);
	//$("#tcvirName").val(jsonObj.virName);
	setFieldValue(document.getElementById("tccpuId"+jsonObj.cpu), 'tccpu', jsonObj.cpu);
	setFieldValue(document.getElementById("tcmemoryId"+jsonObj.memory), 'tcmemory', jsonObj.memory);
	$("#tcosType").val(jsonObj.osType);
	$("#tcosBitNum").val(jsonObj.osBitNum);
	$("#tcimgCode").val(jsonObj.imgCode);
	$("#tcimgUserName").val(jsonObj.imgUserName);
	$("#tcimgUserPass").val(jsonObj.imgUserPass);
	$("#tcimgPath").val(jsonObj.imgPath);
	$("#tcimgExpireDate").val(jsonObj.imgExpireDate);
	$("#tcexpireDate").val(jsonObj.imgExpireDate);
	$("#tcvirNum").val(jsonObj.virNum);
	addPckgDiskRow(jsonObj.diskType, jsonObj.diskSize, jsonObj.diskEncrypt);//新增套餐磁盘行
	addPckgSoftRow(jsonObj.softName, jsonObj.softVer, jsonObj.softParam);//新增套餐软件安装行
}
</script>
</head>
<body>
<form id="tcmainForm" name="tcmainForm" action="" enctype="multipart/form-data" method="post">
<input type="hidden" name="tcareaCode" id="tcareaCode" value="1"/>
<input type="hidden" name="tcplatType" id="tcplatType" value="vmware"/>
<input type="hidden" name="tcdeployType" id="tcdeployType" value="1"/>
<input type="hidden" name="pckgId" id="pckgId" value=""/>
<table style="width:100%;margin-top: 0px;margin-left: 0px;background-color: #e4e6e9;">
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;地域</td>
		<td align="right" style="width: 120px;padding:10px;">地域：</td>
		<td align="left" style="padding:10px;" colspan="6">
			<ul id="tcareaCodeId" class="ullitab list-inline">
				<c:if test="${not empty areaCodeList}">
				<c:forEach items="${areaCodeList}" var="var" varStatus="st">
				<li onclick="setFieldValue(this, 'tcareaCode', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;平台类型</td>
		<td align="right" style="width: 120px;padding:10px;">平台类型：</td>
		<td align="left" style="padding:10px;" colspan="6">
			<ul id="tcplatTypeId" class="ullitab list-inline">
				<c:if test="${not empty platTypeList}">
				<c:forEach items="${platTypeList}" var="var" varStatus="st">
				<li onclick="setFieldValue(this, 'tcplatType', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;部署类型</td>
		<td align="right" style="width: 120px;padding:10px;">部署类型：</td>
		<td align="left" style="padding:10px;" colspan="6">
			<ul id="tcdeployTypeId" class="ullitab list-inline">
				<c:if test="${not empty deployTypeList}">
				<c:forEach items="${deployTypeList}" var="var" varStatus="st">
				<li onclick="setFieldValue(this, 'tcdeployType', '${var.dictCode}')" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;套餐列表</td>
		<td align="left" style="padding:10px;" colspan="7">
			<table style="width: 100%;border:1px solid #cccccc;">
				<c:if test="${not empty pckgList}">
				<c:forEach items="${pckgList}" var="var" varStatus="st">
				<tr>
					<td align="left" style="width: 120px;padding:10px;border-right:1px solid #cccccc;">
						<span style="float: left;"><input type="radio" name="tcName" id="tctableId" onclick='choosePckg("{\"id\":\"${var.id}\",\"envCode\":\"${var.envCode}\",\"projectCode\":\"${var.projectCode}\",\"resType\":\"${var.resType}\",\"virName\":\"${var.virName}\",\"cpu\":\"${var.cpu}\",\"memory\":\"${var.memory}\",\"osType\":\"${var.osType}\",\"osBitNum\":\"${var.osBitNum}\",\"imgCode\":\"${var.imgCode}\",\"imgUserName\":\"${var.imgUserName}\",\"imgUserPass\":\"${var.imgUserPass}\",\"imgPath\":\"${var.imgPath}\",\"imgExpireDate\":\"${var.imgExpireDate}\",\"diskType\":\"${var.diskType}\",\"diskSize\":\"${var.diskSize}\",\"diskEncrypt\":\"${var.diskEncrypt}\",\"softName\":\"${var.softName}\",\"softVer\":\"${var.softVer}\",\"softParam\":\"${var.softParam}\",\"expireDate\":\"${var.expireDate}\",\"virNum\":\"${var.virNum}\"}	")'/>${var.pckgName}</span>
						<div style="float: left;background-image: url(images/close.gif);" onmouseover="$(this).addClass('img_close_mouseover')" onmouseout="$(this).removeClass('img_close_mouseover')" onclick="delPckg(this, '${var.id}', '${var.pckgName}')" class="img_close"></div>
					</td>
					<td>
						<table style="width: 100%;border-collapse:separate;border-spacing:0px 10px;">
						<tr>
							<td align="right" style="width: 120px;">计费方式：</td>
							<td align="left" style="width: 180px;">包年包月</td>
							<td align="right" style="width: 120px;">实例规格：</td>
							<td align="left" style="width: 180px;">${var.cpu}&nbsp;核&nbsp;${var.memory}&nbsp;GB</td>
							<td align="right" style="width: 120px;">I/O优化：</td>
							<td align="left" style="width: 180px;">I/O优化实例</td>
						</tr>
						<tr>
							
							<td align="right" style="width: 120px;">镜像：</td>
							<td align="left" style="width: 180px;">${var.osType}&nbsp;${var.osBitNum}&nbsp;位</td>
							<td align="right" style="width: 120px;">数据盘：</td>
							<td align="left" style="width: 180px;">${var.diskType}&nbsp;(&nbsp;${var.diskSize}&nbsp;GB)</td>
							<td align="right" style="width: 120px;">购买量：</td>
							<td align="left" style="width: 180px;">1&nbsp;个月&nbsp;${var.virNum}&nbsp;台</td>
						</tr>
						</table>
					</td>
				</tr>
				</c:forEach>
				</c:if>
			</table>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top" rowspan="2"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;项目</td>
		<td align="right" style="width: 120px;padding:10px;">环境：</td>
		<td align="left" style="padding:10px;" colspan="6">
			<ul id="tcenvCodeId" class="ullitab list-inline">
				<c:if test="${not empty envCodeList}">
				<c:forEach items="${envCodeList}" var="var" varStatus="st">
				<li id="tcenvCodeId${var.dictCode}" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">项目：</td>
		<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;" colspan="6">
			<select class="chosen-select form-control" name="tcprojectCode" id="tcprojectCode" data-placeholder="请选择项目" style="vertical-align:top;width: 120px;" disabled>
			<option value="">请选择</option>
			<c:forEach items="${projectList}" var="var">
				<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
			</c:forEach>
		  	</select>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top" rowspan="5"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;基本配置</td>
		<td align="right" style="width: 120px;padding:10px;">资源类型：</td>
		<td align="left" style="padding:10px;" colspan="6">
			<ul id="tcresTypeId" class="ullitab list-inline">
				<c:if test="${not empty resTypeList}">
				<c:forEach items="${resTypeList}" var="var" varStatus="st">
				<li id="tcresTypeId${var.dictCode}" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">虚拟机名称：</td>
		<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="6">
			<input type="text" name="tcvirName" id="tcvirName"  value=""/>
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">推荐配置：</td>
		<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="6">
			<ul id="tcrecommendTypeId" class="ullitab list-inline">
				<c:if test="${not empty recommendTypeList}">
				<c:forEach items="${recommendTypeList}" var="var" varStatus="st">
				<li class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">CPU：</td>
		<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="6">
			<ul id="tccpuId" class="ullitab list-inline">
				<c:if test="${not empty cpuList}">
				<c:forEach items="${cpuList}" var="var" varStatus="st">
				<li id="tccpuId${var.dictCode}" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">内存：</td>
		<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="6">
			<ul id="tcmemoryId" class="ullitab list-inline">
				<c:if test="${not empty memoryList}">
				<c:forEach items="${memoryList}" var="var" varStatus="st">
				<li id="tcmemoryId${var.dictCode}" class=${var.dictDefault=='1'?"active":""}>${var.dictValue}</li>
				</c:forEach>
				</c:if>
			</ul>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top" rowspan="3"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;镜像</td>
		<td align="right" style="width: 120px;padding:10px;">操作系统：</td>
		<td align="left" style="width: 120px;padding:10px;">
			<select class="chosen-select form-control" name="tcosType" id="tcosType" data-placeholder="请选择操作系统" style="vertical-align:top;width: 100%;" disabled>
			<option value="">请选择</option>
			<c:forEach items="${osTypeList}" var="var">
				<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
			</c:forEach>
		  	</select>
		</td>
		<td align="right" style="width: 120px;padding:10px;">位数：</td>
		<td align="left" style="width: 120px;padding:10px;">
			<select class="chosen-select form-control" name="tcosBitNum" id="tcosBitNum" data-placeholder="请选择位数" style="vertical-align:top;width: 100%;" disabled>
			<option value="">请选择</option>
			<c:forEach items="${osBitNumList}" var="var">
				<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
			</c:forEach>
		  	</select>
		</td>
		<td align="right" style="width: 120px;padding:10px;">镜像信息：</td>
		<td align="left" style="padding:10px;" colspan="2">
			<select class="chosen-select form-control" name="tcimgCode" id="tcimgCode" data-placeholder="请选择镜像" style="vertical-align:top;width: 120px;" disabled>
			<option value="">请选择</option>
			<c:forEach items="${imgCodeList}" var="var">
				<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
			</c:forEach>
		  	</select>
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">用户名：</td>
		<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;">
			<input type="text" name="tcimgUserName" id="tcimgUserName" value="" disabled/>
		</td>
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">密码：</td>
		<td align="left" style="width: 120px;padding-left:10px;padding-bottom:10px;">
			<input type="text" name="tcimgUserPass" id="tcimgUserPass" value="" disabled/>
		</td>
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">路径：</td>
		<td align="left" style="padding-left:10px;padding-bottom:10px;" colspan="2">
			<input type="text" name="tcimgPath" id="tcimgPath" value="" disabled/>&nbsp;&nbsp;Linux系统必填
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">到期时间：</td>
		<td style="padding-left:10px;padding-bottom:10px;" colspan="6">
			<input type="text" name="tcimgExpireDate" id="tcimgExpireDate" value="" class="span10 date-picker" data-date-format="yyyy-mm-dd" style="width:120px;" placeholder="到期时间" disabled/>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;存储</td>
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">存储：</td>
		<td style="padding:10px;" colspan="6">
			<table id="tcdiskTableId">
				<tr>
					<td align="left" style="width: 120px;padding-right:10px;">
						<select class="chosen-select form-control" name="tcdiskType" id="tcdiskType" data-placeholder="请选择磁盘类型" style="vertical-align:top;width: 120px;" disabled>
						<option value="">请选择</option>
						<c:forEach items="${diskTypeList}" var="var">
							<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
						</c:forEach>
					  	</select>
					</td>
					<td align="left" style="width: 120px;">
						<input type="text" name="tcdiskSize" id="tcdiskSize" value="20" style="width: 120px;" maxlength="5" disabled/>
					</td>
					<td align="left" style="width: 200px;">
					  	GB&nbsp;&nbsp;&nbsp;<span id="tciopsId">1120</span>&nbsp;IOPS&nbsp;&nbsp;&nbsp;<input name="tcdiskEncrypt" id="tcdiskEncrypt" type="checkbox" value="" disabled/>加密
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;软件安装</td>
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">安装软件：</td>
		<td style="padding:10px;" colspan="6">
			<table id="tcsoftTableId">
				<tr>
					<td align="left" style="width: 120px;padding-right:10px;">
						<select class="chosen-select form-control" name="tcsoftName" id="tcsoftName" data-placeholder="请选择软件名称" style="vertical-align:top;width: 120px;" disabled>
						<option value="">请选择</option>
						<c:forEach items="${softNameList}" var="var">
							<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
						</c:forEach>
					  	</select>
					</td>
					<td align="left" style="width: 120px;">
						<select class="chosen-select form-control" name="tcsoftVer" id="tcsoftVer" data-placeholder="请选择软件版本" style="vertical-align:top;width: 120px;" disabled>
						<option value="">请选择</option>
						<c:forEach items="${softVerList}" var="var">
							<option value="${var.dictCode}" <c:if test="${var.dictDefault=='1'}">selected</c:if>>${var.dictValue}</option>
						</c:forEach>
					  	</select>
					</td>
					<td align="left" style="width: 120px;">
						<input type="hidden" name="tcsoftParam" id="tcsoftParam" value=""/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top" rowspan="2"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;数量</td>
		<td align="right" style="width: 120px;padding:10px;">到期时间：</td>
		<td style="padding:10px;" colspan="6">
			<input type="text" name="tcexpireDate" id="tcexpireDate" value="" class="span10 date-picker" data-date-format="yyyy-mm-dd" style="width:120px;" placeholder="到期时间" disabled/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" value="" disabled/>永久
		</td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding-right:10px;padding-bottom:10px;">数量：</td>
		<td style="width: 120px;padding-left:10px;padding-bottom:10px;" colspan="6">
			<div class="input-group spinner" data-trigger="spinner" id="spinner" style="width: 120px;"> 
			    <input type="text" id="tcvirNum" name="tcvirNum" class="form-control" value="1" data-max="1000" data-min="1" data-step="1" disabled> 
			    <div class="input-group-addon"> 
			        <a href="javascript:void();" class="spin-up" data-spin="up"><i class="icon-sort-up"></i></a> 
			        <a href="javascript:void();" class="spin-down" data-spin="down"><i class="icon-sort-down"></i></a> 
			    </div> 
			</div>
		</td>
	</tr>
	<tr><td colspan="8" height="10px"></td>
	<tr class="tablecls">
		<td align="left" style="width: 120px;padding-left:10px;background-color:#cccccc;" valign="top" rowspan="2"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;当前配置</td>
		<td align="right" style="width: 120px;padding:10px;">当前配置：</td>
		<td style="padding:10px;" colspan="6"></td>
	</tr>
	<tr class="tablecls">
		<td align="right" style="width: 120px;padding:10px;">计费方式：</td>
		<td align="left" style="width: 120px;padding:10px;">包年包月</td>
		<td align="right" style="width: 120px;padding:10px;">地域：</td>
		<td align="left" style="width: 120px;padding:10px;"><span id="areaCode"></span></td>
		<td align="right" style="width: 120px;padding:10px;">实例规格：</td>
		<td align="left" style="width: 120px;padding:10px;" colspan="2"><span id="areaCode"></span></td>
	</tr>
</table>
</form>
</body>
</html>