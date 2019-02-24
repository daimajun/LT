package cn.youngfish.lt.controller;

import cn.youngfish.lt.model.httpmodel.AjaxInfo;
import cn.youngfish.lt.model.httpmodel.UserInfo;
import cn.youngfish.lt.server.UserService;
import cn.youngfish.lt.server.impl.UserServiceImpl;
import cn.youngfish.lt.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ClassName UserServlet <br>
 * Description 处理用户相关请求 。<br>
 * Date 2019/2/19 11:36 <br>
 *
 * @author fish
 * @version 1.0
 **/
@WebServlet({"/user/getUserList", "/user/validateCheckCode"})
public class UserServlet extends HttpServlet {

    private static UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserInfo userInfo = (UserInfo) req.getSession().getAttribute("USER_INFO");

        //判断是否是以getUserList结尾的请求，表示获得所有服务的用户（前提是当前用户是）
        if (req.getRequestURI().endsWith("user/getUserList")) {
            List<Map<String, Object>> userList = userService.getUserList(userInfo.getUser());
            resp.getWriter().write(StringUtils.objectToJsonString(userList));
            return;
        } else if (req.getRequestURI().endsWith("user/validateCheckCode")) {
            //前端页面进行时时验证码验证
            String checkCode = req.getParameter("checkCode");
            String attribute = (String) req.getSession().getAttribute("CHECK_CODE_KEY");
            if (checkCode.equalsIgnoreCase(attribute)) {
                resp.getWriter().write(StringUtils.objectToJsonString(new AjaxInfo(true, "")));
                return;
            } else {
                resp.getWriter().write(StringUtils.objectToJsonString(new AjaxInfo(false, "")));
            }

        }
    }

}
