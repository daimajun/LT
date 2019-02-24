package cn.youngfish.lt.server.impl;

import cn.youngfish.lt.model.User;
import cn.youngfish.lt.model.httpmodel.AjaxInfo;
import cn.youngfish.lt.server.LoginService;
import cn.youngfish.lt.util.JDBCUtils;
import cn.youngfish.lt.util.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * ClassName LoginServiceImpl <br>
 * Description 登录服务实现 。<br>
 * Date 2019/2/16 21:15 <br>s
 *
 * @author fish
 * @version 1.0
 **/
public class LoginServiceImpl implements LoginService {

    @Override
    public AjaxInfo validateUserLogin(String loginName, String password) {
        AjaxInfo ajaxInfo = null;

        //登录名为空
        if (StringUtils.isEmpty(loginName)) {
            return new AjaxInfo(false, "登录失败！登录名不能为空！");
        }

        //登录密码为空
        if (StringUtils.isEmpty(password)) {
            return new AjaxInfo(false, "登录失败！登录密码不能为空！");
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = " select * from User where loginName = ? and password = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), loginName, password);
        boolean isSuccess = users.size() == 1;
        ajaxInfo = new AjaxInfo(isSuccess, isSuccess ? "登录成功！" : "登录失败！账户或密码错误！", isSuccess ? users.get(0) : null);

        return ajaxInfo;
    }
}






