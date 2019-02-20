package cn.youngfish.lt.server.impl;

import cn.youngfish.lt.server.UserService;
import cn.youngfish.lt.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

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
        int update = jdbCtemplate.update(sql, status, id);
        if (update == 1)
            return true;
        return false;
    }

}
