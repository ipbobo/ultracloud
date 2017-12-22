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
	    grid: {borderWidth: 0, y: 20, y2: 50},
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
	            //barWidth : 30,//柱图宽度
	            data: valArr
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option);//加载图表
}

//获取仪表盘，必须先引入echarts.min.js
function getGaugeChart(chartId, titleArr, val, colorArr, unit){
	var option = {
	    tooltip : {formatter: "{a}<br>{b}: {c}"+unit},
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    series : [
	        {
	            name: titleArr[0],
	            type: 'gauge',
	            splitNumber: 10,//分割段数，默认为5
	            axisLine: {//坐标轴线
	                lineStyle: {//属性lineStyle控制线条样式
	                    color: colorArr, 
	                    width: 8
	                }
	            },
	            axisTick: {//坐标轴小标记
	                splitNumber: 10,//每份split细分多少段
	                length: 12,//属性length控制线长
	                lineStyle: {//属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {//坐标轴文本标签，详见axis.axisLabel
	                textStyle: {//其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto'
	                }
	            },
	            splitLine: {//分隔线
	                show: true,//默认显示，属性show控制显示与否
	                length: 30,//属性length控制线长
	                lineStyle: {//属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width: 5
	            },
	            title: {
	                show: true,
	                offsetCenter: [0, '-40%'],//x, y，单位px
	                textStyle: {//其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail: {
	                formatter: '{value}'+unit,
	                textStyle: {//其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{name: titleArr[1], value: val}]
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option, true);//加载图表
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