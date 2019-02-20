package cn.youngfish.lt.model.httpmodel;

import java.io.Serializable;

/**
 * ClassName AjaxInfo <br>
 * Description TODO 。<br>
 * Date 2019/2/17 9:51 <br>
 *
 * @author fish
 * @version 1.0
 **/
public class AjaxInfo implements Serializable {

    private static final long serialVersionUID = 1700453991203024450L;

    private Boolean success;
    private String msg;
    private Object obj;

    public AjaxInfo() {
    }

    public AjaxInfo(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public AjaxInfo(Boolean success, String msg, Object obj) {
        this.success = success;
        this.msg = msg;
        this.obj = obj;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "AjaxInfo{" + "success=" + success + ", msg='" + msg + '\'' + ", obj=" + obj + '}';
    }
}
