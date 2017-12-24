<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
<script type="text/javascript">
	function showDialog(msg){
		$('#msg_div').text(msg);
		top.hangge();
		$('#msgModal').modal('show');
	}
	function closeDialog(){
		$('#msgModal').modal('hide');
		//top.Dialog.close();
	}
</script>
</head>
<body>
<!-- 模态框（Modal） -->
<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="msgModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="msgModalLabel">提示信息</h4>
            </div>
            <div class="modal-body" id="msg_div"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="closeDialog();" data-dismiss="modal">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>