package cn.youngfish.lt.model.httpmodel;

import cn.youngfish.lt.model.User;

/**
 * ClassName UserInfo <br>
 * Description TODO 。<br>
 * Date 2019/2/19 9:42 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class UserInfo {

    private User user;
    //客服id
    private Integer kfUserID = -1;
    //聊天室id
    private Integer chatRoomId = -1;
    //客服名称
    private String kfUserName;

    public UserInfo(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getKfUserID() {
        return kfUserID;
    }

    public void setKfUserID(Integer kfUserID) {
        this.kfUserID = kfUserID;
    }

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getKfUserName() {
        return kfUserName;
    }

    public void setKfUserName(String kfUserName) {
        this.kfUserName = kfUserName;
    }

    /**
     * 判断当前是否有用户登录 。<br>
     * Date 19:33 2019/2/19 <br>
     *
     * @return true 表示有， false 表示不存在
     */
    public boolean hasUser() {
        if (user != null) {
            return true;
        }
        return false;
    }

    /**
     * 获得当前用户权限 。<br>
     * Date 19:35 2019/2/19 <br>
     *
     * @return -1 表示管理员，0 表示普通用户
     */
    public Integer getUserPermissions() {
        return user.getPermissions();
    }

}
