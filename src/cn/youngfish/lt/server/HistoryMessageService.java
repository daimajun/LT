package cn.youngfish.lt.server;

import cn.youngfish.lt.model.HistoryMessage;
import cn.youngfish.lt.model.httpmodel.AjaxInfo;

import java.util.List;
import java.util.Map;

/**
 * ClassName HistoryMessageService <br>
 * Description 历史聊天记录服务类 。<br>
 * Date 2019/2/22 19:28 <br>
 *
 * @author fish
 * @version 1.0
 **/
public interface HistoryMessageService {

    /**
     * 保存历史聊天记录 。<br>
     * Date 19:31 2019/2/22 <br>
     *
     * @param historyMessage 单条聊天信息
     * @return AjaxInfo 保存是否成功信息对象
     */
    AjaxInfo saveHistoryMessage(HistoryMessage historyMessage);

    /**
     * 获得对应用户所有聊天 。<br>
     * Date 19:32 2019/2/22 <br>
     *
     * @param sendUserId 发送消息用户id
     * @param desUserId  发送目标用户id
     * @return 聊天记录集合
     */
    List<HistoryMessage> findHistoryMessageBySendUserIDAndDesUserId(Integer sendUserId, Integer desUserId);

    /**
     * 根据当前用户获得对应的历史消息 。<br>
     * Date 20:57 2019/2/22 <br>
     *
     * @return 历史消息集合
     */
    List<Map<String, Object>> findHistoryMessageNumberByNowUser();
}
