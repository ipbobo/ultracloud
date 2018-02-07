var locat = (window.location+'').split('/'); 
$(function(){if('main'== locat[3]){locat =  locat[0]+'//'+locat[2];}else{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};});

var fmid = "fhindex";	//菜单点中状态
var mid = "fhindex";	//菜单点中状态
var systemnoticeCount = 0;		//站内信总数
var USER_ID;			//用户ID
var user = "FH";		//用于即时通讯（ 当前登录用户）
var TFHsmsSound = '1';	//站内信提示音效
var websocket;			//websocket对象
var wimadress="";		//即时聊天服务器IP和端口
var oladress="";		//在线管理和站内信服务器IP和端口

function siMenu(id,fid,MENU_NAME,MENU_URL){
	if(id != mid){
		$("#"+mid).removeClass("active");
		$("#"+id).addClass("active");
		mid = id;
	}
	
	if(fid != fmid){
		$("#"+fmid).removeClass("active open");
		$("#"+fid).addClass("active open");
		fmid = fid;
	}
	
	top.mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
	if(MENU_URL != "druid/index.html"){
		jzts();
	}
}

$(function(){
	getHeadMsg();	//初始页面最顶部信息
});

//初始页面信息
function getHeadMsg(){
	$.ajax({
		type: "POST",
		url: locat+'/head/getList.do?tm='+new Date().getTime(),
    	data: encodeURI(""),
		dataType:'json',
		//beforeSend: validateData,
		cache: false,
		success: function(data){
			 $.each(data.list, function(i, list){
				 $("#user_info").html('<small>欢迎您</small> '+list.NAME+'');//登陆者资料
				 user = list.USERNAME;
				 USER_ID = list.USER_ID;		//用户ID
				 if(list.USERNAME != 'admin'){
					 $("#systemset").hide();	//隐藏系统设置
				 }
			 });
			 updateUserPhoto(data.userPhoto);			//用户头像
			 systemnoticeCount = Number(data.systemnoticeCount);
			 $("#systemnoticeCount").html(Number(systemnoticeCount));	//站内信未读总数
			 TFHsmsSound = data.FHsmsSound;				//站内信提示音效
			 wimadress = data.wimadress;				//即时聊天服务器IP和端口
			 oladress = data.oladress;					//在线管理和站内信服务器IP和端口
			 online();									//连接在线
		}
	});
}

//获取系统公告未读总数(在系统公告删除未读状态时调用此函数更新未读数)
function getSystemNoticeCount(){
	$.ajax({
		type: "POST",
		url: locat+'/head/getSystemNoticeCount.do?tm='+new Date().getTime(),
    	data: encodeURI(""),
		dataType:'json',
		cache: false,
		success: function(data){
			 systemnoticeCount = Number(data.systemnoticeCount);
			 $("#systemnoticeCount").html(Number(systemnoticeCount));	//系统公告未读总数
		}
	});
}

//加入在线列表
function online(){
	if (window.WebSocket) {
		websocket = new WebSocket(encodeURI('ws://'+oladress)); //oladress在main.jsp页面定义
		websocket.onopen = function() {
			//连接成功
			websocket.send('[join]'+user);
		};
		websocket.onerror = function() {
			//连接失败
		};
		websocket.onclose = function() {
			//连接断开
		};
		//消息接收
		websocket.onmessage = function(message) {
			var message = JSON.parse(message.data);
			if(message.type == 'goOut'){
				$("body").html("");
				goOut("1");
			}else if(message.type == 'thegoout'){
				$("body").html("");
				goOut("2");
			}else if(message.type == 'senFhsms'){
				fhsmsCount = Number(fhsmsCount)+1;
				$("#fhsmsCount").html(Number(fhsmsCount));
				$("#fhsmsobj").html('<audio style="display: none;" id="fhsmstsy" src="static/sound/'+TFHsmsSound+'.mp3" autoplay controls></audio>');
				$("#fhsmstss").tips({
					side:3,
		            msg:'有新消息',
		            bg:'#AE81FF',
		            time:30
		        });
			}
		};
	}
}

//下线
function goOut(msg){
	window.location.href=locat+"/logout.do?msg="+msg;
}

//去通知收信人有站内信接收
function fhsmsmsg(USERNAME){
	var arrUSERNAME = USERNAME.split(';');
	for(var i=0;i<arrUSERNAME.length;i++){
		websocket.send('[fhsms]'+arrUSERNAME[i]);//发送通知
	}
}

//读取站内信时减少未读总数
function readFhsms(){
	systemnoticeCount = Number(systemnoticeCount)-1;
	$("#systemnoticeCount").html(Number(systemnoticeCount) <= 0 ?'0':systemnoticeCount);
}

//修改头像
function editPhoto(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="修改头像";
	 diag.URL = locat+'/head/editPhoto.do';
	 diag.Width = 650;
	 diag.Height = 530;
	 diag. ShowMaxButton = true;	//最大化按钮
     diag.ShowMinButton = true;		//最小化按钮
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//修改个人资料
function editUserH(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="用户设置";
	 diag.URL = locat+'/user/goEditMyU.do';
	 diag.Width = 469;
	 diag.Height = 465;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}


//帮助
function help(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="帮助";
	 diag.URL = locat+'/user/goHelp.do';
	 diag.Width = 469;
	 diag.Height = 465;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//系统设置
function editSystem(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="系统设置";
	 diag.URL = locat+'/sysconfig/updatePre.do';
	 diag.Width = 600;
	 diag.Height = 526;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//系统设置
function editSys(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="系统设置";
	 diag.URL = locat+'/head/goSystem.do';
	 diag.Width = 600;
	 diag.Height = 526;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}

//系统公告
function goSystemNoticWindows(){
	 jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="系统公告";
	 diag.URL = locat+'/systemnotice/listSystemNotice.do?STATUS=0';
	 diag.Width = 800;
	 diag.Height = 500;
	 diag.CancelEvent = function(){ //关闭事件
		 getSystemNoticeCount();
		 diag.close();
		 
	 };
	 diag.show();
}

//切换菜单
function changeMenus(){
	window.location.href=locat+'/main/yes';
}

//清除加载进度
function hangge(){
	$("#jzts").hide();
}

//显示加载进度
function jzts(){
	$("#jzts").show();
}

//刷新用户头像
function updateUserPhoto(value){
	$("#userPhoto").attr("src",value);//用户头像
}

function logout(){
	window.parent.location.href=locat + '/logout.do';
}
