/**
 * 创建 LT 对象
 * @param url 连接url
 * @param showStatusTag 显示状态的标签id
 * @param showSendMessage 显示聊天内容的标签id
 * @constructor
 */
var ltUrl;
var ltShowStatusTag;
var ltShowSendMessage;
var ltSocket;

function lt(url, showStatusTag, showSendMessage) {
    if (ltSocket != null) {
        ltSocket = null;
    }
    ltUrl = url;
    ltShowStatusTag = showStatusTag;
    ltShowSendMessage = showSendMessage;

    //创建聊天对象
    if ('WebSocket' in window) {
        ltSocket = new WebSocket(url);
    } else {
        alert("当前浏览器不支持websocket!");
    }

    //连接发生错误的回调方法
    ltSocket.onerror = function () {
        showStatus('登录错误');
    }

    //连接成功建立的回调方法
    ltSocket.onopen = function () {
        showStatus('在线');
    }

    //连接关闭的回调方法
    ltSocket.onclose = function () {
        showStatus('离线');
    }

    //接收消息回调函数
    ltSocket.onmessage = function (event) {
        var $showSendMsg = $("#" + ltShowSendMessage);
        $showSendMsg.append(getReceptionMessageHtml(event.data));
        $showSendMsg[0].scrollTop = $showSendMsg[0].scrollHeight;
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        ltSocket.close();
    }

}

/**
 * 显示状态
 * @param msg
 */
function showStatus(msg) {
    $("#" + ltShowStatusTag).text(msg);
}

/**
 * 向指定用户发送消息
 * @param msg 消息内容
 * @param sendToUserId 发送目标id
 * @param webSocketId 会话唯一标识符
 */
function sendMsg(msg, sendToUserId, webSocketId) {
    //message作为发送的信息，sendToUserId作为发送的对象标识，socketId是此次会话的标识
    ltSocket.send(JSON.stringify({'message': msg, 'sendToUserId': sendToUserId, 'socketId': webSocketId}));
    var $showSendMsg = $("#" + ltShowSendMessage);
    $showSendMsg.append(getSendMessageHtml(msg));
    $showSendMsg[0].scrollTop = $showSendMsg[0].scrollHeight;
}

function getReceptionMessageHtml(html) {
    var html = "<div class=\"chat-show-box-li\">" +
        "<div class=\"msg-box\">" +
        "<div class=\"msg-box-left\">" +
        "<div class=\"user-icon\"></div></div>" +
        "<div class=\"msg-box-right\">" +
        "<div class=\"from-username\">" + sendToUserName + "</div>" +
        "<div class=\"msg-content\">" + html + "</div></div>" +
        "<div class=\"clearfloat\"></div></div></div>";
    return html;
}

function getSendMessageHtml(html) {
    var html = "<div class=\"chat-show-box-li\">" +
        "<div class=\"msg-box\">" +
        "<div class=\"msg-box-left-me\">" +
        "<div class=\"from-username-me\">" + nowUserName + "</div>" +
        "<div class=\"msg-content-me\">" + html + "</div></div>" +
        "<div class=\"msg-box-right-me\">" +
        "<div class=\"user-icon-me\"></div></div>" +
        "<div class=\"clearfloat\"></div></div></div>";
    return html;
}
