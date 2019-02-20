package cn.youngfish.lt.server;

import cn.youngfish.lt.model.httpmodel.AjaxInfo;

/**
 * ClassName LoginService <br>
 * Description 用户登录服务 。<br>
 * Date 2019/2/16 21:13 <br>
 *
 * @author fish
 * @version 1.0
 **/
public interface LoginService {

    /**
     * 验证用户登录是否正确 。<br>
     * Date 9:58 2019/2/17 <br>
     *
     * @param loginName 用户名
     * @param password  用户密码
     * @return boolea true正确则成功
     */
    AjaxInfo validateUserLogin(String loginName, String password);

}
