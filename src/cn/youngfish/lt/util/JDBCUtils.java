package cn.youngfish.lt.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ClassName JDBCUtils <br>
 * Description TODO 。<br>
 * Date 2019/2/16 21:02 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class JDBCUtils {

    private static DruidDataSource druidDataSource;

    static {
        Properties properties = new Properties();
        try {
            properties.load(JDBCUtils.class.getResourceAsStream("/jdbc.properties"));
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataSource getDataSource() {
        return druidDataSource;
    }

    public static JdbcTemplate getJDBCtemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(druidDataSource);
        return jdbcTemplate;
    }

}
