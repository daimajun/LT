package cn.youngfish.lt.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * ClassName StringUtils <br>
 * Description TODO 。<br>
 * Date 2019/2/16 20:46 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class StringUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static boolean isEmpty(String str) {
        if (str == null)
            return true;
        if (str.length() <= 0)
            return true;
        return false;
    }

    public static String objectToJsonString(Object o) {
        if (o != null) {
            try {
                String string = objectMapper.writeValueAsString(o);
                return string;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("对象转换成JSON字符串是吧！");
            }
        }
        return "";
    }

}
