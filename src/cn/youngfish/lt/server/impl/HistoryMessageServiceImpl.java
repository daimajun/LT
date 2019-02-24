package cn.youngfish.lt.server.impl;

import cn.youngfish.lt.model.HistoryMessage;
import cn.youngfish.lt.model.httpmodel.AjaxInfo;
import cn.youngfish.lt.server.HistoryMessageService;
import cn.youngfish.lt.util.JDBCUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName HistoryMessageServiceImpl <br>
 * Description 历史聊天记录服务实现类 。<br>
 * Date 2019/2/22 19:34 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class HistoryMessageServiceImpl implements HistoryMessageService {

    @Override
    public AjaxInfo saveHistoryMessage(HistoryMessage historyMessage) {
        if (historyMessage == null) {
            return new AjaxInfo(false, "聊天记录保存出错！");
        }
        JdbcTemplate jdbcTemplate = JDBCUtils.getJDBCtemplate();
        //(id, sendUserId, desUserId, msg, sendDate, isRead)
        String sql = "insert into HistoryMessage values(null, ?,?,?,?,?)";
        Object[] param = {historyMessage.getSendUserId(), historyMessage.getDesUserId(), historyMessage.getMsg(),
                historyMessage.getSendDate(), historyMessage.getIsRead()};
        int update = 0;
        try {
            update = jdbcTemplate.update(sql, param);
        } catch (DataAccessException e) {
            return new AjaxInfo(false, "保存失败");
        }
        if (update == 1) {
            return new AjaxInfo(true, "保存成功");
        }
        return new AjaxInfo(false, "保存失败");
    }

    @Override
    public List<HistoryMessage> findHistoryMessageBySendUserIDAndDesUserId(Integer sendUserId, Integer desUserId) {
        JdbcTemplate jdbcTemplate = JDBCUtils.getJDBCtemplate();
        String sql = "select * from HistoryMessage where sendUserId = ? and desUserId = ?";
        String updateSql = "update HistoryMessage set isRead = 1 where id = ?";
        List<HistoryMessage> query;
        try {
            query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(HistoryMessage.class), sendUserId, desUserId);
            List<Object[]> msgIdList = new ArrayList<>();
            for (int i = 0; i < query.size(); i++) {
                Object[] tempArray = {query.get(i).getId()};
                msgIdList.add(tempArray);
            }
            jdbcTemplate.batchUpdate(updateSql, msgIdList);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
        return query;
    }

    @Override
    public List<Map<String, Object>> findHistoryMessageNumberByNowUser(Integer nowUserId) {
        JdbcTemplate jdbcTemplate = JDBCUtils.getJDBCtemplate();
        String sql = "select msgNum,id,loginName from (SELECT count(sendUserId) AS msgNum, sendUserId FROM historymessage WHERE " +
                "desUserId = ? AND isRead = 0 GROUP BY sendUserId ) as t1 LEFT JOIN user t2 on t1.sendUserId = t2.id";
        List<Map<String, Object>> mapList;
        try {
            mapList = jdbcTemplate.queryForList(sql, nowUserId);
        } catch (DataAccessException e) {
            return null;
        }
        return mapList;
    }

}
