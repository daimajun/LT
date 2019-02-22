package cn.youngfish.lt.filter;

import cn.youngfish.lt.model.User;
import cn.youngfish.lt.model.httpmodel.UserInfo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName LoginFilter <br>
 * Description TODO 。<br>
 * Date 2019/2/17 21:32 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpServletRequest.getRequestURI();

        if (UserInfo.USER_INFO != null) {
            filterChain.doFilter(httpServletRequest, servletResponse);
            return;
        } else {
            //判断是否有权限访问指定页面
            if (requestURI.endsWith("index.jsp") || "/".equals(requestURI)) {
                filterChain.doFilter(httpServletRequest, servletResponse);
                return;
            }
            httpServletResponse.sendRedirect("/index.jsp");
            return;
        }

    }

    @Override
    public void destroy() {

    }
}
