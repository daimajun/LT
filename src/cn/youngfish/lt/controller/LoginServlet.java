package cn.youngfish.lt.controller;

import cn.youngfish.lt.model.User;
import cn.youngfish.lt.model.httpmodel.AjaxInfo;
import cn.youngfish.lt.server.LoginService;
import cn.youngfish.lt.server.UserService;
import cn.youngfish.lt.server.impl.LoginServiceImpl;
import cn.youngfish.lt.server.impl.UserServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * ClassName LoginServlet <br>
 * Description 用户登录控制台 。<br>
 * Date 2019/2/16 20:15 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class LoginServlet extends HttpServlet {

    private LoginService loginService;

    /**
     * 初始化用户登录服务 。<br>
     * Date 10:14 2019/2/17 <br>
     */
    @Override
    public void init() throws ServletException {
        if (loginService == null) {
            loginService = new LoginServiceImpl();
        }
    }

    /**
     * 返回登录验证数据 。<br>
     * Date 10:15 2019/2/17 <br>
     *
     * @param req  请求数据
     * @param resp 返回数据
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("content-type", "text/json;charset=UTF-8");
        String loginName = req.getParameter("loginName");
        String password = req.getParameter("password");
        String remberPsd = req.getParameter("remberPsd");

        AjaxInfo ajaxInfo = loginService.validateUserLogin(loginName, password);
        if (ajaxInfo.getSuccess()) {
            UserService userService = new UserServiceImpl();
            //保存当前登录信息到session中
            User user = (User) ajaxInfo.getObj();
            //切换成在线状态
            userService.changeUserStatus(user.getId(), User.ON_LINE_STATUS);
            req.getSession().setAttribute("USER_INFO", user);

            //判断是否保存密码等登录信息
            if ("on".equals(remberPsd)) {
                Cookie remberPsdLoginNameCook = new Cookie("USER_NAME", user.getLoginName());
                Cookie remberPsdPasswordCook = new Cookie("USER_PSD", user.getPassword());
                Cookie remberPsdSessionIdCook = new Cookie("JSESSIONID", URLEncoder.encode(req.getSession().getId(), "utf-8"));
                remberPsdLoginNameCook.setMaxAge(60 * 60 * 60);
                remberPsdPasswordCook.setMaxAge(60 * 60 * 60);
                remberPsdSessionIdCook.setMaxAge(60 * 60 * 60);
                resp.addCookie(remberPsdLoginNameCook);
                resp.addCookie(remberPsdPasswordCook);
                resp.addCookie(remberPsdSessionIdCook);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(ajaxInfo);
        resp.getWriter().write(string);
    }

}
