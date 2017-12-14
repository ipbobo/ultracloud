<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 百度echarts -->
<script src="plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
setTimeout("top.hangge()",500);
</script>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="hr hr-6 dotted hr-double"></div>
					<div class="row">
						<div class="col-xs-12">

							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i>
								欢迎使用 拓云管理平台&nbsp;&nbsp;
								<!-- <strong class="green">
									&nbsp;QQ:313596790
									<a href="http://www.fhadmin.org" target="_blank"><small>(&nbsp;www.fhadmin.org&nbsp;)</small></a>
								</strong> -->
							</div>
							
							
							<div id="main" style="width: 400px;height:400px;" class="col-xs-4 col-sm-4" ></div>
							<div id="main2" style="width: 400px;height:400px;" class="col-xs-4 col-sm-4" ></div>
							<div id="main3" style="width: 400px;height:400px;" class="col-xs-4 col-sm-4" ></div>
							
							<script type="text/javascript">
						        // 基于准备好的dom，初始化echarts实例
						        var myChart = echarts.init(document.getElementById('main'));
						
						        var json = [  
						                    {value:65,name:'正常'},  
						                    {value:20,name:'警告'},  
						                    {value:5,name:'危险'},  
						                    {value:10,name:'未知'}, 
						                    ];  
						        var option = {  
						                title : {  
						                    text: 'CPU',  
						                    subtext: '使用情况',  
						                    x:'center'  
						                },  
						                tooltip : {  
						                    trigger: 'item',  
						                    formatter: "{a} <br/>{b} : {c} %"  
						                },  
						                legend: {  
						                    orient : 'vertical',  
						                    x : 'left',  
						                    data:['正常','警告','危险','未知']  
						                }, 
						                color:['green', '#f5b031','red','blueviolet'],
						                toolbox: {  
						                    show : false,  
						                    feature : {  
						                        mark : {show: true},  
						                        dataView : {show: true, readOnly: false},  
						                        magicType : {  
						                            show: true,   
						                            type: ['pie', 'funnel'],  
						                            option: {  
						                                funnel: {  
						                                    x: '25%',  
						                                    width: '50%',  
						                                    funnelAlign: 'left',  
						                                    max: 1548  
						                                }  
						                            }  
						                        },  
						                        restore : {show: true},  
						                        saveAsImage : {show: true}  
						                    }  
						                },  
						                calculable : true,  
						                series : [  
						                    {  
						                        name:'CPU',  
						                        type:'pie',  
						                        radius : '55%',//饼图的半径大小  
						                        center: ['50%', '60%'],//饼图的位置  
						                        data:json  
						                    }  
						                ]  
						            };        

						        // 使用刚指定的配置项和数据显示图表。
						        myChart.setOption(option);
						        
						        
						        var myChart2 = echarts.init(document.getElementById('main2'));
						        var json2 = [  
						                    {value:35,name:'正常'},  
						                    {value:45,name:'警告'},  
						                    {value:15,name:'危险'},  
						                    {value:5,name:'未知'}, 
						                    ];  
						        var option2 = {  
						                title : {  
						                    text: '内存',  
						                    subtext: '使用情况',  
						                    x:'center'  
						                },  
						                tooltip : {  
						                    trigger: 'item',  
						                    formatter: "{a} <br/>{b} : {c} %"  
						                },  
						                legend: {  
						                    orient : 'vertical',  
						                    x : 'left',  
						                    data:['正常','警告','危险','未知']  
						                },  
						                color:['green', '#f5b031','red','blueviolet'],
						                toolbox: {  
						                    show : false,  
						                    feature : {  
						                        mark : {show: true},  
						                        dataView : {show: true, readOnly: false},  
						                        magicType : {  
						                            show: true,   
						                            type: ['pie', 'funnel'],  
						                            option: {  
						                                funnel: {  
						                                    x: '25%',  
						                                    width: '50%',  
						                                    funnelAlign: 'left',  
						                                    max: 1548  
						                                }  
						                            }  
						                        },  
						                        restore : {show: true},  
						                        saveAsImage : {show: true}  
						                    }  
						                },  
						                calculable : true,  
						                series : [  
						                    {  
						                        name:'内存',  
						                        type:'pie',  
						                        radius : '55%',//饼图的半径大小  
						                        center: ['50%', '60%'],//饼图的位置  
						                        data:json2  
						                    }  
						                ]  
						            };        

						        // 使用刚指定的配置项和数据显示图表。
						        myChart2.setOption(option2);
						        
						        
						        var myChart3 = echarts.init(document.getElementById('main3'));
						        var json3 = [  
						                    {value:15,name:'正常'},  
						                    {value:15,name:'警告'},  
						                    {value:5,name:'危险'},  
						                    {value:65,name:'未知'}, 
						                    ];  
						        var option3 = {  
						                title : {  
						                    text: '磁盘',  
						                    subtext: '使用情况',  
						                    x:'center'  
						                },  
						                tooltip : {  
						                    trigger: 'item',  
						                    formatter: "{a} <br/>{b} : {c} %"  
						                },  
						                legend: {  
						                    orient : 'vertical',  
						                    x : 'left',  
						                    data:['正常','警告','危险','未知']  
						                },  
						                color:['green', '#f5b031','red','blueviolet'],
						                toolbox: {  
						                    show : false,  
						                    feature : {  
						                        mark : {show: true},  
						                        dataView : {show: true, readOnly: false},  
						                        magicType : {  
						                            show: true,   
						                            type: ['pie', 'funnel'],  
						                            option: {  
						                                funnel: {  
						                                    x: '25%',  
						                                    width: '50%',  
						                                    funnelAlign: 'left',  
						                                    max: 1548  
						                                }  
						                            }  
						                        },  
						                        restore : {show: true},  
						                        saveAsImage : {show: true}  
						                    }  
						                },  
						                calculable : true,  
						                series : [  
						                    {  
						                        name:'磁盘',  
						                        type:'pie',  
						                        radius : '55%',//饼图的半径大小  
						                        center: ['50%', '60%'],//饼图的位置  
						                        data:json3  
						                    }  
						                ]  
						            };        

						        // 使用刚指定的配置项和数据显示图表。
						        myChart3.setOption(option3);
						    </script>
							
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>