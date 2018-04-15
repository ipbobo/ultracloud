<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Console</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="static/webmks/css/wmks-all.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.8.16/jquery-ui.min.js"></script>
<!-- <script type="text/javascript" src="https://180.169.225.158/vsphere-client/js/wmks.js"></script> -->
<script type="text/javascript" src="static/webmks/wmks.min.js"></script>
<div id="wmksContainer" style="position:absolute;width:100%;height:100%"></div>
<script>
    var wmks = WMKS.createWMKS("wmksContainer",{})
        .register(WMKS.CONST.Events.CONNECTION_STATE_CHANGE, function(event,data){
            if(data.state == WMKS.CONST.ConnectionState.CONNECTED){
                console.log("connection state change : connected");
            }
        });
    wmks.connect("wss://${Host}/ticket/${Ticket}");
</script>
</body>
</html>