package cn.youngfish.lt.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * ClassName HistoryMessage <br>
 * Description TODO 。<br>
 * Date 2019/2/22 19:19 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class HistoryMessage implements Serializable {
    private static final long serialVersionUID = 6884082162734799831L;

    private Integer id;
    private Integer sendUserId;
    private Integer desUserId;
    private String msg;
    private Date sendDate;
    private Integer isRead;//0表示未读，1表示已读

    public HistoryMessage() {
    }

    public HistoryMessage(Integer sendUserId, Integer desUserId, String msg, Date sendDate, Integer isRead) {
        this.sendUserId = sendUserId;
        this.desUserId = desUserId;
        this.msg = msg;
        this.sendDate = sendDate;
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getDesUserId() {
        return desUserId;
    }

    public void setDesUserId(Integer desUserId) {
        this.desUserId = desUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HistoryMessage that = (HistoryMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(sendUserId, that.sendUserId) && Objects.equals(desUserId, that.desUserId) && Objects.equals(msg, that.msg) && Objects.equals(sendDate, that.sendDate) && Objects.equals(isRead, that.isRead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sendUserId, desUserId, msg, sendDate, isRead);
    }

    @Override
    public String toString() {
        return "HistoryMessage{" + "id=" + id + ", sendUserId=" + sendUserId + ", desUserId=" + desUserId + ", msg='" + msg + '\'' + ", " + "sendDate=" + sendDate + ", isRead=" + isRead + '}';
    }
}
