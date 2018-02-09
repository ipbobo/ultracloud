<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
var objId=null;
function showAlert(msg, idName){
	objId=arguments[1]?arguments[1]:null;//默认不关闭 
	$('#msg_div').text(msg);
	$('#msgModal').modal('show');
}

//关闭
function closeAlert(){
	$('#msgModal').modal('hide');
	if(objId) $("#"+objId).focus();
}
</script>
</head>
<body>
<!-- 模态框对话框 -->
<div class="modal fade" id="msgModal" role="dialog" aria-labelledby="msgModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 300px;height: 30px;z-index: 1040;">
        <div class="modal-content">
            <div style="height: 30px;padding: 2px;" class="modal-header">
                <button style="height: 100%;" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <span class="modal-title" id="msgModalLabel">提示信息</span>
            </div>
            <div class="modal-body" id="msg_div"></div>
            <div style="height: 30px;padding: 2px;" class="modal-footer">
            	<input style="height: 100%;" type="button" value="确定" id="msg_btn" onclick="closeAlert();">
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
$(top.hangge());
</script>
</body>
</html>