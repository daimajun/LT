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
        showStatus('WebSocket连接发生错误');
    }

    //连接成功建立的回调方法
    ltSocket.onopen = function () {
        showStatus('WebSocket连接成功');
    }

    //连接关闭的回调方法
    ltSocket.onclose = function () {
        showStatus('WebSocket连接关闭');
    }

    //接收消息回调函数
    ltSocket.onmessage = function (event) {
        var $showSendMsg = $("#" + ltShowSendMessage);
        var html = $showSendMsg.html();
        html += "<li>" + event.data + "</li>";
        $showSendMsg.html(html);
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
}


