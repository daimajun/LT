package cn.youngfish.lt.controller;

import cn.youngfish.lt.model.User;
import cn.youngfish.lt.model.httpmodel.UserInfo;
import cn.youngfish.lt.util.JDBCUtils;
import cn.youngfish.lt.util.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ClassName UserServlet <br>
 * Description 处理用户相关请求 。<br>
 * Date 2019/2/19 11:36 <br>
 *
 * @author fish
 * @version 1.0
 **/
@WebServlet({"/user/getUserList"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //判断是否是以getUserList结尾的请求，表示获得所有服务的用户（前提是当前用户是）
        if (req.getRequestURI().endsWith("user/getUserList")) {
            List<User> userList = getUserList();
            resp.getWriter().write(StringUtils.objectToJsonString(userList));
            return;
        }
    }

    private List<User> getUserList() {
        JdbcTemplate jdbCtemplate = JDBCUtils.getJDBCtemplate();

        //获得对应权限用户
        Integer permission = 0;
        String sql = "select * from user";

        //管理员所获得到的好友列表
        if (UserInfo.getUserPermissions() == -1) {
            permission = 0;
            return jdbCtemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        }

        //普通户获得到的好友列表
        if (UserInfo.getUserPermissions() == 0) {
            permission = -1;
            return jdbCtemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        }
        return null;
    }
}
