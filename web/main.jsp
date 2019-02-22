<%@ page import="cn.youngfish.lt.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>案例</title>

    <link href="css/bootstrap.css" rel="stylesheet">
    <script src="js/jquery-3.3.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="wangEdite/wangEditor.min.js"></script>
    <script src="js/LT.js"></script>
    <title>登录聊天室</title>
    <%
        User user_info = (User) request.getSession().getAttribute("USER_INFO");
        Integer permission = user_info.getPermissions();
    %>
    <script type="text/javascript">
        var permission = '<%= permission%>';
        var editer;
        $(function () {

            //初始化editer插件
            var E = window.wangEditor;
            editer = new E("#editerToopBar", "#editerContent");
            editer.customConfig.menus = [
                'head',  // 标题
                'bold',  // 粗体
                'fontSize',  // 字号
                'fontName',  // 字体
                'italic',  // 斜体
                'underline',  // 下划线
                'strikeThrough',  // 删除线
                'foreColor',  // 文字颜色
                'backColor',  // 背景颜色
                'link',  // 插入链接
                'list',  // 列表
                'justify',  // 对齐方式
                'emoticon',  // 表情
                'code',  // 插入代码
                'undo',  // 撤销
                'redo'  // 重复
            ];
            editer.create()

            //初始化好友列表
            getFrientList();

        });

        function getFrientList() {
            $.ajax({
                url: "/user/getUserList",
                type: "GET",
                success: function (data) {
                    var parse = JSON.parse(data);
                    var $headerOne = $("#headerOne");
                    var $headerTwo = $("#headerTwo");
                    var $friendListOne = $("#friendListOne");
                    var $friendListTwo = $("#friendListTwo");
                    console.log(parse.length);

                    //TODO 目前 不考虑以下需求
                    //根据当前登录用户显示对应的管理和好友列表
                    /*if (permission === '-1') {
                        $headerOne.html("服务人员");
                        $friendListOne.html("");
                        for (var i = 0; i < parse.length; i++) {
                            $friendListOne.append("<li class=\"list-group-item\"><span><"
                                + parse[i].id + "></span><a href=\"#\">" + parse[i].loginName + "</a></li>");
                        }


                    }*/

                    /*
                        获得所有好友信息，并显示在列表中
                     */
                    $friendListOne.html("");
                    for (var i = 0; i < parse.length; i++) {
                        $friendListOne.append("<li class=\"list-group-item\"><span><"
                            + parse[i].id + "></span><a href=\"JavaScript:openLtWebSocket('" + parse[i].id + "','" + parse[i].loginName + "');\">"
                            + parse[i].loginName + "</a></li>");
                    }
                }
            });
        }
    </script>
    <style type="text/css">
        .editerTopBar > span {
            height: 30px;
            line-height: 30px;
            text-align: center;
            padding-left: 5px;
        }

        .showContent {
            border: 1px solid #ccc;
            width: 100%;
            height: 300px;
        }

        .toolbar {
            border: 1px solid #ccc;
        }

        .buttonBox {
            border: 1px solid #ccc;
            width: 100%;
            padding: 5px 0;
            overflow: auto;
        }

        .buttonBox > input {
            float: right;
            margin-right: 5px;
        }

        .text {
            border: 1px solid #ccc;
            height: 100px;
        }
    </style>

</head>
<body>
<div class="container" style="height: 100%">
    <div class="col-sm-8" style="height: 100%; padding-top: 50px;">
        <div class="editerTopBar" style="text-align: center; width: 100%; height: 32px;border: 1px solid #ccc;">
            <span style="float:left;" id="nowUserStatus">未连接状态</span>
            <span id="sendToUser">请选择对话好友</span>
            <span id="nowUser" style="float: right">当前用户：<%=user_info.getLoginName()%></span>
        </div>
        <div class="showContent" id="showContent" style="overflow: auto"></div>
        <div class="toolbar" id="editerToopBar"></div>
        <div class="buttonBox">
            <input type="button" class="btn btn-sm" value="聊天记录"/>
            <input type="button" class="btn btn-sm" onclick="senTxt();" value="发送"/>
        </div>
        <div class="text" id="editerContent"></div>
    </div>
    <div class="col-sm-4" style="height: 100%;padding-top: 50px;">
        <h3 style="width: 100%;text-align: center;margin: 0;" id="headerOne">你的好友</h3>
        <div style="overflow-x: hidden;overflow-y: auto;">
            <ul class="list-group" id="friendListOne">
                <li class="list-group-item">Dapibus ac facilisis in
                    <span class="badge">42</span>
                </li>
                <li class="list-group-item">Morbi leo risus</li>
                <li class="list-group-item">Porta ac consectetur ac</li>
                <li class="list-group-item">Vestibulum at eros</li>
                <li class="list-group-item">Porta ac consectetur ac</li>
                <li class="list-group-item">Porta ac consectetur ac</li>
                <li class="list-group-item">Porta ac consectetur ac</li>
                <li class="list-group-item">Porta ac consectetur ac</li>
            </ul>
        </div>
        <%--<h3 style="width: 100%;text-align: center;margin-bottom: 0;" id="headerTwo">在线客服</h3>
        <div style="height: 170px;overflow-x: hidden;overflow-y: auto;">
            <ul class="list-group" id="friendListTwo">
                <li class="list-group-item">Dapibus ac facilisis in
                    <span class="badge">42</span>
                </li>
                <li class="list-group-item">Morbi leo risus</li>
                <li class="list-group-item">Porta ac consectetur ac</li>
                <li class="list-group-item">Vestibulum at eros</li>
            </ul>
        </div>--%>
    </div>
</div>
</body>

<script type="text/javascript">
    /*
        实现一一对话的主要思路
        1.通过websocket的路径结尾字符串来区别，在url结尾有一个可变字符串，用,隔开，前面表示当前用户唯一标识（userId），
          后面一个标识会话唯一标识（socketId）
        2.userId标识当前用户，主要是当前创建连接的用户名，标识某个用户连接了那个聊天室
        3.socketId标识聊天室，一个用户可能连接多个聊天室，这时就可以控制当两个用户向同一个用户发送消息的时候，
          不会在同一个信息显示框中显示，需要打开对应的框才行。
        4.关于socketId的规定，因为用户名是唯一的且用户id是int自增长类型，所以就用两个用户名拼接成，拼接的先后顺序就以id大的在前反正在后。

     */

    //当前用户
    var nowUserName = '<%= user_info.getLoginName()%>';
    //当前用户的id
    var nowUserId = '<%= user_info.getId()%>';
    var sendToUserName;//发送消息的对象
    var webSocketId;//聊天室唯一标识
    var url = "ws://" + window.location.host + "/chat/";

    //打开与某个用户的会话
    function openLtWebSocket(userId, userName) {
        //关闭上一聊天室
        if (ltSocket != null) {
            ltSocket = null;
        }
        if (userId.length <= 0) {
            alert("选择聊天对象的id出现问题！刷新页面重试！");
            return;
        }
        if (userName.length <= 0) {
            alert("选择聊天对象的name出现问题！刷新页面重试！");
            return;
        }
        $("#sendToUser").text(userName);
        sendToUserName = userName;
        if (nowUserId > userId) {
            webSocketId = nowUserName + userName;
        } else {
            webSocketId = userName + nowUserName;
        }
        url += nowUserName + "," + webSocketId;
        //初始化websocket插件
        lt(url, 'nowUserStatus', 'showContent');
    }

    //发送消息
    function senTxt() {
        var html = editer.txt.html();
        editer.txt.html('');
        sendMsg(html, sendToUserName, webSocketId);
    }

    //判断当前浏览器是否支持WebSocket
    //判断当前浏览器是否支持WebSocket
    /*if ('WebSocket' in window) {
        websocket = new WebSocket(url);
    } else {
        alert('当前浏览器 Not support websocket')
    }
    //连接发生错误的回调方法
    websocket.onerror = function () {
        showStatus('WebSocket连接发生错误');
    }

    //连接成功建立的回调方法
    websocket.onopen = function () {
        showStatus('WebSocket连接成功');
    }
    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }
    //连接关闭的回调方法
    websocket.onclose = function () {
        showStatus('WebSocket连接关闭');
    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        var html = $('#showContent').html();
        html += "<li>" + innerHTML + "</li>";
        $('#showContent').html(html);
    }

    //显示当前连接状态
    function showStatus(status) {
        $("#nowUserStatus").text(status);
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var html = editer.txt.html();
        editer.txt.html('');
        //message作为发送的信息，role作为发送的对象标识，socketId是此次会话的标识
        websocket.send(JSON.stringify({'message': html, 'sendToUserId': sendToUserId, 'socketId': webSocketId}));
    }*/

</script>
</html>
