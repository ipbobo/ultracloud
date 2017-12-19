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


//获取饼状图，必须先引入echarts.min.js
function getPieChart(chartId, titleArr, keyArr, valArr, colorArr, unit){
	var dataArr=[];
	if(keyArr.length==valArr.length && keyArr.length==colorArr.length){
		for(var i=0;i<keyArr.length;i++){
			dataArr.push({name: keyArr[i], value: valArr[i]});
		}
	}
	
	var option = {
	    title : {text: titleArr[0], subtext: titleArr[1], x:'center'},  
	    tooltip : {trigger: 'item', formatter: "{a}<br>{b}: {c}"+unit, textStyle:{align:'left'}},//item或axis
	    legend: {orient : 'vertical', x : 'left', data: keyArr}, 
	    color: colorArr,
	    toolbox: {
	        show : false,  
	        feature : {  
	            mark : {show: true},  
	            dataView : {show: true, readOnly: false},  
	            restore : {show: true},  
	            saveAsImage : {show: true}  
	        }
	    },  
	    calculable : true,  
	    series : [
	        {
	            name: titleArr[0],  
	            type: 'pie',  
	            radius: '35%',//饼图的半径大小  
	            center: ['50%', '50%'],//饼图的位置  
	            data: dataArr
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option);//加载图表
}

//获取柱状图，必须先引入echarts.min.js
function getBarChart(chartId, titleArr, keyArr, valArr, colorArr, unit){
	var option = {
		title : {text: titleArr[0], subtext: titleArr[1], x: 'center', y: 'bottom'},
	    tooltip : {trigger: 'item', formatter: "{a}<br>{b}: {c}"+unit, textStyle:{align:'left'}},//item或axis
	    legend: {data: keyArr},
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    grid: {
	        borderWidth: 0,
	        y: 50,
	        y2: 50
	    },
	    xAxis : [
	        {
	            type : 'category',
	            show: true,
	            data : keyArr
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
            	show: true
	        }
	    ],
	    series : [
	        {
	            name: titleArr[0],
	            type: 'bar',
	            itemStyle: {
	                normal: {
	                    color: function(params) {
	                        return colorArr[params.dataIndex]
	                    },
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: '{c}'+unit
	                    }
	                }
	            },
	            data: valArr
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option);//加载图表
}

//发送Http请求
function sendHttpPost(url, jsonObj, func){
    $.ajax({
	    type: 'post',  
	    url: url,
	    data: jsonObj,
	    dataType: 'json',  
	    success: function(data){
	    	func(data, true);//回调函数，即执行传递进来函数
	    },
	    error: function(data) {
	    	func(data, false);//回调函数，即执行传递进来函数
	    }
	});
}