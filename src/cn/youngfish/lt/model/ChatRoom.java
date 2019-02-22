package cn.youngfish.lt.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * ClassName ChatRoom <br>
 * Description 聊天室类 。<br>
 * Date 2019/2/21 21:29 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = -4091802236946885417L;

    private Integer id;
    private Integer kfId;
    private Integer userId;

    public ChatRoom() {
    }

    public ChatRoom(Integer id, Integer kfId, Integer userId) {
        this.id = id;
        this.kfId = kfId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKfId() {
        return kfId;
    }

    public void setKfId(Integer kfId) {
        this.kfId = kfId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(id, chatRoom.id) && Objects.equals(kfId, chatRoom.kfId) && Objects.equals(userId, chatRoom.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kfId, userId);
    }

    @Override
    public String toString() {
        return "ChatRoom{" + "id=" + id + ", kfId=" + kfId + ", userId=" + userId + '}';
    }
}
