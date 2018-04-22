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
function getPieChart(chartId, titleArr, legendArr, radiusArr, centerArr, keyArr, valArr, colorArr, unit){
	var dataArr=[];
	if(keyArr.length==valArr.length && keyArr.length==colorArr.length){
		for(var i=0;i<keyArr.length;i++){
			dataArr.push({name: keyArr[i], value: valArr[i]});
		}
	}
	
	var option = {
	    title: {text: titleArr[0], subtext: titleArr[1], x:'center'},  
	    tooltip: {trigger: 'item', formatter: "{a}<br>{b}: {c}"+unit, textStyle:{align:'left'}},//item或axis
	    legend: {orient: legendArr[0], x: legendArr[1]?legendArr[1]:'center', y: legendArr[2]?legendArr[2]:'top', data: keyArr},
	    color: colorArr,
	    calculable: true,  
	    series: [
	        {
	            name: titleArr[0],  
	            type: 'pie',  
	            radius: radiusArr,//饼图的半径大小['35%']
	            center: centerArr,//饼图的位置['50%', '50%']
	            data: dataArr
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option);//加载图表
}

//获取环形图，必须先引入echarts.min.js
function getAnnularChart(chartId, titleArr, legendArr, radiusArr, centerArr, keyArr, valArr, colorArr, unit){
	var dataArr=[];
	if(keyArr.length==valArr.length && keyArr.length==colorArr.length){
		for(var i=0;i<keyArr.length;i++){
			dataArr.push({name: keyArr[i], value: valArr[i]});
		}
	}
	
	var option = {
		title: {text: titleArr[0], subtext: '', x:'center', y: '100', textStyle: {color: '#000000', fontSize: '12', fontWeight: 'normal'}},  
	    tooltip: {trigger: 'item', formatter: "{a}<br>{b}: {c}({d}"+unit+")", textStyle:{align:'left'}},//item或axis
	    legend: {orient: legendArr[0], x: legendArr[1]?legendArr[1]:'center', y: legendArr[2]?legendArr[2]:'top', data: keyArr},//horizontal、vertical
	    color: colorArr,
	    calculable: true,  
	    series: [
	        {
	            name: titleArr[1],  
	            type: 'pie',
	            hoverAnimation:false,
	            radius: radiusArr,//饼图的半径大小['30%', '50%']
	            center: centerArr,//饼图的位置['50%', '75%']
	            itemStyle: {
	            	normal: {label: {show: false}, labelLine: {show: false}}, 
	            	emphasis: {label: {show: true, position: 'inside', textStyle: {fontSize: '12', fontWeight: 'bold'}}}
	            },
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
		title: {text: titleArr[0], subtext: titleArr[1], x: 'center', y: 'bottom', textStyle: {fontSize: '15'}},
	    tooltip: {trigger: 'item', formatter: "{a}<br>{b}: {c}"+unit, textStyle:{align:'left'}},//item或axis
	    legend: {data: keyArr},
	    calculable: true,
	    grid: {borderWidth: 0, y: 20, y2: 50},
	    xAxis: [
	        {
	            type: 'category',
	            show: true,
	            data: keyArr,
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color: '#1c96d5'//左边线的颜色
                    }
                }//@zjb 2017-4-21
	        }
	    ],
	    yAxis: [
	        {
                splitLine:{show: false},//去掉网格线 @zjb 2017-4-21
                type: 'value',
                show: true,
                splitArea : {show : true},//显示网格 @zjb 2017-4-21
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color: '#1c96d5'//左边线的颜色
                    }
                }//@zjb 2017-4-21
	        }
	    ],
	    series: [
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
	            //barWidth: 30,//柱图宽度
	            data: valArr
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option);//加载图表
}

//获取仪表盘，必须先引入echarts.min.js
function getGaugeChart(chartId, titleArr, centerArr, offsetCenterArr, val, colorArr, unit, splitNumber){
	var option = {
	    tooltip: {formatter: (titleArr[1]?"{b}: ":"")+"{c}"+unit},
	    series: [
	        {
	            name: titleArr[0],
	            type: 'gauge',
	            center: centerArr,//圆心坐标：默认全局居中
	            precision: 0,//小数精度，默认为0，无小数点
	            splitNumber: splitNumber?splitNumber:5,//分割段数，默认为5
	            axisLine: {//坐标轴线
	                lineStyle: {//属性lineStyle控制线条样式
	                    color: colorArr, 
	                    width: 8
	                }
	            },
	            axisTick: {//坐标轴小标记
	                splitNumber: splitNumber?splitNumber:5,//每份split细分多少段
	                length: 11,//属性length控制线长
	                lineStyle: {//属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {//坐标轴文本标签，详见axis.axisLabel
	            	margin: 0,
	                textStyle: {//其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontSize: 10
	                }
	            },
	            splitLine: {//分隔线
	                show: true,//默认显示，属性show控制显示与否
	                length: 14,//属性length控制线长
	                lineStyle: {//属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width: 5
	            },
	            title: {
	                show: true,
	                offsetCenter: offsetCenterArr,//x, y，单位px：[0, 70]
	                textStyle: {//其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            detail: {
	            	show: true,
	                formatter: '{value}'+unit,
	                offsetCenter: ['0', 38],//x, y，单位px
	                width: 0,
	                height: 0,
	                textStyle: {//其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    color: 'auto',
	                    fontWeight: 'normal',
	                    fontSize: '18'
	                }
	            },
	            data:[{name: titleArr[1], value: (val*1).toFixed(2)}]
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option, true);//加载图表
}

//获取折线图，必须先引入echarts.min.js
function getLineChart(chartId, titleArr, xdataArr, ydataArr){
	var option = {
	    tooltip: {trigger: 'axis', formatter: "{c}: {b}", textStyle:{align:'left'}},//item或axis
	    legend: {data: titleArr[0]},
	    calculable: true,
	    grid: {x: 25, y: 5, x2: 15, y2: 25},
	    xAxis: [
	        {
	        	show: true,
	            type: 'category',
	            boundaryGap : false,
	            axisLabel : {
	                formatter: '{value}',
	                interval: 0,//全部显示，仅type为category有效,
                    textStyle: {
                        color: '#888888',//坐标值得具体的颜色

                    }
	            },
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color: '#ffffff'//左边线的颜色
                    }
                },
                axisTick:{
                    show:false
                },
                splitLine: {
                    lineStyle: {
                        color: ['#f8f8f8']
                    }
                },
	            data : xdataArr
	        }
	    ],
	    yAxis: [
	        {
	        	show: true,
	            type: 'value',
	            min : '0',
	            max : '1',
	            splitNumber: '6',
	            scale: true,
	            boundaryGap : false,
	            axisLabel : {
	                formatter: '{value}',
                    textStyle: {
                        color: '#888888',//坐标值得具体的颜色

                    }
	            },
                axisLine: {
                    onZero: false,
                    lineStyle: {
                        type: 'solid',
                        color: '#ffffff'//左边线的颜色
                    }
                },
                axisTick:{
                    show:false
                },
                splitLine: {
                    lineStyle: {
                        color: ['#f8f8f8']
                    }
                }
	        }
	    ],
	    series: [
	        {
	            name: titleArr[0],
	            type: 'line',
	            smooth:true,
	            showAllSymbol : true,
	            itemStyle: {
	            	normal: {
                        color:'#3dbfa7',
                        lineStyle: {
	                        shadowColor : 'rgba(0,0,0,0.4)',
							color:'#3dbfa7'
	                    }
	                }
	            },
	            data: ydataArr
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById(chartId));
	myChart.setOption(option);//加载图表
}

//金额格式化
function amtFmt(amt, unit){
	if(''==amt || isNaN(amt)){return 'Not a Number ! ';}//如果amt不是数字，则将amt置0，并返回
	amt = amt.toString().replace(/\$|\,/g,'');//将amt中的$,去掉，将amt变成一个纯粹的数据格式字符串
	var sign = amt.indexOf("-")> 0 ? '-' : '';//如果amt是负数，则获取她的符号
	var cents = amt.indexOf(".")> 0 ? amt.substr(amt.indexOf(".")) : ''; //如果存在小数点，则获取数字的小数部分
	cents = cents.length>1 ? cents : '' ;//小数位(含小数点)
	amt = amt.indexOf(".")>0 ? amt.substring(0,(amt.indexOf("."))) : amt;//获取数字的整数数部分
	if('' == cents){ if(amt.length>1 && '0' == amt.substr(0,1)){return 'Not a Number ! ';}}//如果没有小数点，整数部分不能以0开头
	else{if(amt.length>1 && '0' == amt.substr(0,1)){return 'Not a Number ! ';}}//如果有小数点，且整数的部分的长度大于1，则整数部分不能以0开头
	for (var i = 0; i < Math.floor((amt.length-(1+i))/3); i++){
		amt = amt.substring(0,amt.length-(4*i+3))+','+amt.substring(amt.length-(4*i+3));
	}
    
	return (unit?unit:'￥')+sign+amt+cents;//将数据（符号、整数部分、小数部分）整体组合返回
}

//获取当前日志YYYY-MM-DD
function getCurrDate(){
	var currDate = new Date();
	var month = currDate.getMonth()+1;
	if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
	
	var day = currDate.getDate();
	if (day >= 0 && day <= 9) {
		day = "0" + day;
	}
	
	return currDate.getFullYear()+"-"+month+"-"+day;
}

function getDateDiff(beginDate, endDate) {
	if(beginDate=="" || endDate=="" || beginDate>=endDate){
		return 0;
	}
	
	if(endDate=="9999-12-31"){
		return 5;
	}
	
	var startTime = new Date(Date.parse(beginDate.replace(/-/g, "/"))).getTime();
	var endTime = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
	return Math.abs((endTime-startTime))/(1000 * 60 * 60 * 24);
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

//格式化mysql中的datetime时间格式
function formatDateTime(datetime) {
	var len = datetime.length;
	return datetime.substring(0, len-2);
}