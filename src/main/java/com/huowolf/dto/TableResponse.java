package com.huowolf.dto;

import java.util.List;

/**
 * Created by huowolf on 2018/10/13.
 */
public class TableResponse<E> {
    private Integer code=0;
    private String msg="";
    private Integer total;
    private List<E> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
