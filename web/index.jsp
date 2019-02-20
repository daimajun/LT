<%--
  Created by IntelliJ IDEA.
  User: fish
  Date: 2019/2/16
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>案例</title>

    <link href="css/bootstrap.css" rel="stylesheet">
    <script src="js/jquery-3.3.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.validate.min.js"></script>
    <title>登录聊天室</title>
    <script type="text/javascript">

        $(function () {
            var cookies = document.cookie;
            if (cookies != null) {
                var cookiesTemp = cookies.split(";");
                for (var i = 0; i < cookiesTemp.length; i++) {
                    var cookieStr = cookiesTemp[i].split("=");
                    if ("USER_NAME" == cookieStr[0].trim()) {
                        $("#loginName").val(cookieStr[1].trim());
                    }
                    if ("USER_PSD" == cookieStr[0].trim()) {
                        $("#password").val(cookieStr[1].trim());
                    }
                }
            }


            $("#loginForm").validate({
                rules: {
                    "loginName": {
                        "required": true,
                        "rangelength": [4, 8]
                    },
                    "password": {
                        "required": true,
                        "rangelength": [8, 12]
                    }
                },
                messages: {
                    "loginName": {
                        "required": "请输入登录名",
                        "rangelength": "登录名在4~8位"
                    },
                    "password": {
                        "required": "请输入登录密码",
                        "rangelength": "登录密码在8~12位"
                    }
                }
            });
        });

        function login() {
            var $loginForm = $("#loginForm");
            if ($loginForm.valid()) {
                $.ajax({
                    url: "/login",
                    type: "POST",
                    data: $loginForm.serialize(),
                    success: function (data) {
                        if (data.success) {
                            location.href = "/main.jsp";
                        } else {
                            var message = data.msg;
                            if (message != null && message.length > 0) {
                                $("#messageContent").text(message).show();
                            }
                        }
                    }
                });
            }
        }


    </script>

</head>
<body>
<form action="" method="post" id="loginForm">
    <div class="container" style="width: 430px;height: 330px;background-color: antiquewhite;margin: 150px auto;">
        <div class="input-group" style="margin: 10px 0;">
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            </span>
            <input type="text" id="loginName" name="loginName" class="form-control" placeholder="登录名">
        </div>
        <div style="height: 20px;">
            <label for="loginName" generated="true" class="error" style="display: none;color: red;">"输入有误，请重新输入！"</label>
        </div>

        <div class="input-group" style="margin: 10px 0;">
            <span class="input-group-addon">
                <span class="glyphicon glyphicon-hdd" aria-hidden="true"></span>
            </span>
            <input type="password" id="password" name="password" class="form-control" placeholder="登录密码">
        </div>
        <div style="height: 20px;">
            <label for="password" generated="true" class="error" style="display: none;color: red;">"输入有误，请重新输入！"</label>
        </div>
        <div class="input-group" style="margin: 10px 0;height: 30px;line-height: 30px;width: 100%">
            <input type="checkbox" name="remberPsd" checked="checked" style="width: 15px;height: 15px;float: left;margin-top: 7px; ">
            <span style="float: left">记住密码</span>
            <a style="float: right;">找回密码</a>
        </div>
        <div class="input-group text-center" style="margin: 10px 0;width:100%;">
            <input type="button" class="btn btn-primary" style="width: 50%;" value="提交" onclick="login();">
        </div>
        <div style="height: 20px;" id="message">
            <label id="messageContent" class="error" style="display: none;color: red;">"输入有误，请重新输入！"</label>

        </div>
    </div>
</form>
</body>
</html>
