// 判断是否为0－9的数字字符
function isNumber(e) {
	for (i = 0; i < e.length; i++) {
		var oneChar = e.charAt(i);
		if (oneChar < '0' || oneChar > '9') {
			return false;
		}
	}
	return true;
}

// check empty
function isEmpty(e) {
	var newString = trim(e);
	if (newString == null || newString == "")
		return true;
	else
		return false;
}

// check date，输入是YYYY-MM-DD
function isDate(str){
	if (str.length != 10) {
		return false;
	}
	
	str=str.replace(/\-/g, "");//全部替换
	var y = str.substring(0, 4);
	var m = str.substring(4, 6) - 1;
	var d = str.substring(6, 8);

	var date = new Date(y, m, d);
	if ((date.getFullYear() == y) && (date.getMonth() == m) && (date.getDate() == d)) {
		return true;
	}
	
	return false;
}

// str前后的空格除去
function trim(str) {
	var returnstr = "";
	if (str == "")
		return "";
	var i = 0;
	for (i = 0; i < str.length; i++) {
		if (str.charAt(i) == ' ') {
			continue;
		}
		break;
	}
	// str = "" + str;
	str = str.substring(i, str.length);
	if (str == "")
		return "";
	for (i = str.length - 1; i >= 0; i--) {
		if (str.charAt(i) == ' ') {
			continue;
		}
		break;
	}
	returnstr = str.substring(0, i + 1);
	return returnstr;
}