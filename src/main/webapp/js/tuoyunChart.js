function getTuoyunChart(id,title,percent,style) {
    if(style=="meterStyle1"){
        var width=$("#"+id).width();
        $("#"+id).append("<canvas  id='tuoyun_canvas_"+id+"' class='meterStyle1' width='"+width+"' height='"+width+"'></canvas>");
        toCanvasStyle1("tuoyun_canvas_"+id,percent,title);
    }
}
function getTuoyunPercentChart(id,title,littleNum,middleNum,heightNum,stopNum,title3,title4,title5,title6) {
    littleNum=parseInt(littleNum);
    middleNum=parseInt(middleNum);
    heightNum=parseInt(heightNum);
    stopNum=parseInt(stopNum);
    var title1=littleNum+middleNum+heightNum+stopNum+"台";
    var title2=title;
    var width=$("#"+id).width();
    $("#"+id).append("<canvas  id='tuoyun_canvas_"+id+"' class='meterStyle1' width='"+width+"' height='"+(width+60)+"'></canvas>");
    toCanvasStyle2("tuoyun_canvas_"+id,title1,title2,littleNum,middleNum,heightNum,stopNum,title3,title4,title5,title6);
    //console.log(percent1+" "+percent2+" "+percent3+" "+percent4+" ")
}
function toCanvasStyle1(id ,progress,title){
    //canvas进度条
    var canvas = document.getElementById(id),
        ctx = canvas.getContext("2d"),
        percent = progress,  //最终百分比
        circleX = canvas.width / 2,  //中心x坐标
        circleY = canvas.height / 2,  //中心y坐标
        radius = canvas.width /3, //圆环半径
        lineWidth = 20,  //圆形线条的宽度
        fontSize = 14, //字体大小
        fontColor='#96c849';

    //console.log(circleX+" "+circleY+" "+radius)
    //两端圆点
    function smallcircle1(cx, cy, r) {
        ctx.beginPath();
        //ctx.moveTo(cx + r, cy);
        ctx.lineWidth = 1;
        ctx.fillStyle = '#06a8f3';
        ctx.arc(cx, cy, r,0,Math.PI*2);
        ctx.fill();
    }
    function smallcircle2(cx, cy, r) {
        ctx.beginPath();
        //ctx.moveTo(cx + r, cy);
        ctx.lineWidth = 1;
        ctx.fillStyle = '#00f8bb';
        ctx.arc(cx, cy, r,0,Math.PI*2);
        ctx.fill();
    }
    function smallcircle3(cx, cy, r) {
        ctx.beginPath();
        //ctx.moveTo(cx + r, cy);
        ctx.lineWidth = 1;
        ctx.fillStyle = '#000000';
        ctx.arc(cx, cy, r,0,Math.PI*2);
        ctx.fill();
    }
    function pointer(process){

        ctx.save();
        ctx.translate(circleX,circleY);
        ctx.rotate(Math.PI + process/100 * (Math.PI));
        ctx.lineWidth=1;
        ctx.beginPath();
        ctx.strokeStyle = '#000000';
        ctx.moveTo(0,lineWidth/6);
        ctx.lineTo(radius+lineWidth/2,0);
        ctx.lineTo(0,-lineWidth/6);
        ctx.lineTo(0,lineWidth/6);
        ctx.fillStyle = '#000000';
        ctx.fill();

        ctx.stroke();
        ctx.closePath();
        ctx.restore();
    }

    //画圆
    function circle(cx, cy, r) {
        ctx.beginPath();
        //ctx.moveTo(cx + r, cy);
        ctx.lineWidth = lineWidth;
        ctx.strokeStyle = '#eee';
        //ctx.arc(cx, cy, r, Math.PI*2/3, Math.PI * 1/3);
        ctx.arc(cx, cy, r, Math.PI,Math.PI*2);
        ctx.stroke();
    }

    //画弧线
    function sector(cx, cy, r, endAngle) {
        ctx.beginPath();
        //ctx.moveTo(cx, cy + r); // 从圆形底部开始画
        ctx.lineWidth = lineWidth;

        // 渐变色 - 可自定义
        var linGrad = ctx.createLinearGradient(
            circleX-radius-lineWidth, circleY, circleX+radius+lineWidth, circleY
        );
        linGrad.addColorStop(0.0, '#96c849');
        linGrad.addColorStop(0.35, '#e6e143');
        linGrad.addColorStop(0.5, '#f9d840');
        linGrad.addColorStop(0.65, '#f9bd3e');
        linGrad.addColorStop(1, '#f5553a');
        ctx.strokeStyle = linGrad;

        //圆弧两端的样式
        ctx.lineCap = 'butt';

        //圆弧
        ctx.arc(
            cx, cy, r,
            Math.PI,
            Math.PI + endAngle/100 * (Math.PI),
            false
        );
        ctx.stroke();
    }

    // 刷新
    function loading() {
        if (process >= percent) {
            clearInterval(circleLoading);
        }

        if(process>=35){
            fontColor='#e6e143';
        }
        if(process>=50){
            fontColor='#f9d840';
        }
        if(process>=65){
            fontColor='#f9bd3e';
        }
        if(process>=90){
            fontColor='#f5553a';
        }
        //清除canvas内容
        ctx.clearRect(0, 0, circleX * 2, circleY * 2);

        //中间的字
        ctx.font = fontSize + 'px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = fontColor;
        ctx.fillText(parseFloat(process).toFixed(0) + '%', circleX, circleY+25);

        ctx.font = fontSize + 'px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = '#000000';
        ctx.fillText(title, circleX, circleY+50);

        //圆形
        circle(circleX, circleY, radius);

        //圆弧
        sector(circleX, circleY, radius,process);
        //指针
        smallcircle3(circleX, circleY,lineWidth/6);
        pointer(process);
        //两端圆点
        //smallcircle1(150+Math.cos(2*Math.PI/360*120)*100, 150+Math.sin(2*Math.PI/360*120)*100, 5);
        //smallcircle2(150+Math.cos(2*Math.PI/360*(120+process*3))*100, 150+Math.sin(2*Math.PI/360*(120+process*3))*100, 5);
        //控制结束时动画的速度
        if (process / percent > 0.90) {
            process += 0.30;
        } else if (process / percent > 0.80) {
            process += 0.55;
        } else if (process / percent > 0.70) {
            process += 0.75;
        } else {
            process += 1.0;
        }
    }

    var process = 0.0;  //进度
    var circleLoading = window.setInterval(function () {
        loading();
    }, 20);

    //结尾帧
    function draw() {
        process=percent;//定格结尾

        if(process>=35){
            fontColor='#e6e143';
        }
        if(process>=50){
            fontColor='#f9d840';
        }
        if(process>=65){
            fontColor='#f9bd3e';
        }
        if(process>=90){
            fontColor='#f5553a';
        }
        //清除canvas内容
        ctx.clearRect(0, 0, circleX * 2, circleY * 2);

        //中间的字
        ctx.font = fontSize + 'px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = fontColor;
        ctx.fillText(parseFloat(process).toFixed(0) + '%', circleX, circleY+25);

        ctx.font = fontSize + 'px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = '#000000';
        ctx.fillText(title, circleX, circleY+50);

        //圆形
        circle(circleX, circleY, radius);

        //圆弧
        sector(circleX, circleY, radius,process);
        //指针
        smallcircle3(circleX, circleY,lineWidth/6);
        pointer(process);

    }
    // 监听鼠标事件
    canvas.onmousemove = function (e) {
        if (process >= percent) {
            // 得到鼠标的坐标
            var x = e.pageX - canvas.getBoundingClientRect().left,
                y = e.pageY - $(document).scrollTop() - canvas.getBoundingClientRect().top,
                top = e.pageY+5,
                left = e.pageX+5;
            //指针
            smallcircle3(circleX, circleY,lineWidth/6);
            pointer(process); //画出想要响应事件的图形区域
            if (ctx.isPointInPath(x, y)) {
                //判断鼠标是否在响应位置中
                //响应事件
                showTips(title +" "+ parseFloat(process).toFixed(0) + '%');
                followMouse(top,left);
            } else {
                hideTips();
            }
            draw();

        }
    }
}

function toCanvasStyle2(id ,title1,title2,littleNum,middleNum,heightNum,stopNum,title3,title4,title5,title6){
    littleNum=parseInt(littleNum);
    middleNum=parseInt(middleNum);
    heightNum=parseInt(heightNum);
    stopNum=parseInt(stopNum);
    var percent1=parseInt((middleNum/(littleNum+middleNum+heightNum+stopNum)*100).toFixed());
    var percent2=parseInt((heightNum/(littleNum+middleNum+heightNum+stopNum)*100).toFixed());
    var percent3=parseInt((littleNum/(littleNum+middleNum+heightNum+stopNum)*100).toFixed());
    var percent4=parseInt((stopNum/(littleNum+middleNum+heightNum+stopNum)*100).toFixed());
    //canvas进度条
    var canvas = document.getElementById(id),
        ctx = canvas.getContext("2d"),
        circleX = canvas.width / 2,  //中心x坐标
        circleY = (canvas.width / 2)+35,  //中心y坐标
        radius = canvas.width /4, //圆环半径
        lineWidth = 25,  //圆形线条的宽度
        fontSize = 20, //字体大小
        space=1;//间隔
    function smallcircle(cx, cy, r,color) {
        ctx.beginPath();
        //ctx.moveTo(cx + r, cy);
        ctx.lineWidth = 1;
        ctx.fillStyle = color;
        ctx.arc(cx, cy, r,0,Math.PI*2);
        ctx.fill();
    }
    function messageList(){
        var width=(radius*2+lineWidth);
        var msp=(canvas.width-width)/2;
        var top=canvas.width/8;
        smallcircle(msp+lineWidth/8, top, lineWidth/8,'#3ba1df');
        smallcircle(width/2+msp+lineWidth/8, top, lineWidth/8,'#f77275');
        smallcircle(msp+lineWidth/8, top*2, lineWidth/8,'#ffc300');

        ctx.font = '12px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = '#000000';
        ctx.fillText(littleNum+"台", msp+25+lineWidth/8, top);
        ctx.fillText(middleNum+"台", msp+25+lineWidth/8, top*2);
        ctx.fillText(heightNum+"台", width/2+msp+25+lineWidth/8, top);

        if(stopNum!=0){
            smallcircle(width/2+msp+lineWidth/8, top*2, lineWidth/8,'#c6c6c6');
            ctx.fillStyle = '#000000';
            ctx.fillText(stopNum+"台", width/2+msp+25+lineWidth/8, top*2);
        }

    }

    //画圆
    function circle(cx, cy, r ,color) {
        ctx.beginPath();
        //ctx.moveTo(cx + r, cy);
        ctx.lineWidth = lineWidth;
        ctx.strokeStyle = color;
        //ctx.arc(cx, cy, r, Math.PI*2/3, Math.PI * 1/3);
        ctx.arc(cx, cy, r, 0,Math.PI*2);
        ctx.stroke();
    }

    //画弧线
    function sector(cx, cy, r, endAngle) {
        ctx.beginPath();
        //ctx.moveTo(cx, cy + r); // 从圆形底部开始画
        ctx.lineWidth = lineWidth;
        //圆弧两端的样式
        ctx.lineCap = 'butt';
        ctx.strokeStyle = '#ffc300';
        var startAngle=0;
        if (process >= percent1) {//进入percent2
            ctx.strokeStyle = '#ffc300';
            startAngle=percent1/100*Math.PI*2;
            ctx.arc(cx, cy, r,0,(percent1-space)/100*Math.PI*2,false);
            ctx.stroke();
            ctx.beginPath();
            ctx.strokeStyle = '#ffffff';
            ctx.arc(cx, cy, r,(percent1-space)/100*Math.PI*2,startAngle,false);
            ctx.stroke();
            if(process < percent1+percent2) {
                ctx.beginPath();
                ctx.strokeStyle = '#f77275';
                ctx.lineWidth = 30;
                r=r+2.5;
                endAngle = endAngle - percent1;
            }
        }
        if(process >= percent1+percent2) {//进入percent3
            ctx.beginPath();
            ctx.strokeStyle = '#f77275';
            ctx.lineWidth = 30;
            startAngle=(percent1+percent2)/100*Math.PI*2;
            ctx.arc(cx, cy, r+2.5,percent1/100*Math.PI*2,(percent1+percent2-space)/100*Math.PI*2,false);
            ctx.stroke();
            ctx.beginPath();
            ctx.strokeStyle = '#ffffff';
            ctx.arc(cx, cy, r,(percent1+percent2-space)/100*Math.PI*2,startAngle,false);
            ctx.stroke();
            if(process < percent1+percent2+percent3) {
                ctx.beginPath();
                ctx.strokeStyle = '#3ba1df';
                ctx.lineWidth = lineWidth;
                endAngle = endAngle - percent1-percent2;
            }
        }
        if(process >= percent1+percent2+percent3) {//进入percent4
            ctx.beginPath();
            ctx.strokeStyle = '#3ba1df';
            ctx.lineWidth = lineWidth;
            startAngle=(percent1+percent2+percent3)/100*Math.PI*2;
            ctx.arc(cx, cy, r,(percent1+percent2)/100*Math.PI*2,(percent1+percent2+percent3-space)/100*Math.PI*2,false);
            ctx.stroke();
            ctx.beginPath();
            ctx.strokeStyle = '#ffffff';
            ctx.arc(cx, cy, r,(percent1+percent2+percent3-space)/100*Math.PI*2,startAngle,false);
            ctx.stroke();
            if(process <= 100) {
                ctx.beginPath();
                ctx.strokeStyle = '#c6c6c6';
                ctx.lineWidth = lineWidth;
                endAngle = endAngle - percent1-percent2-percent3;
            }
        }

        ctx.arc(
            cx,
            cy,
            r,
            startAngle,
            startAngle+endAngle/100 * (Math.PI*2),
            false
        );



        ctx.stroke();
    }

    // 刷新
    function loading() {
        //console.log(percent1+percent2+percent3+percent4)
        if (process >= percent1+percent2+percent3+percent4) {
            clearInterval(circleLoading);
        }

        //清除canvas内容
        ctx.clearRect(0, 0, circleX * 2, circleY * 2);

        //中间的字
        ctx.font = '16px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = '#000000';
        ctx.fillText(title1, circleX, circleY);

        ctx.font = '14px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = '#000000';
        ctx.fillText(title2, circleX, circleY+radius+lineWidth/2+30);

        //圆形
        messageList();

        //圆弧(比例1)
        sector(circleX, circleY, radius,process);

        //两端圆点
        //smallcircle1(150+Math.cos(2*Math.PI/360*120)*100, 150+Math.sin(2*Math.PI/360*120)*100, 5);
        //smallcircle2(150+Math.cos(2*Math.PI/360*(120+process*3))*100, 150+Math.sin(2*Math.PI/360*(120+process*3))*100, 5);
        //控制结束时动画的速度
        process += 1.0;

    }

    var process = 0.0;  //进度
    var circleLoading = window.setInterval(function () {
        loading();
    }, 20);

    // 结尾帧
    function draw() {
        //console.log(percent1+percent2+percent3+percent4)
        process=percent1+percent2+percent3+percent4;

        //清除canvas内容
        ctx.clearRect(0, 0, circleX * 2, circleY * 2);

        //中间的字
        ctx.font = '16px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = '#000000';
        ctx.fillText(title1, circleX, circleY);

        ctx.font = '14px April';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillStyle = '#000000';
        ctx.fillText(title2, circleX, circleY+radius+lineWidth/2+30);

        //圆形
        messageList();

        //圆弧(比例1)
        sector(circleX, circleY, radius,process);

    }
    // 监听鼠标事件
    canvas.onmousemove = function (e) {
        if (process >= percent1+percent2+percent3+percent4) {
            // 得到鼠标的坐标
            var x = e.pageX - canvas.getBoundingClientRect().left,
                y = e.pageY - $(document).scrollTop() - canvas.getBoundingClientRect().top,
                top = e.pageY+5,
                left = e.pageX+5,
                isPointInPath=0;
            ctx.closePath();
            //画出想要响应事件的图形区域
            //percent1区域
            ctx.beginPath();
            ctx.arc(circleX, circleY, radius,0,(percent1-space)/100*Math.PI*2,false);
            ctx.closePath();
            if (ctx.isPointInPath(x, y)) {
                //判断鼠标是否在响应位置中
                isPointInPath=1;
            }
            ctx.beginPath();
            //percent2区域
            ctx.arc(circleX, circleY, radius+2.5,percent1/100*Math.PI*2,(percent1+percent2-space)/100*Math.PI*2,false);
            ctx.closePath();
            if (ctx.isPointInPath(x, y)) {
                //判断鼠标是否在响应位置中
                isPointInPath=2;
            }
            ctx.beginPath();
            //percent3区域
            ctx.arc(circleX, circleY, radius,(percent1+percent2)/100*Math.PI*2,(percent1+percent2+percent3-space)/100*Math.PI*2,false);
            ctx.closePath();
            if (ctx.isPointInPath(x, y)) {
                //判断鼠标是否在响应位置中
                isPointInPath=3;
            }
            ctx.beginPath();
            //percent4区域
            ctx.arc(circleX, circleY, radius,(percent1+percent2+percent3)/100*Math.PI*2,Math.PI*2,false);
            ctx.closePath();
            if (ctx.isPointInPath(x, y)) {
                //判断鼠标是否在响应位置中
                isPointInPath=4;
            }

            if (isPointInPath==1) {
                //响应事件
                showTips(middleNum +"台("+title4+")"+percent1+"%");
                followMouse(top,left);
            } else if (isPointInPath==2) {
                //响应事件
                showTips(heightNum+"台("+title5+")"+percent2+"%");
                followMouse(top,left);
            } else if (isPointInPath==3) {
                //响应事件
                showTips(littleNum+"台("+title3+")"+percent3+"%");
                followMouse(top,left);
            } else if (isPointInPath==4) {
                //响应事件
                showTips(stopNum+"台("+title6+")"+percent4+"%");
                followMouse(top,left);
            } else {
                hideTips();
            }

            draw();

        }
    }

}
function showTips(title) {
    $('#tip-msg').html(title);
    $('#tip').show();
}
function hideTips(){
    $('#tip').hide();
}
function followMouse(top,left){
    $('#tip').css({
        'top' : top + 'px',
        'left': left+ 'px'
    });
}
/*$(function () {
    $('canvas').mouseout(function(e){
        $('#tip').hide();
    })
})*/
