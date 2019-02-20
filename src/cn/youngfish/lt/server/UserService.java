package cn.youngfish.lt.server;

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

}
