package cn.youngfish.lt.controller;

import cn.youngfish.lt.model.HistoryMessage;
import cn.youngfish.lt.model.httpmodel.UserInfo;
import cn.youngfish.lt.server.HistoryMessageService;
import cn.youngfish.lt.server.impl.HistoryMessageServiceImpl;
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
 * ClassName HistoryMessageServlet <br>
 * Description TODO 。<br>
 * Date 2019/2/22 19:53 <br>
 *
 * @author fish
 * @version 1.0
 **/
@WebServlet({"/historyMessage/findHistoryMessage", "/historyMessage/getHistoryMessageNum"})
public class HistoryMessageServlet extends HttpServlet {

    private HistoryMessageService historyMessageService;

    @Override
    public void init() throws ServletException {
        historyMessageService = new HistoryMessageServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sendUserId = req.getParameter("sendUserId");
        resp.setContentType("application/json;charset=utf-8");
        //当前没有登录用户直接结束
        if (UserInfo.USER_INFO == null) {
            return;
        }
        if (req.getRequestURI().endsWith("/findHistoryMessage")) {
            List<HistoryMessage> historyMessageList =
                    historyMessageService.findHistoryMessageBySendUserIDAndDesUserId(Integer.valueOf(sendUserId),
                            UserInfo.USER_INFO.getId());
            resp.getWriter().write(StringUtils.objectToJsonString(historyMessageList));
        } else if (req.getRequestURI().endsWith("/getHistoryMessageNum")) {
            List<Map<String, Object>> historyMessageNumList = historyMessageService.findHistoryMessageNumberByNowUser();
            resp.getWriter().write(StringUtils.objectToJsonString(historyMessageNumList));
        }
    }
}
