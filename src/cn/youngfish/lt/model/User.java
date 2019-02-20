package cn.youngfish.lt.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * ClassName User <br>
 * Description TODO 。<br>
 * Date 2019/2/16 19:34 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class User implements Serializable {

    /**离线*/
    public static Integer OFF_LINE_STATUS = -1;
    /**繁忙*/
    public static Integer BUSY_STATUS = 0;
    /**在线*/
    public static Integer ON_LINE_STATUS = 1;

    private static final long serialVersionUID = -5594724695266791481L;

    private Integer id;
    private String loginName;
    private String password;
    private Integer permissions;
    private Integer status;

    public User() {
    }

    public User(Integer id, String loginName, String password) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
    }

    public User(Integer id, String loginName, String password, Integer permissions) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(loginName, user.loginName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginName, password);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", loginName='" + loginName + '\'' + ", password='" + password + '\'' + ", permissions=" + permissions + ", status=" + status + '}';
    }
}
