﻿<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
    <%@ include file="../../system/index/top.jsp" %>
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
    <link rel="stylesheet" href="css/newSkin.css">
</head>
<body class="no-skin new-page-list">
<div class="main-container" id="main-container">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <form action="snapshot/list.do" method="post" name="Form" id="Form">
                            <input type="hidden" name="TYPE" value="${pd.TYPE}"/>
                            <c:if test="${QX == 1}">
                                <table style="margin-top:5px;">
                                    <tr>
                                        <td style="padding-left:6px;"><a class="btn btn-success btn-sm new-ctl-style bt-green">创建</a></td>
                                        <td style="padding-left:6px;"><a class="btn btn-warning btn-sm new-ctl-style bt-blue">回滚</a></td>
                                        <td style="padding-left:6px;"><a class="btn btn-danger btn-sm new-ctl-style bt-red">删除</a></td>
                                        <td style="padding-left:6px;">
                                            <div class="nav-search">
										<span class="input-icon">
											<input type="text" class="nav-search-input new-ctl-style"
                                                   id="nav-search-input" autocomplete="off" name="keywords"
                                                   value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </c:if>
                            <table id="simple-table" class="table table-striped table-bordered table-hover"
                                   style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center" style="width:35px;">
                                        <label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox"/><span
                                                class="lbl"></span></label>
                                    </th>
                                    <th class="center">服务器名称</th>
                                    <th class="center">IP</th>
                                    <th class="center">允许保存手动快照版本</th>
                                    <th class="center">已保存手动快照版本</th>
                                    <th class="center">允许保存自动快照版本</th>
                                    <th class="center">已保存自动快照版本</th>
                                    <th class="center">创建人</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr>
                                        <td class='center'>
                                            <label class="pos-rel">
                                                <input type='checkbox' name='ids' value="${var.id}" class="ace"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class='center'>${var.name}</td>
                                        <td class='center'>${var.ip}</td>
                                        <td class='center'>5</td>
                                        <td class='center'>1</td>
                                        <td class='center'>5</td>
                                        <td class='center'>0</td>
                                        <td class='center'>tc1</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="page-header position-relative">
                                <table style="width:100%;">
                                    <tr>
                                        <td style="vertical-align:top;">
                                            <div class="pagination"
                                                 style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>

</div>

<%@ include file="../../system/index/foot.jsp" %>

<script src="static/ace/js/bootbox.js"></script>
<script src="static/ace/js/ace/ace.js"></script>
<script src="static/ace/js/chosen.jquery.js"></script>
<script src="static/js/jquery.tips.js"></script>
<script src="static/js/myjs/head.js"></script>
<script type="text/javascript">
    $(top.hangge());//关闭加载状态
    //检索
    function tosearch() {
        top.jzts();
        $("#Form").submit();
    }

    $(function () {

        //日期框
        $('.date-picker').datepicker({
            autoclose: true,
            todayHighlight: true
        });

        //下拉框
        if (!ace.vars['touch']) {
            $('.chosen-select').chosen({allow_single_deselect: true});
            $(window)
                .off('resize.chosen')
                .on('resize.chosen', function () {
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                }).trigger('resize.chosen');
            $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                if (event_name != 'sidebar_collapsed') return;
                $('.chosen-select').each(function () {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                });
            });
            $('#chosen-multiple-style .btn').on('click', function (e) {
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if (which == 2) $('#form-field-select-4').addClass('tag-input-style');
                else $('#form-field-select-4').removeClass('tag-input-style');
            });
        }


        //复选框全选控制
        var active_class = 'active';
        $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function () {
            var th_checked = this.checked;//checkbox inside "TH" table header
            $(this).closest('table').find('tbody > tr').each(function () {
                var row = this;
                if (th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
            });
        });
    });

    //发站内信
    function sendFhsms(username) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "站内信";
        diag.URL = '<%=basePath%>fhsms/goAdd.do?username=' + username;
        diag.Width = 660;
        diag.Height = 444;
        diag.CancelEvent = function () { //关闭事件
            top.jzts();
            setTimeout("self.location=self.location", 100);
            diag.close();
        };
        diag.show();
    }

    //删除
    function del(ztid, STATUS, type, Id, SANME_ID) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (type == "1" && STATUS == '2' && $("#" + ztid).html() == '<span class="label label-important arrowed-in">未读</span>') {
                top.readFhsms();//读取站内信时减少未读总数
                <!-- readFhsms()函数在 WebRoot\static\js\myjs\head.js中 -->
            }
            if (result) {
                top.jzts();
                var url = "<%=basePath%>fhsms/delete.do?FHSMS_ID=" + Id + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    nextPage(${page.currentPage});
                });
            }
        });
    }

    //查看信件
    function viewx(ztid, STATUS, type, Id, SANME_ID) {
        if (type == "1" && STATUS == '2' && $("#" + ztid).html() == '<span class="label label-important arrowed-in">未读</span>') {
            $("#" + ztid).html('<span class="label label-success arrowed">已读</span>');
            top.readFhsms();//读取站内信时减少未读总数
            <!-- readFhsms()函数在 WebRoot\static\js\myjs\head.js中 -->
        }
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "站内信";
        diag.URL = '<%=basePath%>fhsms/goView.do?FHSMS_ID=' + Id + '&TYPE=' + type + '&SANME_ID=' + SANME_ID + '&STATUS=' + STATUS;
        diag.Width = 600;
        diag.Height = 460;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    //批量操作
    function makeAll(msg) {
        bootbox.confirm(msg, function (result) {
            if (result) {
                var str = '';
                var username = '';
                for (var i = 0; i < document.getElementsByName('ids').length; i++) {
                    if (document.getElementsByName('ids')[i].checked) {
                        if (str == '') str += document.getElementsByName('ids')[i].value;
                        else str += ',' + document.getElementsByName('ids')[i].value;

                        if (username == '') username += document.getElementsByName('ids')[i].id;
                        else username += ';' + document.getElementsByName('ids')[i].id;
                    }
                }
                if (str == '') {
                    bootbox.dialog({
                        message: "<span class='bigger-110'>您没有选择任何内容!</span>",
                        buttons:
                            {"button": {"label": "确定", "className": "btn-sm btn-success"}}
                    });
                    $("#zcheckbox").tips({
                        side: 1,
                        msg: '点这里全选',
                        bg: '#AE81FF',
                        time: 8
                    });
                    return;
                } else {
                    if (msg == '确定要删除选中的数据吗?') {
                        top.jzts();
                        $.ajax({
                            type: "POST",
                            url: '<%=basePath%>fhsms/deleteAll.do?tm=' + new Date().getTime(),
                            data: {DATA_IDS: str},
                            dataType: 'json',
                            //beforeSend: validateData,
                            cache: false,
                            success: function (data) {
                                $.each(data.list, function (i, list) {
                                    nextPage(${page.currentPage});//刷新当前页面
                                    top.getFhsmsCount();//更新未读站内信
                                });
                            }
                        });
                    } else if (msg == '确定要给选中的用户发送站内信吗?') {
                        sendFhsms(username);
                    }
                }
            }
        });
    };

    //查看用户
    function viewUser(USERNAME) {
        if ('admin' == USERNAME) {
            bootbox.dialog({
                message: "<span class='bigger-110'>不能查看admin用户!</span>",
                buttons:
                    {"button": {"label": "确定", "className": "btn-sm btn-success"}}
            });
            return;
        }
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "资料";
        diag.URL = '<%=basePath%>user/view.do?USERNAME=' + USERNAME;
        diag.Width = 469;
        diag.Height = 380;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }
</script>
</body>
</html>