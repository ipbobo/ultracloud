<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
<script type="text/javascript">
	function showDialog(msg){
		alert('aaaa');
		$('#msgModalLabel').val(msg);
		$('#msgModal').modal('show');
	}
	function closeDialog(){
		$('#msgModal').modal('hide');
	}
</script>
</head>
<body>
<!-- ��ť����ģ̬�� -->
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">��ʾ��Ϣ</button>
<!-- ģ̬��Modal�� -->
<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="msgModalLabel">��ʾ��Ϣ</h4>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">ȷ��</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>