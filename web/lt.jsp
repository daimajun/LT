<%@ page import="cn.youngfish.lt.model.User" %>
<%@ page import="cn.youngfish.lt.model.httpmodel.UserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    /*当前登录用户*/
    User user = (User) request.getSession().getAttribute("USER_INFO");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>案例</title>

    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/LT.css" rel="stylesheet">
    <script src="js/jquery-3.3.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="wangEdite/wangEditor.min.js"></script>
    <script src="js/LT.js"></script>
    <title>登录聊天室</title>
    <style type="text/css">

    </style>
    <script type="text/javascript">
        //当前用户权限
        var permissions = '<%=user.getPermissions()%>';
        //当前用户
        var nowUserName = '<%= user.getLoginName()%>';
        //当前用户的id
        var nowUserId = '<%= user.getId()%>';
        //客服id
        var sendToUserId = '<%= UserInfo.USER_KFID%>';
        //客服名称
        var sendToUserName = '<%= UserInfo.KF_USER_NAME%>';
        //聊天室id
        var chatRoomId = '<%= UserInfo.CHAT_ROOM_ID%>';

        var url = "ws://" + window.location.host + "/chat/";
        //wangEditer全局对象
        var editer;
        $(function () {

            initWangEditer();

            //判断当前用户是否为客服
            if (permissions !== '-1') {
                openLtWebSocket(nowUserId, chatRoomId);
                $("#sendToUserName").text(sendToUserName);
                $("#kfName").text(sendToUserName);
            } else {
                getMyUser();
                //定时刷新获得信息
                //setInterval(1000 * 30, refreshMessageNumber);
                refreshMessageNumber();
            }

        });

        //初始化wangediter插件
        function initWangEditer() {
            var E = window.wangEditor;
            editer = new E("#editerToopBar", "#editerContent");
            editer.customConfig.menus = [
                'head',  // 标题
                'bold',  // 粗体
                'fontSize',  // 字号
                'fontName',  // 字体
                'italic',  // 斜体
                'foreColor',  // 文字颜色
                'link',  // 插入链接
                'emoticon',  // 表情
            ];
            editer.create();
        }

        //打开与某个用户的会话
        function openLtWebSocket(presentUserId, presentChatRoomId) {
            url = "ws://" + window.location.host + "/chat/";

            if (presentUserId.length <= 0) {
                alert("加载自己信息出错！刷新页面重试！");
                return;
            }
            if (presentChatRoomId.length <= 0) {
                alert("加载聊天室出错！刷新页面重试！");
                return;
            }

            url += presentUserId + "," + presentChatRoomId;
            //初始化websocket插件
            lt(url, 'userStatus', 'chatShowBox');
        }

        //发送消息
        function sendMessage() {
            var html = editer.txt.html();
            editer.txt.html('');
            sendMsg(html, sendToUserId, chatRoomId);
        }

        //客服选中进行通话的客服
        function changeChatRoomInfo(userId, roomId, userName) {
            //判断是否存在未读信息
            var msgNum = $("#" + userName + userId).text();
            if (msgNum) {
                showHistoryMsg(userId);
            }

            //打开对话框做准备
            sendToUserId = userId;
            sendToUserName = userName;
            chatRoomId = roomId;
            openLtWebSocket(nowUserId, roomId);
            $("#sendToUserName").text(userName);
        }

        //客服需要获得所有用户
        function getMyUser() {
            $.ajax({
                url: '/user/getUserList',
                type: 'GET',
                success: function (data) {
                    var datas = JSON.parse(data);
                    $("#leftContentBox").html('');
                    for (var i = 0; i < datas.length; i++) {
                        $("#leftContentBox").append(getUserListHtml(datas[i]));
                    }
                }
            });
        }

        function getUserListHtml(map) {
            var html = "<div class=\"content-list\" onclick=\"changeChatRoomInfo('" + map.id + "','" + map.chatRoomId + "','" + map.loginName + "')\">" +
                "<div class=\"content-list-icon\"></div>" +
                "<span class=\"content-list-icon-name\">" + map.loginName + "</span>\n" +
                "<div class=\"content-list-icon-label\">" +
                "<span id='" + map.loginName + map.id + "'><span></div></div>";
            return html;
        }

        //定时刷新，获得其他用户发给我的信息
        function refreshMessageNumber() {
            $.ajax({
                url: '/historyMessage/getHistoryMessageNum',
                type: 'get',
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $("#" + data[i].loginName + data[i].id).text(data[i].msgNum);
                    }
                }
            });
        }

        function showHistoryMsg(sendUserId) {
            $.ajax({
                url: '/historyMessage/findHistoryMessage',
                type: 'POST',
                data: {'sendUserId': sendUserId},
                success: function (data) {
                    var $chatShowBox =  $("#chatShowBox");
                    for(var i = 0; i < data.length; i++){
                        $chatShowBox.append(getReceptionMessageHtml(data[i].msg));
                    }
                }
            });
        }

    </script>
    <style type="text/css">
        .content-list:hover {
            background: #D2E6F9;
        }
    </style>
</head>
<body>
<div style="height: 590px;width: 1130px;margin: 20px auto;background-color: #FFFFFF;">
    <div class="left-pane">
        <div class="left-header">
            <div class="user-info">
                <div class="user-info-left">
                </div>
                <div class="user-info-right">
                    <div class="user-name"><%= user.getLoginName()%>
                    </div>
                    <div class="user-status" id="userStatus">在线</div>
                </div>
            </div>
            <div class="search-bar">
                <input class="search-bar-btn" placeholder="搜索最近联系的人"/>
            </div>
        </div>
        <div class="left-content">
            <div class="left-content-box" id="leftContentBox">
                <div class="content-list">
                    <div class="content-list-icon"></div>
                    <span class="content-list-icon-name" id="kfName">admin客服</span>
                    <div class="content-list-icon-label">官方</div>
                </div>

            </div>

        </div>
    </div>
    <div class="right-pane">
        <div class="right-pane-header">
            <div class="right-pane-header-content">
                <div class="right-pane-header-content-icon"></div>
                <span class="right-pane-header-content-name" id="sendToUserName">请选择聊天用户</span>
            </div>
        </div>
        <div class="right-pane-body">
            <div class="chat">
                <div class="chat-show">
                    <div class="chat-show-box" id="chatShowBox">

                        <div class="chat-show-box-li">
                            <div class="msg-box">
                                <div class="msg-box-left">
                                    <div class="user-icon"></div>
                                </div>
                                <div class="msg-box-right">
                                    <div class="from-username">在线客服</div>
                                    <div class="msg-content">
                                        <p>Hi~购买服务遇到问题，可以在这里提出，我们会为您解决，JIMI随时都陪伴着你~</p>
                                    </div>
                                </div>
                                <!--解决div高度不能自增长的问题-->
                                <div class="clearfloat"></div>
                            </div>
                        </div>

                        <div class="chat-show-box-li">
                            <div class="msg-box">
                                <div class="msg-box-left-me">
                                    <div class="from-username-me">在线客服</div>
                                    <div class="msg-content">
                                        <p>Hi~购物遇到问题，可以点击底部快捷捷按钮捷按钮捷按钮按钮或直接输入问题，JIMI随时都陪伴着你~</p>
                                    </div>

                                </div>
                                <div class="msg-box-right-me">
                                    <div class="user-icon-me"></div>
                                </div>
                                <!--解决div高度不能自增长的问题-->
                                <div class="clearfloat"></div>
                            </div>
                        </div>

                        <div class="chat-show-box-li">
                            <div class="msg-box">
                                <div class="msg-box-left">
                                    <div class="user-icon"></div>
                                </div>
                                <div class="msg-box-right">
                                    <div class="from-username">在线客服</div>
                                    <div class="msg-content">
                                        <p>Hi~购物遇到问题，可以点击底部快捷按钮或直接输入问题，JIMI随时都陪伴着你~</p>
                                        <p>Hi~购物遇到问题，可以点击底部快捷按钮或直接输入问题，JIMI随时都陪伴着你~</p>
                                        <p>Hi~购物遇到问题，可以点击底部快捷按钮或直接输入问题，JIMI随时都陪伴着你~</p>
                                    </div>
                                </div>
                                <!--解决div高度不能自增长的问题-->
                                <div class="clearfloat"></div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="chat-editer">
                    <div class="toolbar" id="editerToopBar" style="text-align: right;">
                        <input class="btn btn-default btn-sm" style="width: 30px;height: 20px;margin-top: 4px" value="发送"
                               onclick="sendMessage();"/>
                    </div>
                    <div class="editerBox" id="editerContent"></div>
                </div>
            </div>
            <div class="show-heistory">
                <div class="show-heistory-top">
                    <div class="show-heistory-top-left">正在咨询</div>
                    <div class="show-heistory-top-right">常见问题</div>
                </div>
                <div class="show-heistory-body">
                    <div class="content-item">
                        <h4>Hi~购物遇到问题，可以点击底部快捷按钮捷按钮捷按钮捷按钮捷按钮捷按Hi~购物遇到按Hi~购物遇到按Hi~购物遇到</h4>
                        <input class="btn btn-default btn-sm" style="float:right; width: 50px;height: 20px;margin-right: 10px" value="发送"/>
                    </div>
                    <div class="content-item">
                        <h4>Hi~购物遇到问题，可以点击底部快捷按钮捷按钮捷按钮捷按钮捷按钮捷按Hi~购物遇到按Hi~购物遇到按Hi~购物遇到</h4>
                        <input class="btn btn-default btn-sm" style="float:right; width: 50px;height: 20px;margin-right: 10px" value="发送"/>
                    </div>
                    <div class="content-item">
                        <h4>Hi~购物遇到问题，可以点击底部快捷按钮捷按钮捷按钮捷按钮捷按钮捷按Hi~购物遇到按Hi~购物遇到按Hi~购物遇到</h4>
                        <input class="btn btn-default btn-sm" style="float:right; width: 50px;height: 20px;margin-right: 10px" value="发送"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
