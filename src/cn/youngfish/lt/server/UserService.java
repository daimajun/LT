package cn.youngfish.lt.server;

import cn.youngfish.lt.model.ChatRoom;
import cn.youngfish.lt.model.User;

import java.util.List;
import java.util.Map;

/**
 * ClassName UserService <br>
 * Description 用户相关服务 。<br>
 * Date 2019/2/19 20:58 <br>
 *
 * @author fish
 * @version 1.0
 **/
public interface UserService {

    /**
     * 该表用户当前状态 。<br>
     * Date 21:01 2019/2/19 <br>
     *
     * @return true 表示改变成功， false 表示改变状态出现了错误
     */
    boolean changeUserStatus(Integer id, Integer status);

    /**
     * 获得所有的好友列表 。<br>
     * Date 9:05 2019/2/21 <br>
     *
     * @return List<User> 用户List集合
     */
    List<Map<String, Object>> getUserList();

    /**
     * 根据用户id获得用户信息 。<br>
     * Date 22:02 2019/2/21 <br>
     *
     * @param id 需要查询的用户id
     * @return user对象
     */
    User getUserById(Integer id);

    /**
     * 通过用户id获得聊天室相关信息 。<br>
     * Date 22:03 2019/2/21 <br>
     *
     * @param userId 用户id
     * @return ChatRoom 对象
     */
    ChatRoom getChatRoomByUserId(Integer userId);

}
