package cn.youngfish.lt.server.impl;

import cn.youngfish.lt.model.ChatRoom;
import cn.youngfish.lt.model.User;
import cn.youngfish.lt.server.UserService;
import cn.youngfish.lt.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * ClassName UserServiceImpl <br>
 * Description 用户服务实现类 。<br>
 * Date 2019/2/19 21:00 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class UserServiceImpl implements UserService {

    @Override
    public boolean changeUserStatus(Integer id, Integer status) {
        String sql = "update user set status = ? where id = ?";
        JdbcTemplate jdbCtemplate = JDBCUtils.getJDBCtemplate();
        int update = 0;
        try {
            update = jdbCtemplate.update(sql, status, id);
        } catch (DataAccessException e) {
            return false;
        }
        if (update == 1)
            return true;
        return false;
    }

    @Override
    public List<Map<String, Object>> getUserList(User nowUser) {
        JdbcTemplate jdbCtemplate = JDBCUtils.getJDBCtemplate();
        String sql =
                "select t1.id as id,t1.loginName as loginName,t2.id as chatRoomId from user t1 INNER JOIN ( select id,userId from " +
                        "chatroom where kfId = ?) t2 on t1.id = t2.userId";

        if (nowUser.getPermissions() != -1) {
            sql = "SELECT t1.id AS id, t1.loginName AS loginName, t2.id AS chatRoomId FROM USER t1 INNER JOIN ( SELECT id, kfId FROM " +
                    "chatroom WHERE userId = ? ) t2 ON t1.id = t2.kfId";
        }
        List<Map<String, Object>> maps = null;
        try {
            maps = jdbCtemplate.queryForList(sql, nowUser.getId());
        } catch (DataAccessException e) {
            return null;
        }
        return maps;
    }

    @Override
    public User getUserById(Integer id) {
        JdbcTemplate jdbcTemplate = JDBCUtils.getJDBCtemplate();
        String sql = "select * from user where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public ChatRoom getChatRoomByUserId(Integer userId) {
        String sql = "select * from chatroom where userid = ?";
        JdbcTemplate jdbcTemplate = JDBCUtils.getJDBCtemplate();
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ChatRoom.class), userId);
        } catch (DataAccessException e) {
            return null;
        }
    }

}
