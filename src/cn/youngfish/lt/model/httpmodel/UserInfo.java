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

    public static User USER_INFO;

    //客服id
    public static Integer USER_KFID = -1;
    //聊天室id
    public static Integer CHAT_ROOM_ID = -1;
    //客服名称
    public static String KF_USER_NAME = "";

    /**
     * 判断当前是否有用户登录 。<br>
     * Date 19:33 2019/2/19 <br>
     *
     * @return true 表示有， false 表示不存在
     */
    public static boolean hasUser() {
        if (USER_INFO != null) {
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
    public static Integer getUserPermissions() {
        return USER_INFO.getPermissions();
    }

}
