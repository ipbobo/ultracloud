<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TCMP Cloud Console</title>

	<style>
		html, body {
			width: 100%;
			height: 100%;
			margin: 0;
			overflow: hidden;
		}
		.console {
			width: 100%;
			height: 100%;
			border: none;
		}
	</style>

	<script>
		document.getElementsByTagName('title')[0].innerHTML = 'TCMP Cloud Console';
	</script>
</head>
<body>
	<iframe class="console" src="http://ecs.binarii.me:4200"></iframe>
</body>
</html>