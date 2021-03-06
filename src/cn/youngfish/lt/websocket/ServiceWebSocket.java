package cn.youngfish.lt.websocket;

import cn.youngfish.lt.model.HistoryMessage;
import cn.youngfish.lt.model.httpmodel.AjaxInfo;
import cn.youngfish.lt.server.HistoryMessageService;
import cn.youngfish.lt.server.impl.HistoryMessageServiceImpl;
import net.sf.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName ServiceWebSocket <br>
 * Description 处理会话类 。<br>
 * Date 2019/2/19 21:15 <br>
 *
 * @author fish
 * @version 1.0
 **/
@ServerEndpoint("/chat/{userIdAndSocketId}")
public class ServiceWebSocket {

    //静态计数器，需要设置成为线程安全的
    private static int onlineCount;

    //实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key为用户标识（唯一标识）
    private static Map<String, ServiceWebSocket> connections = new ConcurrentHashMap<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //用户唯一标识
    private String userId;
    //会话标识
    private String socketId;
    //对象信息对象
    HistoryMessage historyMessage;
    //保存对话信息对象
    private HistoryMessageService historyMessageService;


    /**
     * 连接建立成功调用的方法
     *
     * @param session           可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param userIdAndSocketId 用户唯一标识和会话标识，他们用','连接
     */
    @OnOpen
    public void onOpen(@PathParam("userIdAndSocketId") String userIdAndSocketId, Session session) {
        historyMessageService = new HistoryMessageServiceImpl();
        this.session = session;

        String[] arr = userIdAndSocketId.split(",");
        //用户标识
        this.userId = arr[0];
        //会话标识
        this.socketId = arr[1];

        //将用户添加到集合中
        connections.put(userId, this);
        // 在线数加
        addOnlineCount();

        //显示当前在线人数
        System.out.println("有新连接加入！新用户：" + userId + ",当前在线人数为" + getOnlineCount());
    }

    /**
     * 当用户断开连接时 。<br>
     * Date 12:22 2019/2/20 <br>
     */
    @OnClose
    public void onClose() {
        //用户断开连接，从集合中移除
        connections.remove(userId);
        //在线人数减一
        subOnlineCount();
        //显示当前人数
        System.out.println(userId + "断开连接！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        //将字符串解析成JSON对象
        JSONObject json = JSONObject.fromObject(message);

        //需要发送的信息
        String msg = null;
        //发送对象的用户标识
        String to = null;
        if (json.has("message")) {
            msg = (String) json.get("message");
        }
        if (json.has("sendToUserId")) {
            to = (String) json.get("sendToUserId");
        }

        //判断对方用户是否存在，并且在指定的聊天室,存在直接发送消息，不存在保存在数据库中
        if (isHaveChatRoom(to)) {
            send(msg, userId, to, socketId);
            saveMessage(msg, to, 1);
        } else {
            saveMessage(msg, to, 0);
        }
    }

    /**
     * 保存用户的聊天记录，分为两种已看和未看的 。<br>
     * Date 11:30 2019/2/24 <br>
     *
     * @param msg    聊天消息
     * @param to     发送目标
     * @param isRead 是否已阅读（0 - 未读，1 - 已读）
     */
    private void saveMessage(String msg, String to, Integer isRead) {
        historyMessage = new HistoryMessage(Integer.parseInt(userId), Integer.parseInt(to), msg, new Date(), isRead);
        AjaxInfo ajaxInfo = historyMessageService.saveHistoryMessage(historyMessage);
        if (!ajaxInfo.getSuccess()) {
            System.out.println("保存聊天记录失败");
        }
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送给指定用户 。<br>
     * Date 12:26 2019/2/20 <br>
     *
     * @param msg      需要发送的消息
     * @param toUserId 需要发送的对象id
     * @param socketId 会话的标识
     */
    public static void send(String msg, String fromUserId, String toUserId, String socketId) {
        try {
            ServiceWebSocket con = connections.get(toUserId);
            if (con != null) {
                if (socketId == con.socketId || con.socketId.equals(socketId)) {
                    con.session.getBasicRemote().sendText(msg);
                }
            }
        } catch (IOException e) {
            // TODO 发送错误时需要处理的
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ServiceWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ServiceWebSocket.onlineCount--;
    }

    /**
     * 判断聊天对象是否存在，主要通过对方用户id和之间的聊天室唯一id 。<br>
     * Date 19:08 2019/2/22 <br>
     *
     * @param toUserId 对方用户id
     * @return true 表示存在， false 表示不存在
     */
    public boolean isHaveChatRoom(String toUserId) {
        for (Map.Entry<String, ServiceWebSocket> entry : connections.entrySet()) {
            if (entry.getKey().equals(toUserId) && entry.getValue().socketId.equals(this.socketId)) {
                return true;
            }
        }
        return false;
    }

}
