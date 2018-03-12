<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>
<link rel="stylesheet" href="plugins/dragula/dist/dragula.min.css">
<link rel="stylesheet" href="plugins/dragula/example/example.css">
	<style>
		body {
			background-color: #FFFFFF;
			max-width: 80%;
			}
		.parent {
		  	background-color: #F5F5F5; 
		  	margin: 5px 0;
		  	padding: 20px;
		}
		.container {
			display: table-cell;
		  	background-color: #DCDCDC;
		  	width: 50%;
		}
		#right-events {
		  	background-color: #D3D3D3;
		}

	</style>
</head>
<body class="no-skin">
<div class="main-container" id="main-container">
<form action="autodeployconfig/bind.do" name="form1" id="form1"  method="post">
	<input type="hidden" name="id" id="id" value="${pd.id}"/>
	<input type="hidden" name="selectedNodeIds" id="selectedNodeIds"/>
	<input type="hidden" name="notBindingNodeList" id="notBindingNodeList" value="${notBindingNodeList}"/>
	<input type="hidden" name="bindedNodeList" id="bindedNodeList" value="${bindedNodeList}"/>
	<div id="zhongxin" style="padding-top: 13px;">
	<div class='parent'>
    <div class='wrapper'>
      <div id='left-events' class='container'>
      </div>
      <div id='right-events' class='container'>
      </div>
    </div>
    <br/>
  </div>
  
  	<div style="text-align:center;">
	    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
		<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
	</div>
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>
	<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
</div>
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src='plugins/dragula/dist/dragula.min.js'></script>
	<script src='plugins/dragula/example/example.min.js'></script>
	<script src='js/json2.js'></script>
	<script type="text/javascript">
	top.hangge();
	
	$(function() {
		var leftNodeText = genNodeListDiv($("#notBindingNodeList").val());
		var rightNodeText = genNodeListDiv($("#bindedNodeList").val());
		
		document.getElementById("left-events").innerHTML = leftNodeText;
		document.getElementById("right-events").innerHTML = rightNodeText;
	});
	
	function genNodeListDiv(nodeList) {
		nodeText = "";
		if(nodeList.length>2) {
			var nodeArray = nodeList.substring(2,nodeList.length-2).split("}, {");
			for(i = 0; i < nodeArray.length; i++) {
				var id;
				var name;
				var nodeObj = nodeArray[i].split(", ");
				   
				for(j = 0; j < nodeObj.length; j++) {
					   var node = nodeObj[j].split("=");
					   if("id" == node[0]) {
						   id = node[1];
					   } else if("name" == node[0]) {
						   name = node[1];
					   }
				}
				nodeText += "<div id='"+id+"'>"+name+"</div>";
			}
		}
		
		return nodeText;
	}
	
	//保存配置流程 
	function save(){
		    var nodeIds = '';
			$("#right-events").children("div").each(function(){
				nodeIds+=this.id+",";
			 });
			$("#selectedNodeIds").val(nodeIds);
			$("#form1").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
	}
	
	dragula([document.getElementById(left), document.getElementById(right)])
	  .on('drag', function (el) {
	    el.className = el.className.replace('ex-moved', '');
	  }).on('drop', function (el) {
	    el.className += ' ex-moved';
	  }).on('over', function (el, container) {
	    container.className += ' ex-over';
	  }).on('out', function (el, container) {
	    container.className = container.className.replace('ex-over', '');
	  });
	</script>

</body>
</html>

